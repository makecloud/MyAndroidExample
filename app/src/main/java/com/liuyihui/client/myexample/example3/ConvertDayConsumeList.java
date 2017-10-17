package com.liuyihui.client.myexample.example3;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 算法-转换dayConsumeList为带月份条的
 * Created by liuyh on 2016/11/23.
 */

public class ConvertDayConsumeList {
    private final static String TAG = "ConvertDayConsumeList";

    public static List<DayConsome> convert(List<DayConsome> resDayConsomeList) {
        //目标列表。将包含原数据元素，和月份条元素
        List<DayConsome> desDayConsuDayConsomeList = new ArrayList<>();

        //临时月份，代表目标列表中最新月份条的月份值
        String tempBarMonth = null;

        //遍历原list
        Iterator it = resDayConsomeList.iterator();
        while (it.hasNext()) {
            //得到原list项
            DayConsome resItem = (DayConsome) it.next();

            //得到此项的月份值
            String thisItemMonth = resItem.getDate().substring(5, 7);// TODO: 2016/11/24 待优化为使用日历精准提取月份
            Log.i(TAG, "得到此项的月份值:" + thisItemMonth);

            //判断此项月份等于 目标列表中最新月份条的月份值
            if (thisItemMonth.equals(tempBarMonth)) {
                //目标list添加数据元素
                desDayConsuDayConsomeList.add(resItem);
                Log.i(TAG, "----目标list添加数据元素");
            } else {
                //目标列表中最新月份条的月份值 更改为此项月份值
                tempBarMonth = thisItemMonth;
                // 目标list添加 月份条对象
                desDayConsuDayConsomeList.add(new DayConsome(tempBarMonth));
                Log.i(TAG, "----目标list添加 月份条对象");
                desDayConsuDayConsomeList.add(resItem);
                Log.i(TAG, "----目标list添加数据元素");
            }
        }

        return desDayConsuDayConsomeList;
    }
}
