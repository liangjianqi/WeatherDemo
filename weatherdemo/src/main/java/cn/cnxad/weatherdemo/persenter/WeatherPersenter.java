package cn.cnxad.weatherdemo.persenter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import cn.cnxad.weatherdemo.api.Urls;
import cn.cnxad.weatherdemo.entity.WeatherBean;
import cn.cnxad.weatherdemo.net.CallbackListener;
import cn.cnxad.weatherdemo.net.GsonRequest;
import cn.cnxad.weatherdemo.net.ParamsUtil;
import cn.cnxad.weatherdemo.net.VolleyManager;

/**
 * 作者: Louis on 2016/5/11 18:01
 */
public class WeatherPersenter {

    private VolleyManager manager;
    private CallbackListener<WeatherBean> listener;

    public WeatherPersenter(String cityId, VolleyManager manager, final CallbackListener<WeatherBean> listener) {
        this.manager = manager;
        this.listener = listener;

        Map<String, String> params = new HashMap<>();
        params.put("cityid", cityId);
        params.put("key", "89de24e528434149b762a771b9e1dc20");

        String url = ParamsUtil.createGetUrlWithParams(Urls.WEATHER_OF_CITY, params);

        GsonRequest<WeatherBean> gsonRequest = new GsonRequest<WeatherBean>(Request.Method.GET,
                params,
                url,
                WeatherBean.class,
                new Response.Listener<WeatherBean>() {
                    @Override
                    public void onResponse(WeatherBean response) {
                        listener.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        manager.addRequestQueue(gsonRequest);

    }
}
