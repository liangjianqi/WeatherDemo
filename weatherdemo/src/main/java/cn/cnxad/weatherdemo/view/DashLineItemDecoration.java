package cn.cnxad.weatherdemo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者: Louis on 2016/5/13 15:30
 */
public class DashLineItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //得到开始和结束位置
        int left = parent.getLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        //获取子View数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

            //起点的高度
            int top = childView.getBottom() + params.bottomMargin;

            //绘制虚线
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right, top);
            //将path线段虚线化,float[]必须是偶数长度，表示先绘制x长度的实线，再绘制y长度的空白，第二个参数为偏移量
            PathEffect pathEffect = new DashPathEffect(new float[]{5, 5}, 5);
            paint.setPathEffect(pathEffect);
            c.drawPath(path, paint);
        }
    }
}
