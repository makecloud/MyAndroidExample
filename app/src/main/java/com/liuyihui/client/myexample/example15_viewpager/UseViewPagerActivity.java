package com.liuyihui.client.myexample.example15_viewpager;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
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
    private ArrayList<View> pageviews = new ArrayList<>();//viewpager视图列表
    private ImageView scrollbarImageView;//横向滚动条
    /*数据*/
    private int offset = 0;// 滚动条初始偏移量
    private int currIndex = 0;// 当前页编号
    private int bmpW;// 滚动条宽度
    private int one;//一倍滚动量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_view_pager);
        //按钮标签
        curPhotoTextView = (TextView) findViewById(R.id.tv_cur_photo);
        devPhotoTextView = (TextView) findViewById(R.id.tv_dev_photo);
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
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //scrollbar滚动条
        scrollbarImageView = (ImageView) findViewById(R.id.iv_scrollbar);


        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.viewpager_cur_photo, null);
        View view2 = inflater.inflate(R.layout.viewpager_dev_photo, null);

        pageviews.add(view1);
        pageviews.add(view2);

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageviews.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //使从ViewGroup中移出当前View
            @Override
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageviews.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            @Override
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageviews.get(arg1));
                return pageviews.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0, true);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.scrollbar).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbarImageView.setImageMatrix(matrix);


    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbarImageView.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
