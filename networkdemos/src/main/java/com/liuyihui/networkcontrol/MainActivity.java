package com.liuyihui.networkcontrol;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liuyihui.mylibrary.io.OohlinkSerializer;
import com.liuyihui.mylibrary.util.FileUtil;
import com.liuyihui.mylibrary.util.ToastUtil;
import com.liuyihui.networkcontrol.devicenetwork.ApManager;
import com.liuyihui.networkcontrol.devicenetwork.WifiControlUtil;
import com.liuyihui.networkcontrol.download.HttpApi;

import java.io.IOException;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * wifi、热点 控制
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    Button openHotspotButton;
    Button connectWifiButton;
    Button downFileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHotspotButton = findViewById(R.id.open_hotspot);
        connectWifiButton = findViewById(R.id.connect_wifi);
        downFileButton = findViewById(R.id.downfile);

        //通过按钮事件设置热点
        openHotspotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试有效.
                ApManager.openAp(MainActivity.this, "Test_open_hotspot", "12345678");
            }
        });

        //连接指定wifi
        connectWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                WifiControlUtil.connectToSpecifiedWifi(MainActivity.this,
                                                       "oohlink_office",
                                                       "oohlink.1306",
                                                       WifiControlUtil.EWifiCipherType.WPA);
            }
        });

        //下载文件按钮
        downFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "https://ygsd-test.oss-cn-beijing.aliyuncs" +
                            ".com/material/40/BFA91EE06E2EE723A2C08B4B656605D8.mp4";
                    String filePath = OohlinkSerializer.getAppSDPath(MainActivity.this) + "aaa";
                    HttpApi.getInstance().downFile(url, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //permission
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
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
        //ToastUtil.toast("已获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        ToastUtil.toast("获取设备读写权限失败");
    }

}
