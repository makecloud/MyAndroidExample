package com.liuyihui.client.myexample.demo18_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gaowen on 2017/9/10.
 */

public class IntentServiceDemoService extends IntentService {
    private static final String TAG = "PlayerCleaner";
    public static final long DAY = 86400000;

    public IntentServiceDemoService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }

}
