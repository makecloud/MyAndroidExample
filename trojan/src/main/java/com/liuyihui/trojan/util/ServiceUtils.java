package com.liuyihui.trojan.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;


/**
 * Created by gaowen on 2017/9/8.
 */

public class ServiceUtils {

    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(Context context, final String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
