package com.liuyihui.client.myexample.example18_service;

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
    
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "received an intent:" + intent);
    }
}
