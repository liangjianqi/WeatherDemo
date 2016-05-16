package cn.cnxad.weatherdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cn.cnxad.weatherdemo.R;

/**
 * 仿大风车效果
 * Author: Louis
 * Time:   2016/5/16 14:49
 */
public class RotationView extends View implements Runnable {

    //风车杆子
    private Bitmap bgBitmap;
    //要旋转的图片
    private Bitmap rotaBitmap;
    //旋转图片的长、宽
    private int width;
    private int height;
    //旋转角度
    private int rotate;
    //是否旋转
    private boolean ifRotate = false;

    public RotationView(Context context) {
        super(context);
    }

    public RotationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        loadImage();
        getViewSize();

        postInvalidate();

        //开启动画
        Thread thread = new Thread(this);
        thread.start();
    }

    //获取资源图片
    private void loadImage() {
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bigpole);
        rotaBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blade_big);
    }


    //获取图片大小
    private void getViewSize() {
        width = rotaBitmap.getWidth();
        height = rotaBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        //抗矩齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(false);

        Matrix matrix = new Matrix();

        float left = (float) width / 2;
        float top = (float) height / 2;

        //根据移动的距离计算移动后的图片
        Bitmap afterBitmap = Bitmap.createBitmap((int) (bgBitmap.getWidth() + left), (int) (bgBitmap.getHeight() + top), bgBitmap.getConfig());

        //风车杆子的起点位置
        matrix.setTranslate(left - 12, top);
        canvas.drawBitmap(bgBitmap, matrix, paint);

        //旋转的中心位置
        matrix.setTranslate((float) width / 2, (float) height / 2);

        //开始旋转
        matrix.preRotate(rotate);

        //还原转轴位置
        matrix.preTranslate(-(float) width / 2, -(float) height / 2);

        //绘制风车
        canvas.drawBitmap(rotaBitmap, matrix, paint);

        super.onDraw(canvas);
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (ifRotate) {
                    this.rotate += 5;
                    this.postInvalidate();

                    Thread.sleep(30);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void startRotate() {
        this.ifRotate = true;
    }

    public void stopRotate() {
        this.ifRotate = false;
    }
}
