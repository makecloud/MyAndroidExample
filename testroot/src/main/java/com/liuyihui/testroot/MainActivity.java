package com.liuyihui.testroot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView isRootTextView;
    private TextView androidIdTextView;
    private TextView macAddressTextView;
    private TextView androidVersionTextView;
    private TextView androidImeiTextView;
    private TextView androidIccidTextView;
    private TextView androidFactoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRootTextView = findViewById(R.id.is_root);
        androidIdTextView = findViewById(R.id.android_id);
        macAddressTextView = findViewById(R.id.mac_address);
        androidVersionTextView = findViewById(R.id.android_version);
        androidImeiTextView = findViewById(R.id.android_imei);
        androidIccidTextView = findViewById(R.id.android_iccid);
        androidFactoryTextView = findViewById(R.id.android_factory);

        //
        boolean isroot = ReallyShellUtil.canRunRootCommands();
        if (isroot) {
            isRootTextView.setTextColor(Color.GREEN);
            isRootTextView.append("app可获得root权限!");
            Toast.makeText(this, "设备已被root", Toast.LENGTH_LONG).show();
        } else {
            isRootTextView.setTextColor(Color.RED);
            isRootTextView.append("app无法获得root权限 --");
        }

        //show android id
        try {
            String androidId = AndroidUtil.getAndroidID(this);
            androidIdTextView.append(androidId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //show mac address
        try {
            String macAddress = MacAddressUtil.getMacAddress(this);
            macAddressTextView.append(macAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //show android version
        try {
            String androidVersion = AndroidUtil.osVersion;
            String deviceModel = AndroidUtil.deviceModel;
            androidVersionTextView.append(androidVersion + " " + deviceModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //show imei
        try {
            String imei = DeviceUtils.getIMEI(this);
            androidImeiTextView.append(imei);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //show iccid
        try {
            String iccid = DeviceUtils.getIccid(this);
            androidIccidTextView.append(iccid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //show factory
        try {
            String factory = DeviceUtils.getManufacturer();
            androidFactoryTextView.append(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
