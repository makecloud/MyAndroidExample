package com.wits.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ShutdownService extends Service {
    private static final String TAG = "ShutdownService";
    Timer timer = new Timer();

    public ShutdownService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取关机时间
        String shutdownTime = intent.getStringExtra("shutdownTime");
        Log.i(TAG, shutdownTime);
        if (TextUtils.isEmpty(shutdownTime)) {
            return Service.START_STICKY;
        }

        //计算关机倒计时
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(shutdownTime.split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(shutdownTime.split(":")[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(shutdownTime.split(":")[2]));
        long countDown = (calendar.getTimeInMillis() - System.currentTimeMillis());
        Log.i(TAG, "关机倒计时: " + countDown / 1000);
        if (countDown <= 0) {
            return Service.START_STICKY;
        }
        //开始倒计时
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intentSetOff = new Intent();
                intentSetOff.setAction("wits.com.simahuan.shutdown");
                sendBroadcast(intentSetOff);
            }
        }, countDown);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
