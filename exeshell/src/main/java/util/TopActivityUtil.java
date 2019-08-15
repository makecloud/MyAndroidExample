package util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class TopActivityUtil {

    public static String getTopActivityInfo(Context context) {
        String packageName = "";
        String topActivityName = "";
        ActivityManager manager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));


        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
            if (topAppProcess != null && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                packageName = topAppProcess.processName;
                topActivityName = "";
            }
        } else {
            //getRunningTasks() is deprecated since API Level 21 (Android 5.0)
            List localList = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo) localList.get(0);
            packageName = localRunningTaskInfo.topActivity.getPackageName();
            topActivityName = localRunningTaskInfo.topActivity.getClassName();
        }
        return packageName + "/" + topActivityName;
    }

}
