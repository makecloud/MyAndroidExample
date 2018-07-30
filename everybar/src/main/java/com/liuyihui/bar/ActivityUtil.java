package com.liuyihui.bar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liuyi on 2018/2/2.
 */

public class ActivityUtil {
    /**
     * 把activity搞成信号栏透明切不占位置,悬浮在布局之上
     * <p>
     * 怎么还有导航栏的操作?
     *
     * @param activity
     */
    public static void setLucencyStatusBar(Activity activity) {
        //搞成叠浮透明信号栏,叠浮透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                      | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //使导航栏重叠在布局之上
                                                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);//使导航栏透明
        }
    }

    /**
     * toolbar统一设置
     * <p>
     * 统一设置返回按钮图标,大小,透明度等
     *
     * @param activity
     * @param toolbar
     * @param title 标题
     */
    public static void toolbarCommonSetting(AppCompatActivity activity, Toolbar toolbar, String title) {
        toolbar.setTitle(title);
//        toolbar.setTitleMarginTop(52);//可控制标题文字上下位置
        toolbar.setElevation(0);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置bar有返回按钮
//        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_yunge_goback);
    }

    /**
     * 获取信号栏高度
     * <p>
     * 在使用沉浸信号栏时, 获得这个宽度, 采用让内容 的padding top设置这个宽度的方法, 来避免信号栏遮挡住文字等.
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
