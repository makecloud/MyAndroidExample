package com.liuyihui.networkcontrol.socketTransmit;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.liuyihui.mylibrary.util.ByteUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * socket发送
 */
public class SocketSendHandler extends Thread  {
    private final String TAG = "SocketTimingService";
    private final String NET_GROUP_ADDRESS = "224.0.0.2";
    private final String DEFAULT_BROADCAST_ADDRESS = "192.168.2.255";
    private final int PORT = 28780;
    private String myIp = "";
    private String broadCastIp;
    private Handler handler;
    private WifiManager.MulticastLock lock;
    byte[] receiveBuff = new byte[8];
    private DatagramPacket dataPkt = null;
    private DatagramSocket skt = null;
    private Thread receiveThread;

    public SocketSendHandler() {
    }

    @Override
    public void run() {
        super.run();
        try {
            long flag = 0L;
            while (true) {
                //            Long nowMills = 1586852630000l;
                Long nowMills = System.currentTimeMillis();

                if ((nowMills - flag) < 5) {
                    System.out.println("捕获到5秒的非毫秒精确倍秒,<5ms,忽略");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                if (nowMills % 5000 < 5) {
                    System.out.println("捕获5秒的非毫秒精确倍秒 " + nowMills);
                    flag = nowMills;

                    //发广播
                    sendDatagramPacket();

                    // TODO: 2020-05-27
                    SocketTransmitDemoActivity.mHandler.obtainMessage(2, "").sendToTarget();
                }

                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "run exit by interrupted", e);
        }
        Log.e(TAG, "receive thread run to exit");
    }

    public void onStop() {
        interrupt();
        Log.d(TAG, "onStop: manual exit play thread");
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void sendDatagramPacket() throws InterruptedException {
        //broadCastIp = IpUtil.getBrocastIp();
        myIp = IpUtil.getMyIp();
        if (TextUtils.isEmpty(myIp)) {
            return;
        }

        try {
            if (skt == null) {
                skt = new DatagramSocket(PORT);
                //skt.setBroadcast(true);
            }
            long time = System.currentTimeMillis();
            byte[] bytes = ByteUtil.long2Bytes(time);
            dataPkt = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("192.168.1.133"), PORT);
            skt.send(dataPkt);
            Log.d(TAG, "sendDatagramPacket: send -> ");
        } catch (IOException e) {
            Log.e(TAG, "sendDatagramPacket: send failed", e);
        }
    }

}
