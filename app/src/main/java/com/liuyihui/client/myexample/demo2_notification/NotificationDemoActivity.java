package com.liuyihui.client.myexample.demo2_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.liuyihui.client.myexample.MainActivity;
import com.liuyihui.client.myexample.R;

/**
 * 通知 用例
 */
public class NotificationDemoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //创建渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_chat";
            String channelName = "新聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId,
                                                                  channelName,
                                                                  importance);
            notificationManager.createNotificationChannel(channel);
        }

    }

    //发通知
    public void notification(View view) {
        Log.d(TAG, "notification: ");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                                                                0,
                                                                new Intent(this,
                                                                           MainActivity.class),
                                                                0);
        Notification notification = new NotificationCompat.Builder(this, "channel_chat")
                .setContentTitle("biaoti")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("content text")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();

        notificationManager.notify(1, notification);


    }
}
