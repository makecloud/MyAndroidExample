package com.liuyihui.trojan.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class AppUtil {
    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && aInfo.processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 打开App
     *
     * @param packageName 包名
     */
    public static void launchApp(Context context,final String packageName) {
        context.startActivity(getLaunchAppIntent(context,packageName));
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(Context context,final String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }
}
