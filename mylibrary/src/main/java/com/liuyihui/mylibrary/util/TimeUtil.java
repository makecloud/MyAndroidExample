package com.liuyihui.mylibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuyh on 2016/12/8.
 */

public class TimeUtil {
    /**
     * @param date 2016-11-24
     * @return
     */
    public static String getWeekOfDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = simpleDateFormat.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            int week = c.get(Calendar.DAY_OF_WEEK);
            switch (week) {
                case Calendar.MONDAY:
                    return "星期一";
                case Calendar.TUESDAY:
                    return "星期二";
                case Calendar.THURSDAY:
                    return "星期三";
                case Calendar.WEDNESDAY:
                    return "星期四";
                case Calendar.FRIDAY:
                    return "星期五";
                case Calendar.SATURDAY:
                    return "星期六";
                case Calendar.SUNDAY:
                    return "星期日";
                default:
                    return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获得当前的yyyy-MM-dd格式日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 替换-为.
     *
     * @param dateStr
     * @return
     */
    public static String replaceSplitor(String dateStr) {
        return dateStr.replace("-", ".");
    }


}
