package com.liuyihui.client.snapshotapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import util.Cmd;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "snapshotapplication";
    private Handler mHandler=new Handler();

    public static void convertToRgba32(DataInput frameBuffer, int[] into) {
        try {
            for (int x = 0; x < into.length; x++) {
                try {
                    int rgb = frameBuffer.readShort() & 0xffff;
                    int red = rgb >> 11;
                    red = (red << 3) | (red >> 2);
                    int green = (rgb >> 5) & 63;
                    green = (green << 2) | (green >> 4);
                    int blue = rgb & 31;
                    blue = (blue << 3) | (blue >> 2);
                    into[x] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } catch (EOFException e) {
                    System.out.println("EOFException=" + e);
                }
            }
        } catch (IOException exception) {
            System.out.println("convertToRgba32Exception=" + exception);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button scrButton=findViewById(R.id.)
        //activity启动时候截图测试
    }

    /**
     * 截图
     *
     * @return
     */
    public boolean getSnapshot(View v) {

        Process sh = null;
        OutputStream os = null;
        boolean ret = true;


        try {
            sh = Runtime.getRuntime().exec("su", null, null);
            os = sh.getOutputStream();
            /**
             * <p>这里可以保存到ramdisk里，但是有些机器没有/dev/shm这个目录</p>
             */
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(this, "invoke system screencap", Toast.LENGTH_SHORT).show();
                String cmd = "/system/bin/screencap " + "/sdcard/cloud/snap/PlanId.291-scrId.299-matId.95-2017-07-31_11-11-11.png";
                os.write(cmd.getBytes("ASCII"));
            } else {
                Toast.makeText(this, "invoke my screencap", Toast.LENGTH_SHORT).show();
                String cmd = "/data/data/com.cloud.player/files/screencapture 2 /sdcard/cloud/snap/PlanId.291-scrId.299-matId.95-2017-07-31_22-22-22.png";
                os.write(cmd.getBytes("ASCII"));
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public void exeCmd(View v) {

        String cmd = "/data/data/com.cloud.player/files/screencapture 2 /sdcard/PlanId.291-scrId.299-matId.95-2017-07-31_22-22-22.png";

        try {
            Cmd.runAsRoot(cmd);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        takeScreenshotUseSystemAPI();

    }

    public void takeScreeshot() {


    }

    /*public void drawPicture() {
        try {
            //分辨率大小，后续可以通过代码来获取到当前的分辨率
            int xResolution = 320;
            int yResolution = 480;
            //执行adb命令，把framebuffer中内容保存到fb1文件中
            Runtime.getRuntime().exec("adb pull /dev/graphics/fb0 C:/fb1");
            //等待几秒保证framebuffer中的数据都被保存下来，如果没有保存完成进行读取操作会有IO异常
            Thread.sleep(15000);
            //读取文件中的数据
            InputStream in = (InputStream) new FileInputStream("C:/fb1");
            DataInput frameBuffer = new LittleEndianDataInputStream(in);


            BufferedImage a;
            BufferedImage screenImage = new BufferedImage(xResolution, yResolution, BufferedImage.TYPE_INT_ARGB);
            int[] oneLine = new int[xResolution];
            for (int y = 0; y < yResolution; y++) {
                //从frameBuffer中计算出rgb值
                convertToRgba32(frameBuffer, oneLine);
                //把rgb值设置到image对象中
                screenImage.setRGB(0, y, xResolution, 1, oneLine, 0, xResolution);
            }
            Closeables.closeQuietly(in);

            ByteArrayOutputStream rawPngStream = new ByteArrayOutputStream();
            try {
                if (!ImageIO.write(screenImage, "png", rawPngStream)) {
                    throw new RuntimeException("This Java environment does not support converting to PNG.");
                }
            } catch (IOException exception) {
                // This should never happen because rawPngStream is an in-memory stream.
                System.out.println("IOException=" + exception);
            }
            byte[] rawPngBytes = rawPngStream.toByteArray();
            String base64Png = new Base64Encoder().encode(rawPngBytes);

            File screenshot = OutputType.FILE.convertFromBase64Png(base64Png);
            System.out.println("screenshot==" + screenshot.toString());
            screenshot.renameTo(new File("C:\\screenshottemp.png"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 系统截图超时回调类
     */
    private Runnable mScreenshotTimeout=new Runnable() {
        @Override
        public void run() {
            Log.w(TAG,"snapshot timeout ");
        }
    };
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
}
