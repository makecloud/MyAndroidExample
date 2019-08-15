package com.liuyihui.trojan.aliveservice;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liuyihui.trojan.receiver.WakeReceiver;
import com.liuyihui.trojan.util.AppUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by gaowen on 2017/9/7.
 */

public class MonitorService extends Service {

    private static final String TAG = "MonitorService";
    private final static int GRAY_SERVICE_ID = 1001;
    public static final int MONITOR_TIME = 20;//20秒监控状态
    /**
     * 定时唤醒的时间间隔，1分钟
     */
    private final static int ALARM_INTERVAL = 1 * 60 * 1000;
    private final static int WAKE_REQUEST_CODE = 6666;

    private Disposable mDisposable;

    @Override
    public void onCreate() {
        startAlarmManager();
    }

    private void startAlarmManager() {
        //发送唤醒广播来促使挂掉的UI进程重新启动起来
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(WakeReceiver.MONITOR_ACTION);
        PendingIntent alarmPendingIntent =
                PendingIntent.getBroadcast(this, WAKE_REQUEST_CODE, alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                ALARM_INTERVAL, alarmPendingIntent);
    }

    private void stopPlayerMonitor() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            Log.d(TAG, "stopPlayerMonitor()");
            mDisposable.dispose();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        stopPlayerMonitor();
        mDisposable =
                Flowable.interval(MONITOR_TIME, MONITOR_TIME, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "isAppForeground():" + AppUtil.isAppForeground(MonitorService.this));
                                if (!AppUtil.isAppForeground(MonitorService.this)) {
                                    startActivity(AppUtil.getLaunchAppIntent(MonitorService.this, getPackageName()));
                                }
                            }
                        });
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID,
                    new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayerMonitor();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
