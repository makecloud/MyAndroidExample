package com.liuyihui.client.myexample;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

/**
 * 实现在任何地方都能获得context
 * Created by liuyh on 2016/10/27.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //        CrashHandler crashHandler = CrashHandler.getInstance();
        //        crashHandler.init(context);
    }

    public static Context getContextObject() {
        return context;
    }

    /**
     * 返回应用在sd卡上的目录
     *
     * @return /sdcard/com.liyihui.client.myexample
     */
    public static String getAppPathOnSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            PackageManager pm;
            return Environment.getExternalStorageDirectory()
                              .getAbsolutePath() + File.separator + context.getPackageName();
        } else {
            return null;
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        context = null;
    }
}
