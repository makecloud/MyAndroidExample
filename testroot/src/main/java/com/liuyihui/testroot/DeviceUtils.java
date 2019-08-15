package com.liuyihui.testroot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by gaowen on 2017/7/6.
 */

public class DeviceUtils {

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                imei = tm.getDeviceId();
            }
        } catch (SecurityException e) {
        }
        return imei;
    }

    public static String getIccid(Context context) {
        String iccid = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                iccid = tm.getSimSerialNumber();
            }
        } catch (SecurityException e) {
        }
        return iccid;
    }

    public static Pair<Long, Long> getMeminfo() {
        String cmd_mem = "/proc/meminfo";// 系统内存信息文件
        long total_memory = 0;
        long free_memory = 0;
        try {
            FileReader localFileReader = new FileReader(cmd_mem);
            BufferedReader buf = new BufferedReader(localFileReader, 8192);
            String line1 = buf.readLine();
            String line2 = buf.readLine();
            if (!TextUtils.isEmpty(line1)) {
                // 获得系统总内存，单位是KB，乘以1024转换为Byte
                total_memory = Integer.valueOf(line1.split("\\s+")[1]).longValue() * 1024;
            }
            if (!TextUtils.isEmpty(line2)) {
                // 获得系统空闲内存，单位是KB，乘以1024转换为Byte
                free_memory = Integer.valueOf(line2.split("\\s+")[1]).longValue() * 1024;
            }

            buf.close();
        } catch (IOException e) {
            return null;
        }
        return new Pair(total_memory, total_memory - free_memory);
    }

    /**
     * 获取设备Model，可以用来判断设备型号
     */
    public static String getBuildModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取CPU的核数
     */
    public static int getProcessorNumber() {
        Runtime rt = Runtime.getRuntime();
        return rt.availableProcessors();
    }

    /**
     * 获取系统型号，eg：android_OS_4.1
     */
    public static String getVersionRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取SDK版本号
     */
    public static int getSdkInt() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机制造商信息
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }
}