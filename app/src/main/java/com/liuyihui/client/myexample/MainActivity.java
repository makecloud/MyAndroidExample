package com.liuyihui.client.myexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liuyihui.client.myexample.demo10_photo_album.InvokeSysGalleryActivity;
import com.liuyihui.client.myexample.demo11_slideshow_banner.ShuffleImagesActivity;
import com.liuyihui.client.myexample.demo13_viewflipper.UseViewFlipperActivity;
import com.liuyihui.client.myexample.demo14_2_GridLayout.GridLayoutActivity;
import com.liuyihui.client.myexample.demo14_gridview.UseGridViewActivity;
import com.liuyihui.client.myexample.demo16_recyclerview.Example16Activity;
import com.liuyihui.client.myexample.demo17_DESencrypt.TestDesActivity;
import com.liuyihui.client.myexample.demo1_activity.Demo1Activity;
import com.liuyihui.client.myexample.demo1_activity.Example1_1Activity;
import com.liuyihui.client.myexample.demo20_activityGroup.ContainerActivity;
import com.liuyihui.client.myexample.demo21_framelayout_hierarchy.Example21Activity;
import com.liuyihui.client.myexample.demo2_notification.NotificationDemoActivity;
import com.liuyihui.client.myexample.demo4_use_camera.Example4Activity;
import com.liuyihui.client.myexample.demo6_windowShow_demos.Example6Activity;
import com.liuyihui.client.myexample.demo7_custom_view.Example7Activity;
import com.liuyihui.client.myexample.demo8_choose_city_demo.ChoseCityActivity;

import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import util.Cmd;
import util.ToastUtil;

/**
 * 主页、引导页
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE,
                          Manifest.permission.RECORD_AUDIO,
                          Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        //test
        subThreadWork();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    public void btn_eg1Click(View view) {
        startActivity(Demo1Activity.class);
    }

    public void btn_eg1_1Click(View view) {
        startActivity(Example1_1Activity.class);
    }

    public void btn_eg2Click(View view) {
        startActivity(NotificationDemoActivity.class);
    }


    public void btn_eg3Click(View view) {
        //打开相机预览
        startActivity(Example4Activity.class);
    }

    public void btn_eg4Click(View view) {
        //调用系统相机
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    public void btn_eg5Click(View view) {
        //高德地图显示，使用activity显示地图
    }

    public void btn_eg5_1Click(View view) {
        //fragment显示高德地图
    }

    public void btn_eg5_2Click(View view) {
    }

    public void btn_eg6Click(View view) {
        startActivity(Example6Activity.class);
    }

    public void btn_eg7click(View view) {
        startActivity(Example7Activity.class);
    }

    public void btn_eg8Click(View view) {
        startActivity(ChoseCityActivity.class);
    }

    public void btn_eg9Click(View view) {
    }

    public void btn_eg10Click(View view) {
        startActivity(InvokeSysGalleryActivity.class);
    }

    public void btn_eg11Click(View view) {
        startActivity(ShuffleImagesActivity.class);
    }

    public void btn_eg12Click(View view) {
        startActivity(UseViewFlipperActivity.class);
    }

    public void btn_eg13Click(View view) {
    }

    public void btn_eg13_2Click(View view) {
    }

    public void btn_eg14_2Click(View view) {
        startActivity(GridLayoutActivity.class);
    }

    public void btn_eg14Click(View view) {
        startActivity(UseGridViewActivity.class);
    }

    public void btn_eg16Click(View view) {
        startActivity(Example16Activity.class);
    }

    public void btn_eg17Click(View view) {
        startActivity(TestDesActivity.class);
    }

    public void btn_eg18Click(View view) {
    }

    public void btn_eg19Click(View view) {
    }

    public void btn_eg20Click(View view) {
        startActivity(ContainerActivity.class);
    }

    public void btn_eg21Click(View view) {
        startActivity(Example21Activity.class);
    }

    public void btn_eg22Click(View view) {
    }

    public void btn_eg23Click(View view) {
    }

    public void open_adb_tcp(View view) {
        activateTcpAdb();
    }

    public void btn_eg25_1Click(View view) {
    }


    public void btn_eg26Click(View view) {
        //回退5分钟。需要root权限或者系统app签名
        long mil = System.currentTimeMillis();
        SystemClock.setCurrentTimeMillis(mil - 5 * 60 * 1000);//设置系统时间
    }


    private void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }


    private void activateTcpAdb() {
        while (!Cmd.runAsRoot("setprop service.adb.tcp.port 5555")) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Cmd.runAsRoot("stop adbd");
        Cmd.runAsRoot("start adbd");
        Log.i(TAG, "adb use tcp/ip mode.");
        Toast.makeText(this, "adb use tcp/ip mode.", Toast.LENGTH_SHORT).show();
    }

    private void subThreadWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.i(TAG, "subThreadWork...");
                Log.i(TAG, "subThreadWork...");

                Looper.loop();

                //will not run to
                Log.i(TAG, "after loop...");
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
        //ToastUtil.toast("已获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        ToastUtil.toast("获取设备读写权限失败");
    }

}
