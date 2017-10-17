package com.liuyihui.client.myexample.example15_viewpager;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager+fragment滑动切换页面实例
 */
public class ViewPagerFragmentActivity extends AppCompatActivity {

    private final String TAG = "ViewPagerFragmentActivity";
    /*控件*/
    private TextView curPhotoTextView;//现场监拍
    private TextView devPhotoTextView;//终端快照
    private ViewPager viewPager;
    private ImageView scrollbarImageView;//横向滚动条
    /**
     * 页面集合
     */
    List<Fragment> fragmentList;

    /**
     * 四个Fragment（页面）
     */
    OneFragment oneFragment;
    TwoFragment twoFragment;
    /*数据*/
    private int offset = 0;// 滚动条初始偏移量
    private int currIndex = 0;// 当前页编号
    private int bmpW;// 滚动条宽度
    private int one;//一倍滚动量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);
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
        configureViewpager();//配置viewpager
    }

    private void configureViewpager() {
        //fragment初始化
        fragmentList = new ArrayList<>();
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);

        //数据适配器
        PagerAdapter mPagerAdapter = new MyFrageStatePagerAdapter(getSupportFragmentManager());
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0, true);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new ViewPagerFragmentActivity.MyOnPageChangeListener());
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

    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem = viewPager.getCurrentItem();
            if (currentItem == currenttab) {
                return;
            }
            currenttab = viewPager.getCurrentItem();
        }

    }

    //当前选中的项
    private int currenttab = -1;
}
