package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 我的android服务
 * Created by liuyh on 2017/5/22.
 */

public class MyService extends Service {
    private final static String TAG = "MyService";
    private DownloadBinder binder;
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "myservice onBind .");
        binder = new DownloadBinder();
        return binder;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "myservice onCreate .");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "myservice onStartCommand .");
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy() {
        Log.i(TAG, "myservice onDestroy .");
        super.onDestroy();
    }
    
    /**
     * 桥梁对象,这个对象中写一些对服务逻辑的控制性逻辑，启动、停止等。
     * activity绑定到这个服务的时候（回调onbind方法，创建binder对象，并返回句柄），能获取到这个对象的句柄，以实现在activity中通过binder对象操作服务
     */
    public class DownloadBinder extends Binder {
        private static final String TAG = "DownloadBinder";
        
        public void startDownload() {
            Log.i(TAG, "startDownload() executed .");
        }
        
        public int getProgress() {
            Log.i(TAG, "getProgress() executed .");
            return 0;
        }
    }
}
