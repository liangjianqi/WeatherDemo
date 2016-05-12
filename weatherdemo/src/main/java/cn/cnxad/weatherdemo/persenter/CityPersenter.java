package cn.cnxad.weatherdemo.persenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import cn.cnxad.weatherdemo.api.Urls;
import cn.cnxad.weatherdemo.entity.AllChinaCityBean;
import cn.cnxad.weatherdemo.net.CallbackListener;
import cn.cnxad.weatherdemo.net.GsonRequest;
import cn.cnxad.weatherdemo.net.ParamsUtil;
import cn.cnxad.weatherdemo.net.VolleyManager;

/**
 * 作者: Louis on 2016/5/12 09:25
 */
public class CityPersenter {

    private VolleyManager volleyManager;
    private CallbackListener<AllChinaCityBean> callbackListener;

    public CityPersenter(String search, VolleyManager volleyManager, final CallbackListener<AllChinaCityBean> callbackListener) {
        this.volleyManager = volleyManager;
        this.callbackListener = callbackListener;

        Map<String, String> param = new HashMap<>();
        param.put("search", search);
        param.put("key", "89de24e528434149b762a771b9e1dc20");

        String url = ParamsUtil.createGetUrlWithParams(Urls.CITYLIST, param);

        GsonRequest<AllChinaCityBean> cityRequest = new GsonRequest<AllChinaCityBean>(url, AllChinaCityBean.class, new Response.Listener<AllChinaCityBean>() {
            @Override
            public void onResponse(AllChinaCityBean response) {
                callbackListener.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callbackListener.onErrorResponse(error);
            }
        });

        volleyManager.addRequestQueue(cityRequest);
    }
}
