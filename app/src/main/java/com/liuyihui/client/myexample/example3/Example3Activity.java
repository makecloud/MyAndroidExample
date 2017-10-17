package com.liuyihui.client.myexample.example3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * listview悬浮月份例子
 */
public class Example3Activity extends AppCompatActivity {

    private final String TAG = "Example3Activity";
    List<DayConsome> dayConsomeList = new ArrayList<>();
    DayConsumeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        //模拟4个月消费流水数据
        for (int i = 8; i < 16; i++) {
            DayConsome dayConsome = new DayConsome();
            dayConsome.setConsume(2354.15);
            dayConsome.setDate("2016-09-" + i);
            dayConsomeList.add(dayConsome);
        }
        for (int i = 1; i < 7; i++) {
            DayConsome dayConsome = new DayConsome();
            dayConsome.setConsume(2354.15);
            dayConsome.setDate("2016-10-" + i);
            dayConsomeList.add(dayConsome);
        }
        for (int i = 7; i < 21; i++) {
            DayConsome dayConsome = new DayConsome();
            dayConsome.setConsume(2354.15);
            dayConsome.setDate("2016-11-" + i);
            dayConsomeList.add(dayConsome);
        }
        for (int i = 13; i < 19; i++) {
            DayConsome dayConsome = new DayConsome();
            dayConsome.setConsume(2354.15);
            dayConsome.setDate("2016-12-" + i);
            dayConsomeList.add(dayConsome);
        }

        Log.i(TAG, "dayConsomeList转换前：" + dayConsomeList.size());
        //转换数据
        dayConsomeList = ConvertDayConsumeList.convert(dayConsomeList);
        Log.i(TAG, "dayConsomeList转换后：" + dayConsomeList.size());

        //创建适配器
        adapter = new DayConsumeListAdapter(this, dayConsomeList);

        //给 列表视图设置适配器
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(adapter);

    }

    /**
     * 列表视图适配器
     */
    public class DayConsumeListAdapter extends ArrayAdapter<DayConsome> {

        public DayConsumeListAdapter(Context context, List<DayConsome> dayConsomeList) {
            super(context, 0, dayConsomeList);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.example3_list_item_myaccount_dayconsume, null);
            }
            //获取对应项 列表元素对象
            DayConsome dayConsomeItem = getItem(position);
            String barMonth = dayConsomeItem.getBarMonth();
            //获取控件
            LinearLayout barMonthlinLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_month_bar);
            LinearLayout dayConsumeLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_day_consume);
            TextView monthBarTextView = (TextView) convertView.findViewById(R.id.tv_myaccount_monthbar);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.tv_myaccount_date);
            TextView consumeValueTextView = (TextView) convertView.findViewById(R.id.tv_myaccount_dayconsume_value);
            //设置控件
            if (barMonth == null) {
                //显示日消耗布局
                dayConsumeLinearLayout.setVisibility(View.VISIBLE);
                barMonthlinLinearLayout.setVisibility(View.GONE);
                //设置日消耗布局元素属性
                dateTextView.setText(dayConsomeItem.getDate());
                consumeValueTextView.setText(dayConsomeItem.getConsume().toString());
            } else {
                //显示月份条布局
                dayConsumeLinearLayout.setVisibility(View.GONE);
                barMonthlinLinearLayout.setVisibility(View.VISIBLE);
                //设置月份条布局元素属性
                monthBarTextView.setText(barMonth);
            }
            return convertView;
        }
    }
}
