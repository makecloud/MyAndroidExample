package com.example.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * intent service demo
 * <p>
 * 在工作线程 队列式处理intent任务
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
