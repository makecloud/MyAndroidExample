package com.oohlink.messagepush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class NotificationShower {
    private NotificationShower() {

    }


    private static class InstanceHolder {
        private static NotificationShower notificationShower = new NotificationShower();
    }

    public static NotificationShower getInstance() {
        return InstanceHolder.notificationShower;
    }

    public void showNotificationBar(Context context, String content) {

        //设置跳转
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //通知定义
        Notification notification = new NotificationCompat.Builder(context,
                                                                   Constant.CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("title")
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();


        //弹通知
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(1, notification);
    }

    public void showSnackBar(View parentView, String content) {
        Snackbar snackbar = Snackbar.make(parentView, content, Snackbar.LENGTH_LONG);
        snackbar.getView()
                .setBackgroundColor(Color.GRAY);
        snackbar.show();
    }
}
