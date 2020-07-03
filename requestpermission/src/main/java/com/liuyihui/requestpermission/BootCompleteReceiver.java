package com.liuyihui.requestpermission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);
        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();

        Intent start = new Intent(context, RequestPermissionActivity.class);
        context.startActivity(start);

    }
}
