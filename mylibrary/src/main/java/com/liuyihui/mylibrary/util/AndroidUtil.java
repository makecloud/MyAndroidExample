package com.liuyihui.mylibrary.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;


import com.liuyihui.mylibrary.exception.OohlinkException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 安卓系统相关工具类
 * Created by liuyh on 2016/11/9.
 */

public class AndroidUtil {
    public static final String TAG = "cloud.AndroidUtil";
    public static final String osType = "2";//系统类型 2：android
    public static final String deviceModel = Build.MODEL;//手机型号
    public static final String osVersion = Build.VERSION.RELEASE;//android版本

    /**
     * 获取androidId
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        String android_id = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return android_id;
    }

    /**
     * 根据IP获取本地Mac
     * 作为设备唯一标识
     *
     * @param context
     * @return
     * @throws OohlinkException
     */
    public static String getLocalMacAddressFromIp(Context context) throws OohlinkException {
        String mac_s = "";
        try {
            byte[] mac;
            String localIpAdress = getLocalIpAddress();
            InetAddress inetAddress = InetAddress.getByName(localIpAdress);
            NetworkInterface ne = NetworkInterface.getByInetAddress(inetAddress);
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "获取mac失败");
            throw new OohlinkException("请检查网络连接");
        }

        return mac_s;
    }

    /**
     * 获取本地Mac子方法
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs.append("0").append(stmp);
            } else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    @Nullable
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.e("WifiPreferenceIpAddress", ex.toString());
        }
        return null;
    }


    /**
     * 获取app版本名
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }


    /**
     * 获取app版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


    /**
     * 尝试打开wifi获取wifi网卡的mac
     *
     * @param internal 尝试次数
     * @param context 上下文
     * @return
     */
    public static String getMacFromDevice(int internal, Context context) {
        String mac = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mac = tryGetMAC(wifiManager);
        if (!(mac == null)) {
            return mac;
        }

        //获取失败，尝试打开wifi获取
        boolean isOkWifi = tryOpenMAC(wifiManager);
        for (int index = 0; index < internal; index++) {
            //如果第一次没有成功，第二次做100毫秒的延迟。
            if (index != 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mac = tryGetMAC(wifiManager);
            if (!(mac == null)) {
                break;
            }
        }

        //尝试关闭wifi
        if (isOkWifi) {
            tryCloseMAC(wifiManager);
        }
        return mac;
    }

    //尝试获取MAC地址
    private static String tryGetMAC(WifiManager manager) {
        WifiInfo wifiInfo = manager.getConnectionInfo();
        if (wifiInfo == null || null == wifiInfo.getMacAddress()) {
            return null;
        }
        String mac = wifiInfo.getMacAddress().replaceAll(":", "").trim().toUpperCase();
//        mac = formatIdentify(mac);
        return mac;
    }

    //尝试打开wifi
    private static boolean tryOpenMAC(WifiManager manager) {
        boolean softOpenWifi = false;
        int state = manager.getWifiState();
        if (state != WifiManager.WIFI_STATE_ENABLED && state != WifiManager.WIFI_STATE_ENABLING) {
            manager.setWifiEnabled(true);
            softOpenWifi = true;
        }
        return softOpenWifi;
    }

    //尝试关闭wifi
    private static void tryCloseMAC(WifiManager manager) {
        manager.setWifiEnabled(false);
    }


}
