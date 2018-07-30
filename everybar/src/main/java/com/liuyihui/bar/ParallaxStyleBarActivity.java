package com.liuyihui.bar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oohlink.wifiprobe.everybar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 页面顶部展示ImageView 图片, 向上滑动隐藏,并具有滑动视差效果
 */
public class ParallaxStyleBarActivity extends AppCompatActivity {
    private static final String TAG = "ParallaxBarActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    LinearLayout linearLayout;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉本activity的titleBar
        setContentView(R.layout.activity_parallax_style_bar);
        ButterKnife.bind(this);

        imageView.setImageResource(R.drawable.head_bg);

        toolbar.setTitle(" toolbar title");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //加高toolbar
        toolbar.getLayoutParams().height += ActivityUtil.getStatusBarHeight(this);
        //toolbar 加大上间隙,防止内容被信号栏遮盖
        toolbar.setPadding(toolbar.getPaddingLeft(),
                           toolbar.getPaddingTop() + ActivityUtil.getStatusBarHeight(this),
                           toolbar.getPaddingRight(),
                           toolbar.getPaddingBottom());

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //降信号栏设置为透明,悬浮式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置信号栏颜色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.parseColor("#00000000"));
            window.setNavigationBarColor(Color.TRANSPARENT);//使三大键导航栏透明

            //systemui
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //使三键导航栏重叠在布局之上
                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(flag);
        }

        //去掉了滑下来的大标题
        collapsingToolbarLayout.setTitleEnabled(false);

        //向上滑触发变色的距离设置为toolbar高度, 产生布局滑到最顶上才变色的效果. 注意必须+1个像素达到大于其值.
        collapsingToolbarLayout.setScrimVisibleHeightTrigger(toolbar.getLayoutParams().height + 1);

        //监听collapsing layout折叠,没错就是使用appBarLayout来监听
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    Log.i(TAG, "expand");
                } else if (state == State.COLLAPSED) {
                    Log.i(TAG, "collapsed");
                    collapsingToolbarLayout.setStatusBarScrimColor(Color.GREEN);
                } else {
                    Log.i(TAG, "idle");
                }
            }
        });

        //往滑动view里添加item,使有足够数据滑动
        for (int i = 0; i < 30; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
            textView.setText(String.valueOf(i));
            linearLayout.addView(textView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
