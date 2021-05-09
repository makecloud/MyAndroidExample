package com.oohlink.messagepush;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.commonsdk.statistics.common.DeviceConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private TextView deviceTokenTextView;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Constant.receivedMessageList);
        deviceTokenTextView = findViewById(R.id.deviceToken);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //显示deviceToken
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!TextUtils.isEmpty(Constant.deviceToken)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                deviceTokenTextView.setText(Constant.deviceToken);
                            }
                        });
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //显示收到的推送
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        String[] deviceInfo = getTestDeviceInfo(this);
        List<String> deviceInfoList = Arrays.asList(deviceInfo);
        Log.d(TAG, "onCreate: " + deviceInfoList);


        permissionHandle();
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
        return deviceInfo;
    }

    public void showNotification(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /*NotificationShower
                        .getInstance()
                        .showNotificationBar(MainActivity.this, "press button");*/
                NotificationShower
                        .getInstance()
                        .showSnackBar(findViewById(R.id.rootLayout), "jfkdsjfkl");
                Log.d(TAG, "run: ");
            }
        }).start();

    }

    public void permissionHandle() {
        if (Build.VERSION.SDK_INT >= 23) {
            //需要的权限做一个列表，方便遍历检测
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_PHONE_STATE,
                                                Manifest.permission.VIBRATE};

            //遍历检测
            List<String> notGrantedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    notGrantedPermissions.add(permission);
                }
            }

            //
            if (notGrantedPermissions.size() > 0) {
                requestPermissions(notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]), 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            for (int i = 0; i < permissions.length; i++) {
                Log.d(TAG, "onRequestPermissionsResult: " + permissions[i]);
            }

            for (int i = 0; i < grantResults.length; i++) {
                Log.d(TAG, "onRequestPermissionsResult: " + grantResults[i]);
            }


            for (int i = 0; i < permissions.length; i++) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    String msg = "权限:" + permissions[i] + " 得到授权";
                    Toast
                            .makeText(this, msg, Toast.LENGTH_SHORT)
                            .show();
                    Log.d(TAG, "onRequestPermissionsResult: " + msg);
                } else {
                    String msg = "权限:" + permissions[i] + " 被拒绝";
                    Toast
                            .makeText(this, msg, Toast.LENGTH_SHORT)
                            .show();
                    Log.d(TAG, "onRequestPermissionsResult: " + msg);
                }
            }
        }


    }
}
