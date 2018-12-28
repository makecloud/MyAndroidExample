package com.liuyihui.networkcontrol;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * 热点管理类
 */
public class ApManager {
    private static final String TAG = "ApManager";

    /**
     * 判断热点是否开启
     *
     * @param context 上下文
     * @return 热开是否开启
     */
    public static boolean isApOn(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(wifimanager);
        } catch (Throwable ignored) {
            Log.e(TAG, "获取热点状态失败", ignored);
        }
        return false;
    }

    /**
     * 关闭WiFi
     *
     * @param context 上下文
     */
    private static void closeWifi(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (wifimanager.isWifiEnabled()) {
            wifimanager.setWifiEnabled(false);
        }
    }

    /**
     * 开启热点
     *
     * @param context 上下文
     * @param SSID 热点名称
     * @param password 热点密码
     * @return
     */
    public static boolean openAp(Context context, String SSID, String password) {
        if (TextUtils.isEmpty(SSID)) {
            return false;
        }
        //如果wifi开着呢, 关闭wifi
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (wifimanager.isWifiEnabled()) {
            wifimanager.setWifiEnabled(false);
        }
        //
        WifiConfiguration wifiConfiguration = makeApConfig(SSID, password);
        try {
            //如果热点已经是开启状态
            if (isApOn(context)) {
                wifimanager.setWifiEnabled(false);
                //closeAp(context);
                Toast.makeText(context, "热点已经开启,无须再次开启.", Toast.LENGTH_SHORT).show();
                return false;
            }
            //开启热点
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifimanager, wifiConfiguration, true);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "开启热点失败", e);
        }
        return false;
    }

    /**
     * 关闭热点
     *
     * @param context 上下文
     */
    public static void closeAp(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifimanager, null, false);
        } catch (Exception e) {
            Log.e(TAG, "关闭热点失败", e);
        }
    }

    /**
     * 获取开启热点后的IP地址
     *
     * @param context 上下文
     * @return ip地址
     */
    public static String getHotspotLocalIpAddress(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifimanager.getDhcpInfo();
        if (dhcpInfo != null) {
            int address = dhcpInfo.serverAddress;
            return ((address & 0xFF)
                    + "." + ((address >> 8) & 0xFF)
                    + "." + ((address >> 16) & 0xFF)
                    + "." + ((address >> 24) & 0xFF));
        }
        return null;
    }

    /**
     * 设置热点
     *
     * @param SSID 热点名称
     * @param password 密码
     * @return wifi配置对象
     */
    private static WifiConfiguration makeApConfig(String SSID, String password) {
        if (TextUtils.isEmpty(password)) {
            return null;
        }

        WifiConfiguration config = new WifiConfiguration();
        config.SSID = SSID;
        config.preSharedKey = password;
//        config.hiddenSSID = true;
        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return config;
    }

    /**
     * 开启热点
     */
    public static void openHotspot(Context context, String ssid, String password) {
        closeWifi(context);
        if (isApOn(context)) {
            closeAp(context);
        }
        openAp(context, ssid, password);
    }

}