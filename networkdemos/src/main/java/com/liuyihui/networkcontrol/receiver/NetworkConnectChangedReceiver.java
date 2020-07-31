package com.liuyihui.networkcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.liuyihui.mylibrary.util.netinfo.NetworkUtils;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetWorkStateReceiver";
    private Boolean beforeNetworkConnected = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        //
        boolean isNetworkConnected = NetworkUtils.isNetworkConnected(context);
        Log.d(TAG,
              "NetWork:" + isNetworkConnected + ", before:" + beforeNetworkConnected);

        //网络从 未连接 变为 连接
        if (isNetworkConnected) {
            if (beforeNetworkConnected != null && !beforeNetworkConnected) {
                Log.d(TAG, "onReceive: 网络从 未连接 变为 连接");
            }
        }

        beforeNetworkConnected = isNetworkConnected;
    }

}