package com.liuyihui.trojan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HdmiPluggedRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent receivedIt) {
        String action = receivedIt.getAction();
        if (action.equals("android.intent.action.HDMI_PLUGGED")) {
            boolean state = receivedIt.getBooleanExtra("state", false);
            if (state) {
                Toast.makeText(context, "插入hdmi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "拔出hdmi", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
