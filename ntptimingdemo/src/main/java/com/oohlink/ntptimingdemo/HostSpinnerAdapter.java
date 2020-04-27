package com.oohlink.ntptimingdemo;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class HostSpinnerAdapter implements SpinnerAdapter {
    private String[] dataSet;
    private Context context;

    public HostSpinnerAdapter(Context context) {
        this.context = context;
        dataSet = new String[]{"cn.pool.ntp.org",//
                "s1a.time.edu.cn",//北京邮电大学
                "s1b.time.edu.cn",//清华大学
                "s1c.time.edu.cn",//北京大学
                "s1d.time.edu.cn",//东南大学
                "s1e.time.edu.cn",//清华大学
                "s2a.time.edu.cn",//清华大学
                "s2b.time.edu.cn",//清华大学
                "cn.ntp.org.cn",//中国[China]
                "us.ntp.org.cn",//美国[America]
                "kr.ntp.org.cn",//韩国[korea]
                "sgp.ntp.org.cn"//新加坡[Singapore]
        };

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                                        .inflate(android.R.layout.simple_spinner_dropdown_item, null);

        }
        TextView itemView = (TextView) convertView;
        itemView.setText(dataSet[position]);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return dataSet.length;
    }

    @Override
    public Object getItem(int position) {
        return dataSet[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                                        .inflate(android.R.layout.simple_spinner_item, null);

        }
        TextView itemView = (TextView) convertView;
        itemView.setText(dataSet[position]);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return dataSet.length < 1;
    }
}
