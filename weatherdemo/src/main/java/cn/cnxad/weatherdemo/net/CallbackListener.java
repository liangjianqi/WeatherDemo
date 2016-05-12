package cn.cnxad.weatherdemo.net;

import com.android.volley.VolleyError;

/**
 * 作者: Louis on 2016/5/11 17:23
 */
public interface CallbackListener<T> {
    void onSuccessResponse(T response);
    void onErrorResponse(VolleyError error);
}
