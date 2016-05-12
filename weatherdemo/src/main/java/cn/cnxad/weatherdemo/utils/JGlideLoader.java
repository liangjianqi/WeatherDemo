package cn.cnxad.weatherdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 作者: Louis on 2016/5/12 12:30
 */
public class JGlideLoader {

    private static final int PLACE_HOLDER_DEFAULT = 0;

    public static void load(Context context, ImageView imageView, int placeholderRes, Object res) {
        if (res instanceof String) {
            if (placeholderRes == PLACE_HOLDER_DEFAULT) {
                Glide.with(context).load((String) res).crossFade().centerCrop().into(imageView);
            } else {
                Glide.with(context).load((String) res).crossFade().placeholder(placeholderRes).into(imageView);
            }
        }
    }

    public static void load(Context context, ImageView imageView, Object res) {
        load(context, imageView, PLACE_HOLDER_DEFAULT, res);
    }

}
