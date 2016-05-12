package cn.cnxad.weatherdemo.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: Louis on 2016/5/12 16:06
 */
public class DateUtil {
    public static String getFormatTime(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(time);
            SimpleDateFormat formatSdf = new SimpleDateFormat("HH:mm");
           return formatSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
