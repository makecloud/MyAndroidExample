package com.liuyihui.testroot;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyihui.mylibrary.util.MD5Util;
import com.liuyihui.mylibrary.util.ScreenUtil;
import com.liuyihui.testroot.httprepository.HttpApi;
import com.liuyihui.testroot.netinfo.NetWork;
import com.liuyihui.testroot.netinfo.NetworkUtils;
import com.liuyihui.testroot.utils.AndroidUtil;
import com.liuyihui.testroot.utils.DeviceUtils;

import java.io.File;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 设备检测工具
 */
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
    private TextView displayInfoTextView;

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
        displayInfoTextView = findViewById(R.id.displayInfo);

        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE,
                          Manifest.permission.ACCESS_COARSE_LOCATION);
        }

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

        //show screen dip info
        float density = getResources().getDisplayMetrics().density;
        float densityDpi = getResources().getDisplayMetrics().densityDpi;
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        float heightPixels = getResources().getDisplayMetrics().heightPixels;
        float widthPixels = getResources().getDisplayMetrics().widthPixels;
        int UIWidth = ScreenUtil.getUIWidth(this);
        int UIHeight = ScreenUtil.getUIHeight(this);
        int physicalHeight = ScreenUtil.getPhysicalHeight(this);
        int physicalWidth = ScreenUtil.getPhysicalWidth(this);
        int onedpPx = ScreenUtil.dip2px(this, 1);
        String dpInfo = String.format("density=%s,1dp=%spx,densityDpi=%s,xdpi=%s,ydpi=%s," +
                                              "scaleDensity=%s,heightPx=%s,widthPx=%s," +
                                              "UIWidth=%s,UIHeight=%s,physicalWidthPx=%s," +
                                              "physicalHeightPx=%s",
                                      density,
                                      onedpPx,
                                      densityDpi,
                                      xdpi,
                                      ydpi,
                                      scaledDensity,
                                      heightPixels,
                                      widthPixels,
                                      UIWidth,
                                      UIHeight,
                                      physicalWidth,
                                      physicalHeight);
        displayInfoTextView.setText(dpInfo);
    }

    private void loadNetWorkInfo() {
        //网络信息
        netInfoTextView.append("net type:" + NetworkUtils.getNetworkType(this).name());
        netInfoTextView.append("\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NetWork> networks = NetworkUtils.getSimpleNetworks();
                if (networks != null) {
                    for (final NetWork netWork : networks)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                netInfoTextView.append(String.format("interfaceName:%s ipv4:%s " +
                                                                             "enable:%s",
                                                                     netWork.getType(),
                                                                     netWork.getIpv4(),
                                                                     netWork.getIsEnable()));
                                netInfoTextView.append(String.format(" broadcastIp:%s",
                                                                     netWork.getBroadCastIp()));
                                netInfoTextView.append("\n");
                            }
                        });
                }
            }
        }).start();
    }

    public void evaluateMd5(View view) {
        TextView md5tv = findViewById(R.id.md5value);
        File file = new File("/sdcard/oohlink/player/.screen/E94DEFEE0A1820286F051B54413FF42B");
        File file2 = new File("/sdcard/com.liuyihui" + ".networkcontrol" +
                                      "/E94DEFEE0A1820286F051B54413FF42B");
        md5tv.setText(MD5Util.getFileMD5String(file2));
    }

    public void downloadAFile(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://res.oohlink" + ".com/material/645" +
                        "/4F4709B19FB71DD85A406B2D0A0720C8.mp4";
                String storePath = "/sdcard/oohlink/player/" + ".screen" +
                        "/E94DEFEE0A1820286F051B54413FF42B";
                HttpApi.getInstance().downFile(url, storePath);
            }
        }).start();
    }


    public void startSettings(View view) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    public void startDevelopSetting(View view) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        startActivity(intent);
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
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
