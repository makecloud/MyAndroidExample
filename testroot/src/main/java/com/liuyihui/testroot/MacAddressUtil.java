package com.liuyihui.testroot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MacAddressUtil {
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
        return "为获取到";
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
                if ("eth0".equalsIgnoreCase(niName) || "wlan0".equalsIgnoreCase(niName)) {
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
            @SuppressLint("WifiManagerLeak") WifiManager wifi =
                    (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
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
        ShellUtils.CommandResult result = null;
        try {
            result = ShellUtils.execCommand("getprop wifi.interface", false);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }
}
