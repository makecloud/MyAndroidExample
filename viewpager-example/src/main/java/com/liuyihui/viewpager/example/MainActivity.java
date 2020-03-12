package com.liuyihui.viewpager.example;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    List<MaterialData> dataSet;
    Map<Integer, String> dataMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataSet();

        //pager
        viewPager = findViewById(R.id.viewpager);

        //适配器
        pagerAdapter = new PagerAdapter() {
            Map<Integer, View> itemViewMap = new HashMap<>();

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Log.i(TAG, "instantiateItem==");

                if (itemViewMap.get(position) == null) {
                    ShufflePageItemView shuffleView = new ShufflePageItemView(MainActivity.this);
                    if (dataSet.get(position).getType() == 2) {
                        shuffleView.showVideoView(dataSet.get(position).getUrl());
                    } else if (dataSet.get(position).getType() == 1) {
                        shuffleView.showImage(dataSet.get(position).getUrl());
                    }
                    Log.i(TAG, " 创建shuffleView 并加载资源");
                    itemViewMap.put(position, shuffleView);
                }
                View itemView = itemViewMap.get(position);
                container.addView(itemViewMap.get(position));
                return itemView;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //                super.destroyItem(container, position, object);
                container.removeView((View) object);
                Log.i(TAG, "destroyItem==");
            }


            @Override
            public int getCount() {
                Log.i(TAG, "getCount==");
                return dataSet.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                Log.i(TAG, "isViewFromObject==");
                return view == object;
            }
        };

        //设置适配器
        viewPager.setAdapter(pagerAdapter);
    }

    public void initDataSet() {
        dataSet = new ArrayList<>();
        dataSet.add(new MaterialData(2,
                                     "http://flashmedia.eastday" +
                                             ".com/newdate/news/2016-11/shznews1125-19.mp4"));
        dataSet.add(new MaterialData(1,
                                     "http://test.yungeshidai" +
                                             ".com/material/39350fe9bcf2503cf20c96e67c51135b.jpg"));
        dataSet.add(new MaterialData(1,
                                     "http://test.yungeshidai" +
                                             ".com/material/c4241c6bcb09468d1d8c7d251a1fe3b9.jpg"));
        dataSet.add(new MaterialData(1,
                                     "http://test.yungeshidai.com/material/e4f2a39a7afa8aa950f86c82c1ee892e.jpg"));
    }
}
