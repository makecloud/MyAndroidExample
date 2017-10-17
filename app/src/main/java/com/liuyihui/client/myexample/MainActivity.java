package com.liuyihui.client.myexample;

import android.app.Activity;
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
import com.liuyihui.client.myexample.example10.InvokeSysGalleryActivity;
import com.liuyihui.client.myexample.example11.ShuffleImagesActivity;
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
import com.liuyihui.client.myexample.example2_pullToRefreshListView_demo.Example2Activity;
import com.liuyihui.client.myexample.example3.Example3Activity;
import com.liuyihui.client.myexample.example5_use_amap.BaseMapFragmentActivity;
import com.liuyihui.client.myexample.example5_use_amap.Example5Activity;
import com.liuyihui.client.myexample.example6_use_popupwindow.Example6Activity;
import com.liuyihui.client.myexample.example8_choose_city_demo.ChoseCityActivity;
import com.liuyihui.client.myexample.example9.ScanResultActivity;

import util.Cmd;

/**
 * 主页、引导页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="myexample.MainActivity";
    
    /** 按钮 */
    private Button startExampleButton1;
    private Button startExampleButton1_1;//标题栏透明渐变列表
    private Button startExampleButton2;
    private Button startExampleButton3;
    private Button startExampleButton4;
    private Button startExampleButton5;
    private Button startExampleButton5_1;
    private Button startExampleButton6;
    private Button startExampleButton7;
    private Button startExampleButton8;//城市选择实例
    private Button startExampleButton9;//启动二维码扫描
    private Button startExampleButton10;//调用系统相册
    private Button startExampleButton11;//调用系统相册
    private Button startExampleButton12;//使用viewflipper实例
    private Button startExampleButton13;//使用viewpager实例
    private Button startExampleButton14;//使用viewpager+fragment实例
    private Button startExampleButton16;//使用recyclerview
    private Button startExampleButton17;//使用des加密，保存文件，在读文件，解密
    private Button startExampleButton18;//使用服务demo
    private Button startExampleButton19;//在服务中显示界面。作为悬浮界面
    private Button startExampleButton20;//启动其他apk的activity
    private Button startExampleButton21;//控制FrameLayout中的控件层级
    private Button startExampleButton22;//唤醒后台。从后台唤醒自己到前台
    private Button startExampleButton23;//CrashHandler 测试
    private Button startExampleButton24;//使用数据库demo
    private Button activateAdbTCPButton;//启动adb tcp 连接服务
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initViewComponent();
    }
    
    
    /**
     * 初始化控件
     */
    public void initViewComponent() {
        startExampleButton1 = (Button) findViewById(R.id.btn_eg1);
        startExampleButton1_1 = (Button) findViewById(R.id.btn_eg1_1);
        startExampleButton2 = (Button) findViewById(R.id.btn_eg2);
        startExampleButton3 = (Button) findViewById(R.id.btn_eg3);
        startExampleButton4 = (Button) findViewById(R.id.btn_eg4);
        startExampleButton5 = (Button) findViewById(R.id.btn_eg5);
        startExampleButton5_1 = (Button) findViewById(R.id.btn_eg5_1);
        startExampleButton6 = (Button) findViewById(R.id.btn_eg6);
        startExampleButton7 = (Button) findViewById(R.id.btn_eg7);
        startExampleButton8 = (Button) findViewById(R.id.btn_eg8);
        startExampleButton9 = (Button) findViewById(R.id.btn_eg9);
        startExampleButton10 = (Button) findViewById(R.id.btn_eg10);
        startExampleButton11 = (Button) findViewById(R.id.btn_eg11);
        startExampleButton12 = (Button) findViewById(R.id.btn_eg12);
        startExampleButton13 = (Button) findViewById(R.id.btn_eg13);
        startExampleButton14 = (Button) findViewById(R.id.btn_eg14);
        startExampleButton16 = (Button) findViewById(R.id.btn_eg16);
        startExampleButton17 = (Button) findViewById(R.id.btn_eg17);
        startExampleButton18 = (Button) findViewById(R.id.btn_eg18);
        startExampleButton19 = (Button) findViewById(R.id.btn_eg19);
        startExampleButton20 = (Button) findViewById(R.id.btn_eg20);
        startExampleButton21 = (Button) findViewById(R.id.btn_eg21);
        startExampleButton22 = (Button) findViewById(R.id.btn_eg22);
        startExampleButton23 = (Button) findViewById(R.id.btn_eg23);
        startExampleButton24 = (Button) findViewById(R.id.btn_eg24);
        activateAdbTCPButton = (Button) findViewById(R.id.btn_eg25);

        //设置事件
        startExampleButton1.setOnClickListener(this);
        startExampleButton1_1.setOnClickListener(this);
        startExampleButton2.setOnClickListener(this);
        startExampleButton3.setOnClickListener(this);
        startExampleButton4.setOnClickListener(this);
        startExampleButton5.setOnClickListener(this);
        startExampleButton5_1.setOnClickListener(this);
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
    }
    
    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent i;
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
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
                break;
            case R.id.btn_eg5:
                i = new Intent(this, Example5Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg5_1:
                i = new Intent(this, BaseMapFragmentActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg6:
                i = new Intent(this, Example6Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg7:
                String param = "any";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("KTMJ://" + param)));
                break;
            case R.id.btn_eg8:
                i = new Intent(this, ChoseCityActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg9:
                i = new Intent(this, ScanResultActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg10:
                i = new Intent(this, InvokeSysGalleryActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg11:
                i = new Intent(this, ShuffleImagesActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg12:
                i = new Intent(this, UseViewFlipperActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg13:
                i = new Intent(this, UseViewPagerActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg14:
                i = new Intent(this, ViewPagerFragmentActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg16:
                i = new Intent(this, Example16Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg17:
                i = new Intent(this, TestDesActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg18:
                i = new Intent(this, Example18Activity.class);
                Bundle bundle = new Bundle();
                startActivity(i);
                break;
            case R.id.btn_eg19:
                i = new Intent(this, example19Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg20:
                i = new Intent(this, ContainerActivity.class);
                startActivity(i);
                break;
            case R.id.btn_eg21:
                i = new Intent(this, Example21Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg22:
                i = new Intent(this, Example22Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg23:
                i = new Intent(this, Example23Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg24:
                i = new Intent(this, Example24Activity.class);
                startActivity(i);
                break;
            case R.id.btn_eg25:
                activateTcpAdb();
            default:
                break;
        }
    }

    private void activateTcpAdb(){
        while(!Cmd.runAsRoot("setprop service.adb.tcp.port 5555")){
            try {
                Thread.sleep(10 * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Cmd.runAsRoot("stop adbd");
        Cmd.runAsRoot("start adbd");
        Log.i(TAG, "adb use tcp/ip mode.");
        Toast.makeText(this, "adb use tcp/ip mode.", Toast.LENGTH_SHORT).show();


    }
}
