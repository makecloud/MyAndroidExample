package com.liuyihui.networkcontrol;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * wifi 控制工具类
 * Created by liuyi on 2018/12/24.
 */

public class WifiControlUtil {
    private final static String TAG = "WifiControlUtil";

    public enum EWifiCipherType {
        WEP, WPA, NONE, WIFICIPHER_INVALID
    }

    /**
     * 打开wifi
     *
     * @param context 上下文
     * @return wifi是否已打开
     */
    public static boolean openWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.setWifiEnabled(true);//已经开启状态返回true;未开启状态开启成功返回true;开启失败返回false;
    }

    /**
     * 打开wifi
     *
     * @param context 上下文
     * @return 成功与否
     */
    public static boolean closeWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.setWifiEnabled(false);//已经开启状态返回true;未开启状态开启成功返回true;开启失败返回false;
    }

    /**
     * 连接指定wifi
     *
     * @param context 上下文
     * @param ssid wifi名字
     * @param password 密码
     */
    public static void connectToSpecifiedWifi(Context context, String ssid, String password, EWifiCipherType eWifiCipherType) {
        openWifi(context);
        //
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        //wifi配置对象
        WifiConfiguration config;
        //查询已连接过的配置. 处理已连接过情况
        if (wifiManager != null) {
            List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
            if (existingConfigs != null) {
                for (WifiConfiguration existingConfig : existingConfigs) {
                    if (existingConfig == null)
                        continue;
                    if (existingConfig.SSID.equals("\"" + ssid + "\"")  /*&&  existingConfig.preSharedKey.equals("\""  +  password  +  "\"")*/) {
                        //如果连接过，移除。
                        wifiManager.removeNetwork(existingConfig.networkId);
                        break;
                    }
                }
            }
        }
        //新建配置对象
        config = new WifiConfiguration();
        //配置对象属性处理：清除配置
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        //配置对象处理：配置ssid
        config.SSID = "\"" + ssid + "\"";
        //配置对象处理：设置一些属性
        // 分三种情况配置,没有密码,用wep加密,用wpa加密
        // 1.没有密码情况的配置
        if (eWifiCipherType == EWifiCipherType.NONE) {// NONE wifiCong.hiddenSSID = false;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        //  WEP加密情况配置
        else if (eWifiCipherType == EWifiCipherType.WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);//不要以为set的内容会覆盖
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.wepTxKeyIndex = 0;
        }
        //  WPA加密情况配置
        else if (eWifiCipherType == EWifiCipherType.WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);//不要以为set的内容会覆盖
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        //配置添加进wifi,然后启用该配的连接
        int netId = config.networkId;
        Log.d(TAG, "" + netId);
        netId = wifiManager.addNetwork(config);
        Log.d(TAG, "" + netId);
        boolean enabled = wifiManager.enableNetwork(netId, true);
        Log.d(TAG, "enableNetwork status enable=" + enabled);


    }

    /**
     * 查看以前是否也配置过这个网络
     *
     * @param context 山上文
     * @param SSID wifi名字
     * @return 已存在的配置
     */
    private WifiConfiguration isExsits(Context context, String SSID) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }
}
