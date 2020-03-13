package com.liuyihui.mylibrary.util;

import android.content.Context;
import android.content.Intent;

/**
 * note: need hard code the class full name to constants here.
 * <p>
 * Created by liuyi on 2018/1/31.
 */

public class ContextUtil {
    public static final String EXTRA_EXIT_BACK_LOGIN = "extra_exit_back_login";
    private static final String TAG = "ContextUtil";
    private static final String LOGIN_ACTIVITY_CLASS_NAME = "com.yunge.client.media.common.view.LoginActivity";
    private static final String MEDIA_APPLICATION_CLASS_NAME = "com.yunge.client.media.MyApplication";
    private static final String ADVER_APPLICATION_CLASS_NAME = "com.yunge.client.advertiser.MyApplication";
    private static Context context;

    //静态块,在加载类,执行构造方法前执行
    static {
        //library中为拿到context,使用反射获取appModule中的Application类context属性
        try {
            Class mediaApplicationCls = Class.forName(MEDIA_APPLICATION_CLASS_NAME);//这里是代码包名，不是应用包名
            if (mediaApplicationCls != null) {
                context = (Context) mediaApplicationCls.getDeclaredField("context").get(null);//由于context是静态字段, 所以参数null是被忽略的
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "获取context出错");
        }


        try {
            Class adverApplicationCls = Class.forName(ADVER_APPLICATION_CLASS_NAME);
            if (adverApplicationCls != null) {
                context = (Context) adverApplicationCls.getDeclaredField("context").get(null);//由于context是静态字段, 所以参数null是被忽略的
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "获取context出错");
        }
    }


    public static Context getContext() {
        return context;
    }

    /**
     * 退出到登录页activity
     */
    public static void exitToLoginActivity() {
        Context ctx = ContextUtil.getContext();
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClassName(ctx, LOGIN_ACTIVITY_CLASS_NAME);
        ctx.startActivity(i);
        LogUtil.i(TAG, "go to login");
    }

    /**
     * 退出到登录页activity.（销毁之前的activity）
     */
    public static void exitToLoginActivityWithPrompt() {
        Context ctx = ContextUtil.getContext();
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(ctx, LOGIN_ACTIVITY_CLASS_NAME);
        intent.putExtra(EXTRA_EXIT_BACK_LOGIN, true);
        ctx.startActivity(intent);
        LogUtil.i(TAG, "go to login");
    }
}
