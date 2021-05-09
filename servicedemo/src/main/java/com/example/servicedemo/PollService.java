package com.example.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 服务
 * Created by liuyh on 2017/5/22.
 */

public class PollService extends IntentService {

    private static final String TAG = "PollService";

    public PollService() {
        super(TAG);
    }

    /**
     * 每次startIntent都会用传递的intent回调此方法，即使已经服务在运行了，供处理传递的intent
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "received an intent:" + intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }
}
