package com.liuyihui.client.floatplayercontroller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by liuyh on 2017/8/1.
 */

public class FloatService extends Service {

    private WindowManager windowManager;
    private int screenWidth = 480;
    private int screenHeight = 320;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        super.onCreate();

        /** 使用副屏start */
        //查找获取副屏display
        DisplayManager mDisplayManager;//屏幕管理类
        Display[] displays;//屏幕数组
        mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays();
        Display display = displays[displays.length - 1];

        //使用副屏display获取 windowManager
        windowManager = (WindowManager) createDisplayContext(display).getSystemService(WINDOW_SERVICE);
        /** 使用副屏end */


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(screenWidth, screenHeight, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.OPAQUE);

        view = LayoutInflater.from(this).inflate(R.layout.subdisplay_layout, null);

        windowManager.addView(view, params);


    }
    private View view;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        windowManager.removeViewImmediate(view);
        super.onDestroy();
    }
}
