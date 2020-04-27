package com.liuyihui.testroot;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyihui.testroot.netinfo.NetWork;
import com.liuyihui.testroot.netinfo.NetworkUtils;

import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    private TextView isRootTextView;
    private TextView androidIdTextView;
    private TextView macAddressTextView;
    private TextView androidVersionTextView;
    private TextView androidImeiTextView;
    private TextView androidIccidTextView;
    private TextView androidFactoryTextView;
    private TextView androidSystemBuildVersionTextView;
    private TextView netInfoTextView;

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
        androidSystemBuildVersionTextView = findViewById(R.id.android_build_display);
        netInfoTextView = findViewById(R.id.netinfo);

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

        //show buildDisplay 构建版本号
        try {
            String buildDisplay = DeviceUtils.getBuildDisplay();
            androidSystemBuildVersionTextView.append(buildDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadNetWorkInfo();
        //getPermission(this, Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE);
    }

    private void loadNetWorkInfo() {
        //网络信息
        netInfoTextView.append(NetworkUtils.getNetworkType(this).name());
        netInfoTextView.append("\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NetWork> networks = NetworkUtils.getNetworks();
                if (networks != null) {
                    for (final NetWork n : networks)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                netInfoTextView.append(String.format("%s=%s\n",
                                                                     n.getType(),
                                                                     n.getIpv4()));
                                netInfoTextView.append(String.format("broadcast=%s\n",
                                                                     n.getBroadCastIp()));
                            }
                        });
                }
            }
        }).start();
    }


    /**
     * 以下4个方法，使用PermissionGen 框架，针对android 6.x sdk 获取系统某些权限
     * by liuyihui
     *
     * @param activity    活动实例
     * @param permissions 不定长权限数组
     */
    public void getPermission(Activity activity, String... permissions) {
        PermissionGen.with(activity).addRequestCode(100).permissions(permissions).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(MainActivity.this,
                                                 requestCode,
                                                 permissions,
                                                 grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void onSuccess() {
        Toast.makeText(this, "get success", Toast.LENGTH_SHORT).show();
        this.loadNetWorkInfo();
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取设备读写权限失败", Toast.LENGTH_SHORT).show();
    }
}