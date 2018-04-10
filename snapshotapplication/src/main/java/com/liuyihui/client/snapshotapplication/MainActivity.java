package com.liuyihui.client.snapshotapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import java.io.DataInput;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import util.Cmd;
import util.ShellUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "snapshotapplication";
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //安装raw
        String filesPath = getApplicationContext().getFilesDir().getPath();
        File screencaptureFile = new File(filesPath + "/" + "screencapu");
        if (!screencaptureFile.exists()) {
            try {
                screencaptureFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(screencaptureFile);//输出到新文件
            InputStream is = getResources().openRawResource(R.raw.screencapture);//从老文件得到输入流
            byte[] buff = new byte[1024];
            int tmp;
            while ((tmp = is.read(buff)) != -1) {
                fos.write(buff, 0, tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //授权
        if (screencaptureFile.length() > 0) {
            String cmd = "cd " + filesPath + "; chmod 777 screencapu";
            ShellUtils.execCommand(cmd, false);
        }

    }

    /**
     * 执行命令截图
     *
     * @param v
     */
    public void exeCmdToSnapshot(View v) {
        //需要安装一下screencap2
//        String cmd = "/data/data/com.cloud.player/files/screencapture 2 /sdcard/PlanId.291-scrId.299-matId.95-2017-07-31_22-22-22.png";
        String dataPath = Environment.getExternalStorageDirectory().getPath();
        String cmd = "screencap  " + dataPath + "/a.png";

        try {
            ShellUtils.execCommand(cmd, false);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
        }

        //
        reflScreeshot();

        //
        takeScreenshotUseSystemAPI();

    }

    /**
     * 据说4.3系统支持的通过反射调用截图
     */
    public void reflScreeshot() {
        Class sc = null;
        try {
            sc = Class.forName("android.view.Surface");
            for (Method m : sc.getMethods()) {
                Log.i(TAG, m.getName());
            }
            Method method = sc.getMethod("screenshot", new Class[]{int.class, int.class});
            int[] dims = new int[2];
            Object o = method.invoke(sc, new Object[]{(int) dims[0], (int) dims[1]});
            Bitmap mScreenBitmap = (Bitmap) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 调用系统takescreenshot服务
     */
    private void takeScreenshotUseSystemAPI() {
        final Object mScreenshotLock = new Object();
        final ServiceConnection[] mScreenshotConnection = {null};

        synchronized (mScreenshotLock) {
            if (mScreenshotConnection[0] != null) {
                return;
            }
            ComponentName cn = new ComponentName("com.android.systemui", "com.android.systemui.screenshot.TakeScreenshotService");
            Intent intent = new Intent();
            intent.setComponent(cn);

            ServiceConnection conn = new ServiceConnection() {

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    synchronized (mScreenshotLock) {
                        if (mScreenshotConnection[0] != this) {
                            return;
                        }
                        Messenger messenger = new Messenger(service);
                        Message msg = Message.obtain(null, 1);
                        final ServiceConnection myConn = this;

                        Handler h = new Handler(mHandler.getLooper()) {

                            @Override
                            public void handleMessage(Message msg) {
                                synchronized (mScreenshotLock) {
                                    if (mScreenshotConnection[0] == myConn) {
                                        MainActivity.this.unbindService(mScreenshotConnection[0]);
                                        mScreenshotConnection[0] = null;
                                        mHandler.removeCallbacks(mScreenshotTimeout);
                                    }
                                }
                            }
                        };
                        msg.replyTo = new Messenger(h);
                        msg.arg1 = msg.arg2 = 0;
                        /*if (mStatusBar != null && mStatusBar.isVisibleLw()) {
                        }*/
                        msg.arg1 = 1;
                        /*if (mNavigationBar != null && mNavigationBar.isVisibleLw()) {
                        }*/
                        msg.arg2 = 1;
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                }
            };

            if (MainActivity.this.bindService(intent, conn, Context.BIND_AUTO_CREATE)) {
                mScreenshotConnection[0] = conn;
                mHandler.postDelayed(mScreenshotTimeout, 10000);
            }
        }
    }


    /**
     * 系统截图服务超时回调类
     */
    private Runnable mScreenshotTimeout = new Runnable() {
        @Override
        public void run() {
            Log.w(TAG, "snapshot timeout ");
        }
    };

    /**
     * 这种方法状态栏是空白，显示不了状态栏的信息
     */
    public void saveCurrentImage(View v) {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();

        //输出到sd卡
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();

        File imagFile = new File(sdcardPath + "/" + "aaaaa.png");
        try {
            if (!imagFile.exists()) {
                imagFile.createNewFile();
            }
            FileOutputStream foStream = new FileOutputStream(imagFile);
            temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.flush();
            foStream.close();
        } catch (Exception e) {
            Log.i("Show", e.toString());
        }
    }
}
