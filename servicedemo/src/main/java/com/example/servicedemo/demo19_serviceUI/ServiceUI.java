package com.example.servicedemo.demo19_serviceUI;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.servicedemo.R;


/**
 * 使用服务显示界面
 * Created by liuyh on 2017/6/6.
 */

public class ServiceUI extends Service {
    private static final String TAG = "ServiceUI";
    private WindowManager windowManager;
    private View v;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() executed");
        IBinder binder = new MyBinder();
        return binder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate() executed . ");
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        showView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() executed.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy() executed .");
        windowManager.removeViewImmediate(v);
        super.onDestroy();
    }

    private int showView() {
        v = View.inflate(this, R.layout.service_example19, null);


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.height = 800;
        layoutParams.width = 500;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


        //test param
        /*layoutParams = new WindowManager.LayoutParams(500, 800,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | FLAG_WATCH_OUTSIDE_TOUCH
                        | FLAG_HARDWARE_ACCELERATED
                        | FLAG_NOT_FOCUSABLE,
                PixelFormat.OPAQUE);*/


        windowManager.addView(v, layoutParams);
        return 1;
    }

    public class MyBinder extends Binder {
        private final String TAG = "MyBinder_example19";

        public void hideView() {
            v.setVisibility(View.GONE);
        }

        public void resumeView() {
            v.setVisibility(View.VISIBLE);
        }

    }
}
