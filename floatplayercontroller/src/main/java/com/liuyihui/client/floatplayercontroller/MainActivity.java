package com.liuyihui.client.floatplayercontroller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.liuyihui.mylibrary.Cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String BRODCAST_HIDE_PLAYER = "com.oohlink.player.hide";
    private static final String BRODCAST_RESUME_PLAYER = "com.oohlink.player.resume";
    private static final String BRODCAST_EXIT_PLAYER = "com.oohlink.player.exit";

    /** 售货机上商品选择按键 */
    private static final String BRODCAST_SELECT_GOODS = "com.avm.serialport.SELECT_GOODS";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 发送隐藏广播
     *
     * @param v
     */
    public void sendHide(View v) {
        Intent intent = new Intent();
        intent.setAction(BRODCAST_HIDE_PLAYER);
        sendBroadcast(intent);
        Toast.makeText(this, "send Hide", Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送恢复广播
     *
     * @param v
     */
    public void sendResume(View v) {
        Intent intent = new Intent();
        intent.setAction(BRODCAST_RESUME_PLAYER);
        sendBroadcast(intent);
        Toast.makeText(this, "send Resume", Toast.LENGTH_SHORT).show();
    }

    /**
     * 退出播放器
     *
     * @param v
     */
    public void sendStop(View v) {
        Intent intent = new Intent();
        intent.setAction(BRODCAST_EXIT_PLAYER);
        sendBroadcast(intent);
        Toast.makeText(this, "send Exit", Toast.LENGTH_SHORT).show();
    }

    /**
     * 退出播放器
     *
     * @param v
     */
    public void launchPlayer(View v) {

//        ShellUtils.execCommand("am start -n com.cloud.player/.AppStartActivity", true);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName("com.cloud.player", "com.cloud.player.AppStartActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }


    /**
     * 在副屏显示界面
     *
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showOnSecondaryDisplay(View v) {

        //查找获取副屏display
        DisplayManager mDisplayManager;//屏幕管理类
        Display[] displays;//屏幕数组
        mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays();
        Display display = displays[displays.length - 1];

        //用副屏句柄display作为参数，创建一个presentation，并显示
        SubScreenDisplay mPresentation = new SubScreenDisplay(this, display);//displays[1]是副屏
        mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mPresentation.show();
    }

    /**
     * 启动悬浮窗服务
     *
     * @param v
     */
    public void startFloatService(View v) {
        Intent intent = new Intent(this, FloatService.class);
        startService(intent);
    }

    public void removeAllViewInLayout(View v) {

    }

    /**
     * 停止悬浮窗服务
     *
     * @param v
     */
    public void stopFloatService(View v) {
        Intent intent = new Intent(this, FloatService.class);
        stopService(intent);
    }


    /**
     * 截图原理:<br>
     * 利用反射调用android.view.Surface的screenshot方法<br>
     * 此方法在安卓4.2.2下测试成功!!! <br>
     * 需要系统签名,系统sharedUserId<br>
     * 问题:卡顿一下<br>
     * 其他版本反射截图是否可行?,卡顿是这个方法有截图动画导致吗?
     *
     * @param v
     */
    public void takeScreenShot(View v) {

        //截图文件全名
//        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "Screenshot_liuyihui.png";

        String imagePath = "/sdcard/cloud/Screenshot_liuyihui.png";
        //图对象
        Bitmap mScreenBitmap = null;

        //通过显示对象,获取大小
        WindowManager mWindowManager;
        DisplayMetrics mDisplayMetrics;
        Display mDisplay;
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        mDisplayMetrics = new DisplayMetrics();
//        mDisplay.getRealMetrics(mDisplayMetrics);
        mDisplay.getMetrics(mDisplayMetrics);

        //通过显示对象,获取长宽
        float[] dims = {mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels};

        try {
            //貌似隐藏的方法不能被反射调用....
            //反射调用Surface的截图方法
            Class surfaceClass = Class.forName("android.view.Surface");
            Method methodScreenshot = surfaceClass.getMethod("screenshot", int.class, int.class);
            mScreenBitmap = (Bitmap) methodScreenshot.invoke(null, (int) dims[1], (int) dims[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //改为上面反射调用
        //mScreenBitmap = Surface.screenshot((int) dims[0], (int) dims[1]);


        //截图成功与否?
        if (mScreenBitmap == null) {
            Toast.makeText(this, "截图失败", Toast.LENGTH_SHORT).show();
        }

        //截的图压缩写入文件
        try {
            FileOutputStream out = new FileOutputStream(imagePath);
            mScreenBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    /**
     * 发送一个广播<br>
     * 这个广播模拟来乐提供的售货机按钮广播<br>
     * 测试这个广播被接受到了没有
     *
     * @param v
     */
    public void emulatePressGoodsButton(View v) {
        Intent intent = new Intent();
        intent.setAction(BRODCAST_SELECT_GOODS);
        sendBroadcast(intent);
        Toast.makeText(this, "发送广播:" + BRODCAST_SELECT_GOODS, Toast.LENGTH_SHORT).show();
    }

    public void pmInstall(View v) {
        File sdcardDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        for (File f : sdcardDir.listFiles()) {
            if (f.getName().startsWith("player")) {
                String playerFileName = f.getName();
                boolean isRunSuccess;
                isRunSuccess = Cmd.runAsRoot("pm install -r " + playerFileName);
                Toast.makeText(this, String.valueOf(isRunSuccess), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void pmUninstall(View v) {
        File sdcardDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        for (File f : sdcardDir.listFiles()) {
            if (f.getName().startsWith("updater")) {
                String updaterFileName = f.getName();
                boolean isRunSuccess;
                isRunSuccess = Cmd.runAsRoot("pm install -r " + updaterFileName);
                Toast.makeText(this, String.valueOf(isRunSuccess), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 设置定时关机按钮(在威泰克斯主板上测试的项目)
     *
     * @param view
     */
    public void sendTimerPowerOff(View view) {
        //设置定时关机
        Intent intent = new Intent("com.soniq.cybercast.time");
        intent.putExtra("hour", 18);
        intent.putExtra("minute", 8);
        intent.putExtra("mAttribute", 2);
        intent.putExtra("daysOfWeek", 0x7f);
        sendBroadcast(intent);
        Log.i("MainActivity", "设置定时关机");

    }
}
