package com.liuyihui.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.liuyihui.mylibrary.customview.MyToolBar;

import java.lang.reflect.Field;

/**
 * Created by liuyi on 2018/2/2.
 */

public class ActivityUtil {
    private static final String TAG = "ActivityUtil";
    private static int statusBarHeight;

    /**
     * 把activity搞成信号栏透明切不占位置,悬浮在布局之上
     *
     * @param activity
     */
    public static void setLucencyStatusBar(Activity activity) {
        //搞成叠浮透明信号栏,叠浮透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                //                    | View
                                                                //                    .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //使导航栏重叠在布局之上
                                                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //            window.setNavigationBarColor(Color.TRANSPARENT);//使底部导航栏透明
        }
    }

    /**
     * toolbar统一设置
     * <p>
     * 统一设置返回按钮图标,大小,透明度等
     * <p>
     * consider use {OOHLinkBaseActivity#setContentView} to perform toolbar configs.
     *
     * @param activity
     * @param toolbar
     * @param title    标题
     */
    public static void toolbarCommonSetting(AppCompatActivity activity, Toolbar toolbar,
                                            String title) {
        if (toolbar instanceof MyToolBar) {
            ((MyToolBar) toolbar).setTitleText(title);
        } else {
            toolbar.setTitle(title);
        }
        toolbarCommonSetting(activity, toolbar);
    }

    public static void toolbarCommonSetting(AppCompatActivity activity, Toolbar toolbar) {
        //添加toolbar为ActionBar并设置返回按钮
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置bar有返回按钮
        //        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
    }

    /**
     * 获取信号栏高度
     * <p>
     * 在使用沉浸信号栏时, 获得这个宽度, 采用让内容 的padding top设置这个宽度的方法, 来避免信号栏遮挡住文字等.
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Context activity) {
        if (statusBarHeight != 0) {
            return statusBarHeight;
        }
        int resourceId = activity.getResources()
                                 .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //处理信号栏遮挡
    public static void offsetAwayStatusBar(Activity activity, ViewGroup topBarView) {
        topBarView.setPadding(topBarView.getPaddingLeft(),
                              topBarView.getPaddingTop() + ActivityUtil.getStatusBarHeight(activity),
                              topBarView.getPaddingRight(),
                              topBarView.getPaddingBottom());
    }

    /**
     * 设置状态栏黑色图标
     * <p>
     * bug:在setLucencyStatusBar之前前设置无效。猜测可能被setLucencyStatusBar覆盖了东西。
     *
     * @param
     */
    public static void setStatusBarIconColorBlack(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            boolean success = flymeSetStatusBarLightMode(window, true);
            LogUtil.e(TAG, "set bar black icon success? " + success);
        }
    }

    public static void setStatusBarIconColorWhite(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置一次SYSTEM_UI_FLAG_VISIBLE能抵消SYSTEM_UI_FLAG_LIGHT_STATUS_BAR造成的黑色信号栏文本
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
            boolean success = flymeSetStatusBarLightMode(window, true);
            LogUtil.e(TAG, "set bar black icon success? " + success);
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField(
                        "MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
