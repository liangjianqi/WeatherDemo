package cn.cnxad.weatherdemo.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 单例，保证所有请求使用同一个volley对象
 */
public class VolleyManager {

    private static VolleyManager ourInstance;
    private RequestQueue mRequestQuene;

    public static synchronized VolleyManager getInstance(Context mContext) {
        if (ourInstance == null) {
            ourInstance = new VolleyManager(mContext);
        }
        return ourInstance;
    }

    private VolleyManager(Context context) {
        mRequestQuene = Volley.newRequestQueue(context);
    }

    public RequestQueue getRequestQuene() {
        return this.mRequestQuene;
    }

    public <T> void addRequestQueue(Request<T> request) {
        getRequestQuene().add(request);
    }

    public void cancelRequest(Object object) {
        getRequestQuene().cancelAll(object);
    }


}
