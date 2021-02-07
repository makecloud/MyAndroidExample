package com.liuyihui.networkcontrol;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuyihui.mylibrary.util.ToastUtil;
import com.liuyihui.networkcontrol.devicenetwork.ApManager;
import com.liuyihui.networkcontrol.devicenetwork.WifiControlUtil;
import com.liuyihui.networkcontrol.http.TestHttpApi;
import com.liuyihui.networkcontrol.httpdownload.generaldownload.HttpApi;
import com.liuyihui.networkcontrol.httpdownload.queueDownload.DownInfo;
import com.liuyihui.networkcontrol.httpdownload.queueDownload.DownloadManager;
import com.liuyihui.networkcontrol.socketTransmit.SocketTransmitDemoActivity;
import com.liuyihui.networkcontrol.systemProcessedDownload.DownloadUseSystemService;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * wifi、热点、网络demo
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private Button openHotspotButton;
    private Button connectWifiButton;

    private String appDir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHotspotButton = findViewById(R.id.open_hotspot);
        connectWifiButton = findViewById(R.id.connect_wifi);


        //permission
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        //创建目录
        appDir =
                Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APPLICATION_ID;
        File appDirFile = new File(appDir);
        if (!appDirFile.exists()) {
            appDirFile.mkdir();
        }

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


    }

    //测试我自己写的普通方式下载文件
    public void testGeneralDownload(View view) {
        String url = "https://ygsd-test.oss-cn-beijing.aliyuncs" + ".com/material/40" +
                "/BFA91EE06E2EE723A2C08B4B656605D8.mp4";

        String url2 = "http://res.oohlink.com/material/645/4F4709B19FB71DD85A406B2D0A0720C8" +
                ".mp4";

        String filePath = getExternalFilesDir(null).getAbsolutePath() + "/downloaded.mp4";

        HttpApi.getInstance().downFile(url2, filePath);

    }

    //测试播控内写的端点续传下载
    public void testPlayerSustainDown(View view) {
        String matUrl = "http://res.oohlink.com/material/645/4F4709B19FB71DD85A406B2D0A0720C8.mp4";
        final String matMD5 = "E94DEFEE0A1820286F051B54413FF42B";
        String contentType = null;
        final Integer matType = 2;
        final String savePath = appDir + File.separator + matMD5;

        DownInfo downInfo = new DownInfo();
        downInfo.setFileUrl(matUrl);
        downInfo.setFileMd5(matMD5);
        downInfo.setContentType(null);
        downInfo.setType(DownInfo.FileType.SCREEN_MATERIAL);
        downInfo.setSavePathName(savePath);
        downInfo.setDownLoadingListener(new DownInfo.DownLoadingListener() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: download completed");
            }

            @Override
            public void onProgress(DownInfo downInfo) {
                //测试发现downInfo.getDownloadSize()是下载进度
                System.out.print("onProgress: ");
                Log.d(TAG,
                      "onProgress:  readSize=" + downInfo.getReadSize() + ",downloadSize=" + downInfo.getDownloadSize());

            }
        });

        DownloadManager.getInstance().enqueneDownTaskByInfo(downInfo);
    }

    //todo 测试使用系统下载服务下载
    public void testSystemProcessDownload(View view) {
        // TODO: 2020-05-19
        DownloadUseSystemService.getInstance().downloadFile();
    }

    public void testConcat(View view) {
        TestHttpApi.getInstance().getPlayerInfoDoubleSource();
    }

    /**
     * socket传送
     *
     * @param view
     */
    public void testSocketTransmit(View view) {
        startActivity(new Intent(this, SocketTransmitDemoActivity.class));
    }

    public void callHttpUrl(View view) {
        TestHttpApi.getInstance().getPlayerInfo();
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
        //ToastUtil.toast("已获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        ToastUtil.toast("获取设备读写权限失败");
    }

}
