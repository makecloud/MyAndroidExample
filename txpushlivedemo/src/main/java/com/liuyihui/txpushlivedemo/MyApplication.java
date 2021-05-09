package com.liuyihui.txpushlivedemo;

import android.app.Application;

import com.tencent.rtmp.TXLiveBase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/bdd4f28412e4fa0cc8599acc097c1159/TXLiveSDK" +
                ".licence"; // 获取到的 licence url
        String licenceKey = "458c6faff2b7ecca6809dc2984862c1c"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
    }
}