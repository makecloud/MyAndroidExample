package com.liuyihui.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.oohlink.wifiprobe.everybar.R;

/**
 * 控制 安卓activity的各种bar 实例
 * <p>
 * 作为一个导航页面, 启动其他案例页面
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //更改actionBar的属性demo
    public void startBarDemo1(View view) {
        startActivity(new Intent(this, ActionBarBaseDemoActivity.class));
    }

    //选项菜单示例
    public void startOptionMenuDemo1(View view) {
        startActivity(new Intent(this, OptionMenuDemoActivity.class));
    }

    //移动的操作栏示例1
    public void startMovingToolbarDemo(View view) {
        startActivity(new Intent(this, MovingToolBarActivity.class));
    }

    //移动的操作栏示例2
    public void startMovingToolbar2Demo(View view) {
        startActivity(new Intent(this, MovingToolBarActivity2.class));
    }

    //视差效果操作栏示例
    public void startParallaxImageShow(View view) {
        startActivity(new Intent(this, ParallaxStyleBarActivity.class));
    }

    //视差效果操作栏示例
    public void startBaraHideDemo(View view) {
        startActivity(new Intent(this, BarHideDemoActivity.class));
    }
}
