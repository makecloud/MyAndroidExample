package com.liuyihui.client.myexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuyihui.client.myexample.example1.Example1Activity;
import com.liuyihui.client.myexample.example1.Example1_1Activity;
import com.liuyihui.client.myexample.example10_photo_album.InvokeSysGalleryActivity;
import com.liuyihui.client.myexample.example11_slideshow_banner.ShuffleImagesActivity;
import com.liuyihui.client.myexample.example13_viewflipper.UseViewFlipperActivity;
import com.liuyihui.client.myexample.example15_viewpager.UseViewPagerActivity;
import com.liuyihui.client.myexample.example15_viewpager.ViewPagerFragmentActivity;
import com.liuyihui.client.myexample.example16_recyclerview.Example16Activity;
import com.liuyihui.client.myexample.example17_DESencrypt.TestDesActivity;
import com.liuyihui.client.myexample.example18_service.Example18Activity;
import com.liuyihui.client.myexample.example19_serviceUI.example19Activity;
import com.liuyihui.client.myexample.example20_activityGroup.ContainerActivity;
import com.liuyihui.client.myexample.example21_framelayout_hierarchy.Example21Activity;
import com.liuyihui.client.myexample.example22_awakenSelf.Example22Activity;
import com.liuyihui.client.myexample.example23_CrashHandler.Example23Activity;
import com.liuyihui.client.myexample.example24_use_database.Example24Activity;
import com.liuyihui.client.myexample.example25_bitmap.BitmapOperationActivity;
import com.liuyihui.client.myexample.example2_system_action.Example2Activity;
import com.liuyihui.client.myexample.example3.Example3Activity;
import com.liuyihui.client.myexample.example5_1_use_amaplocate.GetLocationActivity;
import com.liuyihui.client.myexample.example5_use_amap.BaseMapFragmentActivity;
import com.liuyihui.client.myexample.example5_use_amap.Example5Activity;
import com.liuyihui.client.myexample.example6_use_popupwindow.Example6Activity;
import com.liuyihui.client.myexample.example8_choose_city_demo.ChoseCityActivity;
import com.liuyihui.client.myexample.example9_qrcode_scan.ScanResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import util.Cmd;

/**
 * 主页、引导页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "myexample.MainActivity";
    @BindView(R.id.btn_eg1)
    Button startExampleButton1;
    @BindView(R.id.btn_eg1_1)
    Button startExampleButton1_1;//标题栏透明渐变列表
    @BindView(R.id.btn_eg2)
    Button startExampleButton2;
    @BindView(R.id.btn_eg3)
    Button startExampleButton3;
    @BindView(R.id.btn_eg4)
    Button startExampleButton4;
    @BindView(R.id.btn_eg5)
    Button startExampleButton5;//使用activity显示地图demo
    @BindView(R.id.btn_eg5_1)
    Button startExampleButton5_1;//使用fragment显示地图demo
    @BindView(R.id.btn_eg5_2)
    Button startExampleButton5_2;//使用amap定位demo
    @BindView(R.id.btn_eg6)
    Button startExampleButton6;
    @BindView(R.id.btn_eg7)
    Button startExampleButton7;//自定义view 实例
    @BindView(R.id.btn_eg8)
    Button startExampleButton8;//城市选择实例
    @BindView(R.id.btn_eg9)
    Button startExampleButton9;//启动二维码扫描
    @BindView(R.id.btn_eg10)
    Button startExampleButton10;//调用系统相册
    @BindView(R.id.btn_eg11)
    Button startExampleButton11;//调用系统相册
    @BindView(R.id.btn_eg12)
    Button startExampleButton12;//使用viewflipper实例
    @BindView(R.id.btn_eg13)
    Button startExampleButton13;//使用viewpager实例
    @BindView(R.id.btn_eg14)
    Button startExampleButton14;//使用viewpager+fragment实例
    @BindView(R.id.btn_eg16)
    Button startExampleButton16;//使用recyclerview
    @BindView(R.id.btn_eg17)
    Button startExampleButton17;//使用des加密，保存文件，在读文件，解密
    @BindView(R.id.btn_eg18)
    Button startExampleButton18;//使用服务demo
    @BindView(R.id.btn_eg19)
    Button startExampleButton19;//在服务中显示界面。作为悬浮界面
    @BindView(R.id.btn_eg20)
    Button startExampleButton20;//启动其他apk的activity
    @BindView(R.id.btn_eg21)
    Button startExampleButton21;//控制FrameLayout中的控件层级
    @BindView(R.id.btn_eg22)
    Button startExampleButton22;//唤醒后台。从后台唤醒自己到前台
    @BindView(R.id.btn_eg23)
    Button startExampleButton23;//CrashHandler 测试
    @BindView(R.id.btn_eg24)
    Button startExampleButton24;//使用数据库demo
    @BindView(R.id.btn_eg25)
    Button activateAdbTCPButton;//启动adb tcp 连接服务
    @BindView(R.id.btn_eg25_1)
    Button bitmapOperationButton;//启动 bitmap操作案例

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化控件
        initViewComponent();
    }


    /**
     * 初始化控件
     */
    public void initViewComponent() {
        startExampleButton1.setOnClickListener(this);
        startExampleButton1_1.setOnClickListener(this);
        startExampleButton2.setOnClickListener(this);
        startExampleButton3.setOnClickListener(this);
        startExampleButton4.setOnClickListener(this);
        startExampleButton5.setOnClickListener(this);
        startExampleButton5_1.setOnClickListener(this);
        startExampleButton5_2.setOnClickListener(this);
        startExampleButton6.setOnClickListener(this);
        startExampleButton7.setOnClickListener(this);
        startExampleButton8.setOnClickListener(this);
        startExampleButton9.setOnClickListener(this);
        startExampleButton10.setOnClickListener(this);
        startExampleButton11.setOnClickListener(this);
        startExampleButton12.setOnClickListener(this);
        startExampleButton13.setOnClickListener(this);
        startExampleButton14.setOnClickListener(this);
        startExampleButton16.setOnClickListener(this);
        startExampleButton17.setOnClickListener(this);
        startExampleButton18.setOnClickListener(this);
        startExampleButton19.setOnClickListener(this);
        startExampleButton20.setOnClickListener(this);
        startExampleButton21.setOnClickListener(this);
        startExampleButton22.setOnClickListener(this);
        startExampleButton23.setOnClickListener(this);
        startExampleButton24.setOnClickListener(this);
        activateAdbTCPButton.setOnClickListener(this);
        bitmapOperationButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_eg1:
                startActivity(new Intent(this, Example1Activity.class));
                break;
            case R.id.btn_eg1_1:
                startActivity(new Intent(this, Example1_1Activity.class));
                break;
            case R.id.btn_eg2:
                startActivity(new Intent(this, Example2Activity.class));
                break;
            case R.id.btn_eg3:
                startActivity(new Intent(this, Example3Activity.class));
                break;
            case R.id.btn_eg4:

                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                break;
            case R.id.btn_eg5:

                startActivity(new Intent(this, Example5Activity.class));
                break;
            case R.id.btn_eg5_1:

                startActivity(new Intent(this, BaseMapFragmentActivity.class));
                break;
            case R.id.btn_eg5_2:

                startActivity(new Intent(this, GetLocationActivity.class));
                break;
            case R.id.btn_eg6:

                startActivity(new Intent(this, Example6Activity.class));
                break;
            case R.id.btn_eg7:
                String param = "any";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("KTMJ://" + param)));
                break;
            case R.id.btn_eg8:

                startActivity(new Intent(this, ChoseCityActivity.class));
                break;
            case R.id.btn_eg9:

                startActivity(new Intent(this, ScanResultActivity.class));
                break;
            case R.id.btn_eg10:

                startActivity(new Intent(this, InvokeSysGalleryActivity.class));
                break;
            case R.id.btn_eg11:

                startActivity(new Intent(this, ShuffleImagesActivity.class));
                break;
            case R.id.btn_eg12:

                startActivity(new Intent(this, UseViewFlipperActivity.class));
                break;
            case R.id.btn_eg13:

                startActivity(new Intent(this, UseViewPagerActivity.class));
                break;
            case R.id.btn_eg14:

                startActivity(new Intent(this, ViewPagerFragmentActivity.class));
                break;
            case R.id.btn_eg16:

                startActivity(new Intent(this, Example16Activity.class));
                break;
            case R.id.btn_eg17:

                startActivity(new Intent(this, TestDesActivity.class));
                break;
            case R.id.btn_eg18:
                startActivity(new Intent(this, Example18Activity.class));
                break;
            case R.id.btn_eg19:

                startActivity(new Intent(this, example19Activity.class));
                break;
            case R.id.btn_eg20:

                startActivity(new Intent(this, ContainerActivity.class));
                break;
            case R.id.btn_eg21:

                startActivity(new Intent(this, Example21Activity.class));
                break;
            case R.id.btn_eg22:

                startActivity(new Intent(this, Example22Activity.class));
                break;
            case R.id.btn_eg23:

                startActivity(new Intent(this, Example23Activity.class));
                break;
            case R.id.btn_eg24:

                startActivity(new Intent(this, Example24Activity.class));
                break;
            case R.id.btn_eg25:
                activateTcpAdb();
                break;
            case R.id.btn_eg25_1:
                startActivity(new Intent(this, BitmapOperationActivity.class));
                break;
            default:
                break;
        }
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
}
