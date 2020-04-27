package com.liuyihui.mylibrary.util;

import android.os.Environment;
import android.util.Log;

import com.liuyihui.mylibrary.util.ShellUtils;

/**
 * 执行shell命令工具类
 */
public class Cmd {

    /** 标记 */
    private static String TAG = "cloud.cmd";

    /**
     * 私有构造方法
     */
    private Cmd() {

    }

    /**
     * 以root权限运行
     *
     * @param cmdStr
     * @return
     */
    public static boolean runAsRoot(String cmdStr) {
        try {
            boolean isRoot = ShellUtils.checkRootPermission();
            Log.i(TAG, "RUN : " + cmdStr + " Permission : " + isRoot);
            ShellUtils.CommandResult result = ShellUtils.execCommand(cmdStr, isRoot);

            if (result.result == 0) {
                return true;
            } else {
                Log.e(TAG, "error Msg: " + result.errorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     */
    public static void remountSystem() {
        runAsRoot("mount -o rw,remount /system");
    }

    /**
     *
     */
    public static void remountSdcard() {
        String sdcard = getSdcardMountPoint();
        runAsRoot("mount -o rw,remount " + sdcard);
    }

    /**
     * @return
     */
    public static String getSdcardMountPoint() {
        String sdcard = "/mnt/sdcard";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdcard = Environment.getExternalStorageDirectory().toString();
        }
        return sdcard;
    }

}
