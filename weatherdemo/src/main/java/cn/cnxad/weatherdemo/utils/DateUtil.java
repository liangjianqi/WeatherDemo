package cn.cnxad.weatherdemo.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: Louis on 2016/5/12 16:06
 */
public class DateUtil {

    /**
     * 格式化时间
     *
     * @param time       原始时间
     * @param fromFromat 原始时间的时间格式
     * @param toFormat   转化后的时间格式
     * @return 字符串类型时间
     */
    public static String getFormatTime(String time, String fromFromat, String toFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFromat);
        try {
            Date date = sdf.parse(time);
            SimpleDateFormat formatSdf = new SimpleDateFormat(toFormat);
            return formatSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param format 需要转化的日期格式
     * @return
     */
    public static String getCurrentTime(String format) {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(currentTime);
    }

    /**
     * 获取指定时间是星期几
     *
     * @param time   需要转化的时间
     * @param format 时间的格式
     * @return
     */
    public static String getWeek(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);

            SimpleDateFormat weekSdf = new SimpleDateFormat("EEEE");
            return weekSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
