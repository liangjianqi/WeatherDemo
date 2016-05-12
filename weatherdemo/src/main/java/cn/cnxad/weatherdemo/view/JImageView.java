package cn.cnxad.weatherdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 作者: Louis on 2016/5/12 12:18
 */
public class JImageView extends ImageView {

    private static final int PLACE_HOLDER_DEFAULT = 0;

    public JImageView(Context context) {
        super(context);
    }

    public JImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImage(int resId) {
        Glide.with(getContext()).load(resId).into(this);
    }

    public void loadImage(String url) {
        this.loadImage(url, PLACE_HOLDER_DEFAULT);
    }

    public void loadImage(String url, int placeholderRes) {

        if (placeholderRes == PLACE_HOLDER_DEFAULT) {
            Glide.with(getContext()).load(url).crossFade().into(this);
        } else {
            Glide.with(getContext()).load(url).crossFade().placeholder(placeholderRes).into(this);
        }

    }

}
