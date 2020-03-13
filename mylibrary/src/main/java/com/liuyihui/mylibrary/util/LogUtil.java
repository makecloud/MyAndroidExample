package com.liuyihui.mylibrary.util;

import android.util.Log;

import com.liuyihui.mylibrary.BuildConfig;


/**
 * 日志工具类，控制日志输出打印
 * Created by liuyh on 2017/4/24.
 */

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    //调试设置成verbose，发布设置成info就行
    private static final int LEVEL = BuildConfig.BUILD_TYPE.equals("debug") ? DEBUG : INFO;

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg, e);
        }
    }

}
