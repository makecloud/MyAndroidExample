package com.liuyihui.networkcontrol.socketTransmit;

import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * socket接收
 */
public class SocketReceiveHandler extends Thread {
    private final String TAG = "SocketReceiveHandler";
    private final String NET_GROUP_ADDRESS = "224.0.0.2";
    private final String DEFAULT_BROADCAST_ADDRESS = "192.168.2.255";
    private final int PORT = 6666;
    private String myIp = "";
    private String broadCastIp;
    private WifiManager.MulticastLock lock;
    byte[] receiveBuff = new byte[1000];
    private DatagramPacket dataPkt = null;
    private DatagramSocket skt = null;
    private Thread receiveThread;

    public SocketReceiveHandler() {
    }


    @Override
    public void run() {
        super.run();
        receiveDatagramPacket();
        Log.e(TAG, "receive thread run to exit");
    }

    public void onStop() {
        interrupt();
        Log.d(TAG, "onStop: manual exit play thread");
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public boolean receiveDatagramPacket() {
        //broadCastIp = IpUtil.getBrocastIp();
        myIp = IpUtil.getMyIp();
        if (TextUtils.isEmpty(myIp)) {
            return false;
        }

        //开启socket接收
        try {

            //
            dataPkt = new DatagramPacket(receiveBuff, receiveBuff.length);

            //组播地址
            //groupAddress = InetAddress.getByName(NET_GROUP_ADDRESS);

            //
            skt = new DatagramSocket(PORT, InetAddress.getByName(myIp));
            //skt.setBroadcast(true);
            //skt.joinGroup(groupAddress);

            while (true) {
                //收包
                Log.d(TAG, "receive Datagram Packet...");
                skt.receive(dataPkt);

                //处理数据包
                byte[] receiveData = dataPkt.getData();

                //send message
                SocketTransmitDemoActivity.mHandler.obtainMessage(1, new String(receiveData))
                                                   .sendToTarget();


            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
            SocketTransmitDemoActivity.mHandler.obtainMessage(1, e.getMessage()).sendToTarget();
        } finally {
            if (skt != null) {
                skt.close();
            }
        }
        return true;
    }

}
