package cn.cnxad.weatherdemo.persenter;

import java.util.List;

import cn.cnxad.weatherdemo.entity.WeatherBean;
import cn.cnxad.weatherdemo.ui.adapter.WindDailyAdapter;
import cn.cnxad.weatherdemo.ui.adapter.WindHourAdapter;

/**
 * 作者: Louis on 2016/5/13 11:50
 */
public class DataboxSet {

    public static void insertDailyList(List<WeatherBean.HeWeatherdataserviceBean.DailyForecastBean> list, WindDailyAdapter adapter) {
        if (null == list || list.size() == 0) return;
        for (WeatherBean.HeWeatherdataserviceBean.DailyForecastBean item : list) {
            adapter.insertItem(item, adapter.getItemCount());
        }
    }

    public static void insertHourList(List<WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean> list, WindHourAdapter adapter) {
        if (null == list || list.size() == 0) return;
        for (WeatherBean.HeWeatherdataserviceBean.HourlyForecastBean item : list) {
            adapter.insertItem(item, adapter.getItemCount());
        }
    }
}
