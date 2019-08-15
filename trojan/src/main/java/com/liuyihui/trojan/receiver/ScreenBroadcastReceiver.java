package com.liuyihui.trojan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * screen状态广播接收者
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
            Toast.makeText(context, "开屏", Toast.LENGTH_SHORT).show();
            Log.i("liuyihui", "开屏");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            Toast.makeText(context, "锁屏", Toast.LENGTH_SHORT).show();
            Log.i("liuyihui", "锁屏");
        }
    }
}
