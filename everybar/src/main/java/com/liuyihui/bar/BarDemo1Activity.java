package com.liuyihui.bar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.oohlink.wifiprobe.everybar.R;

/**
 * 操作bar 实例1
 * <p>
 * n天后回来看这个页面竟然也不知道做啥的, 执行这段代码最终实现啥都看不粗来了~简直了
 */
public class BarDemo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //移除本activity的ActionBar方法一; 2018-7-19 19:00:41看这句注释, 是移除ActionBar吗? 这是去掉TitleBar吧?
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main1);

        //移除本activity的actionBar方法二;注意:导致此activity启动后不显示actionBar. 2018-7-19 18:59:31来看前面这句注释,不显示actionBar是什么效果呢?
//        getSupportActionBar().hide();

        //设置actionBar上的返回按钮显示与否.但并不会自动赋予返回功能.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //清除系统提供的默认保护色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                      | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置系统UI的显示方式
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //添加属性可以自定义设置系统工具栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
