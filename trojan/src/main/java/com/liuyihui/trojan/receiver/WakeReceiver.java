package com.liuyihui.trojan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liuyihui.trojan.aliveservice.MonitorService;
import com.liuyihui.trojan.util.ServiceUtils;

/**
 * Created by gaowen on 2017/9/7.
 */

public class WakeReceiver extends BroadcastReceiver {

    public final static String MONITOR_ACTION = "com.cnit.cloudwindow";
    private final static String MONITOR_SERVICE_NAME =
            "com.cnit.cloudwindow.aliveservices.MonitorService";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (MONITOR_ACTION.equals(action)) {
            if (!ServiceUtils.isServiceRunning(context, MONITOR_SERVICE_NAME)) {
                Intent wakeIntent = new Intent(context, MonitorService.class);
                context.startService(wakeIntent);
            }
        }
    }
}
