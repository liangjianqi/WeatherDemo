package cn.cnxad.weatherdemo.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 作者: Louis on 2016/5/11 15:17
 */
public class GsonRequest<T> extends Request<T> {

    private Response.Listener<T> mListener;
    private Map<String, String> mParams;
    private Gson mGson;
    private Class<T> mClass;
    private final String DEFAULT_ENCODING = "utf-8";


    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    public GsonRequest(int method, Map<String, String> params, String url, Class<T> clazz, Response.Listener listener, Response.ErrorListener errorListener) {
        this(method, url, clazz, listener, errorListener);
        mParams = params;
    }


    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mListener = listener;
        this.mGson = new Gson();
        this.mClass = clazz;
        setTag(listener);
    }

    public GsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null) {
            return mParams;
        }
        return super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            //格式化  "HeWeather data service 3.0"
            String jsonStr = new String(response.data, DEFAULT_ENCODING);

            StringBuffer sb = new StringBuffer(jsonStr);
            sb.deleteCharAt(11);
            sb.deleteCharAt(15);
            sb.delete(22, 26);

            return Response.success(mGson.fromJson(sb.toString(), mClass), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


}
