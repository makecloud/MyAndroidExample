package com.liuyihui.mylibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    /**
     * 过期于今天
     *
     * @param timeStr
     * @return
     */
    public static boolean isPast(String timeStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        try {
            Date date = simpleDateFormat.parse(timeStr);
            if (calendar.getTimeInMillis() > date.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
