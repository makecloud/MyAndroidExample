package com.liuyihui.networkcontrol.socketTransmit;

import android.text.TextUtils;
import android.util.Log;

import com.liuyihui.mylibrary.util.netinfo.NetWork;
import com.liuyihui.mylibrary.util.netinfo.NetworkUtils;

import java.util.List;

public class IpUtil {
    private static final String TAG = "IpUtil";

    /**
     * @return broadcast ip
     */
    public static String getBrocastIp() {
        String ip = null;
        String broadCastIp = null;
        //获取本机当前启用网络的ip
        List<NetWork> networks = NetworkUtils.getNetworks();
        for (NetWork netWork : networks) {
            if (netWork.getIsEnable()) {
                ip = netWork.getIpv4();
                broadCastIp = netWork.getBroadCastIp();
                if (!TextUtils.isEmpty(ip))
                    break;
            }
        }

        if (TextUtils.isEmpty(ip)) {
            Log.e(TAG, "receiveDatagramPacket: 未联网");
        }
        if (TextUtils.isEmpty(broadCastIp)) {
            Log.e(TAG, "receiveDatagramPacket: 无法获取广播ip");
        }
        return broadCastIp;
    }

    /**
     * @return broadcast ip
     */
    public static String getMyIp() {
        String ip = null;
        String broadCastIp = null;
        //获取本机当前启用网络的ip
        List<NetWork> networks = NetworkUtils.getNetworks();
        for (NetWork netWork : networks) {
            if (netWork.getIsEnable()) {
                ip = netWork.getIpv4();
                broadCastIp = netWork.getBroadCastIp();
                if (!TextUtils.isEmpty(ip))
                    break;
            }
        }

        if (TextUtils.isEmpty(ip)) {
            Log.e(TAG, "receiveDatagramPacket: 未联网");
        }
        if (TextUtils.isEmpty(broadCastIp)) {
            Log.e(TAG, "receiveDatagramPacket: 无法获取广播ip");
        }
        return ip;
    }
}
