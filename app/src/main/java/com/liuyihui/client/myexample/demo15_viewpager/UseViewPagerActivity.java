package com.liuyihui.client.myexample.demo15_viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;

/**
 * 使用viewpager实例
 */
public class UseViewPagerActivity extends AppCompatActivity {
    private final String TAG = "UseViewPagerActivity";
    /*控件*/
    private TextView curPhotoTextView;//现场监拍
    private TextView devPhotoTextView;//终端快照
    private ViewPager viewPager;
    /** viewpager 使用的view list */
    private ArrayList<View> pageViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_view_pager);
        //按钮标签
        curPhotoTextView = findViewById(R.id.tv_cur_photo);
        devPhotoTextView = findViewById(R.id.tv_dev_photo);
        curPhotoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击"视频“时切换到第一页
                viewPager.setCurrentItem(0);
            }
        });
        devPhotoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击“音乐”时切换的第二页
                viewPager.setCurrentItem(1);
            }
        });

        //viewpager
        viewPager = findViewById(R.id.viewPager);


        //page views
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.viewpager_cur_photo, null);
        View view2 = inflater.inflate(R.layout.viewpager_dev_photo, null);

        pageViews.add(view1);
        pageViews.add(view2);

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            //获取当前窗体界面数
            @Override
            public int getCount() {
                return pageViews.size();
            }

            //判断是否由对象生成界面
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            //使从ViewGroup中移出当前View
            @Override
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageViews.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            @NonNull
            @Override
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageViews.get(arg1));
                return pageViews.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0, true);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 页面切换监听器
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
