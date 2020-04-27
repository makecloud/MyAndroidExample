package com.liuyihui.mylibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.FLAG_SHOW_UI;

/**
 * Created by gaowen on 2017/7/6.
 */

public class DeviceUtils {

    private static final String MAC_ADDRESS = "mac_address";


    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByWifiInfo(context);
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {

        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                String niName = ni.getName();
                if ("eth0".equalsIgnoreCase(niName)) {
                    byte[] macBytes = ni.getHardwareAddress();
                    if (macBytes != null && macBytes.length > 0) {
                        StringBuilder res1 = new StringBuilder();
                        for (byte b : macBytes) {
                            res1.append(String.format("%02x:", b));
                        }
                        return res1.deleteCharAt(res1.length() - 1).toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo(Context context) {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                String niName = ni.getName();
                if ("wlan0".equalsIgnoreCase(niName)) {
                    byte[] macBytes = ni.getHardwareAddress();
                    if (macBytes != null && macBytes.length > 0) {
                        StringBuilder res1 = new StringBuilder();
                        for (byte b : macBytes) {
                            res1.append(String.format("%02x:", b));
                        }
                        return res1.deleteCharAt(res1.length() - 1).toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     *
     * @return MAC地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCommand("getprop wifi.interface", false);
        if (result.result == 0) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCommand("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    if (result.successMsg != null) {
                        return result.successMsg;
                    }
                }
            }
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 重启
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void reboot(Context context) {
        ShellUtils.execCommand("reboot", true);
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        context.sendBroadcast(intent);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        pm.reboot(null);
    }

    /**
     * 关机
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void shutdown(Context context) {
        ShellUtils.execCommand("reboot -p", true);
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        try {
            if (tm != null) {
                imei = tm.getDeviceId();
            }
        } catch (SecurityException e) {
        }
        return imei;
    }

    public static String getIccid(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = "";
        try {
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

    public static void connectDeviceUseTcp() {
        ShellUtils.execCommand("stop adbd", true);
        ShellUtils.execCommand("setprop service.adb.tcp.port 5555", true);
        ShellUtils.execCommand("start adbd", true);
    }

    public static void setVolume(int volume,Context context) {
        AudioManager mAudioManager =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume > 0) {
            volume = volume * maxVolume / 10;
            if (volume == 0) {
                volume += 1;
            }
        }
        if (volume != currentVolume) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
                                          AudioManager.FLAG_PLAY_SOUND);
        }
    }

    public static void adjustRaiseVolume(Context context) {
        AudioManager mAudioManager =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, ADJUST_RAISE, FLAG_SHOW_UI);
    }

    public static void adjustLowerVolume(Context context) {
        AudioManager mAudioManager =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, ADJUST_LOWER, FLAG_SHOW_UI);
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                                         Settings.Secure.ANDROID_ID);
    }

    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}