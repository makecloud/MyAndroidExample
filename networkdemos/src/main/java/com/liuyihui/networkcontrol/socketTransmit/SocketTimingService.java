package com.liuyihui.networkcontrol.socketTransmit;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.liuyihui.mylibrary.util.ByteUtil;
import com.liuyihui.mylibrary.util.netinfo.NetWork;
import com.liuyihui.mylibrary.util.netinfo.NetworkUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class SocketTimingService extends Service {
    private final String TAG = "SocketTimingService";
    private final String NET_GROUP_ADDRESS = "224.0.0.2";
    private final String DEFAULT_BROADCAST_ADDRESS = "192.168.2.255";
    private final int PORT = 28780;
    private String myIp = "";
    private String broadCastIp;
    private Handler handler;
    private WifiManager.MulticastLock lock;
    private InetAddress groupAddress;
    byte[] receiveBuff = new byte[8];
    private DatagramPacket dataPkt = null;
    private DatagramSocket skt = null;
    private Thread receiveThread;
    private Thread sendThread;
    private SocketEventListener socketEventListener;


    public SocketTimingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        //        WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        //        lock = manager.createMulticastLock("test wifi");
        startReceiveThread();
    }

    /**
     * @return broadcast ip
     */
    public String getIp() {
        String ip = null;
        String broadCastIp = null;
        //获取本机当前启用网络的ip
        List<NetWork> networks = NetworkUtils.getNetworks();
        for (NetWork netWork : networks) {
            if (netWork.getIsEnable()) {
                ip = netWork.getIpv4();
                broadCastIp = netWork.getBroadCastIp();
                if (!TextUtils.isEmpty(ip))
                    break;
            }
        }

        if (TextUtils.isEmpty(ip)) {
            Log.e(TAG, "receiveDatagramPacket: 未联网");
        }
        if (TextUtils.isEmpty(broadCastIp)) {
            Log.e(TAG, "receiveDatagramPacket: 无法获取广播ip");
        }
        return broadCastIp;
    }

    public void startReceiveThread() {
        if (receiveThread != null && receiveThread.isAlive()) {
            Log.e(TAG, "startReceive: receive thread is alive, skip");
            return;
        }
        receiveThread = new Thread(() -> {
            receiveDatagramPacket();
            Log.e(TAG, "receive thread run to exit");
        });
        receiveThread.start();
    }

    public void startSendThread() {
        if (sendThread != null && sendThread.isAlive()) {
            Log.e(TAG, "startSend: send thread is alive, skip");
            return;
        }
        sendThread = new Thread(() -> {
            try {
                sendDatagramPacket();
                Log.e(TAG, "send thread run to exit");
            } catch (InterruptedException e) {
                Log.e(TAG, "send thread interrupted to exit", e);
            }
        });
        sendThread.start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public boolean receiveDatagramPacket() {
        broadCastIp = getIp();
        if (broadCastIp == null) {
            return false;
        }

        //开启socket接收
        try {

            //
            dataPkt = new DatagramPacket(receiveBuff, receiveBuff.length);

            //组播地址
            //groupAddress = InetAddress.getByName(NET_GROUP_ADDRESS);

            //
            skt = new DatagramSocket(PORT, InetAddress.getByName(broadCastIp));
            skt.setBroadcast(true);
            //skt.joinGroup(groupAddress);

            while (true) {
                //收包
                Log.d(TAG, "receive Datagram Packet...");
                skt.receive(dataPkt);

                //处理数据包
                byte[] receiveData = dataPkt.getData();
                long time = ByteUtil.bytes2Long(receiveData);
                Log.d(TAG, "receiveDatagramPacket: " + time);
                boolean setTimeSuccess = SystemClock.setCurrentTimeMillis(time);
                Log.d(TAG, "receiveDatagramPacket: set time success " + setTimeSuccess);


                //发送供界面显示
                //                RxBus.getInstance()
                //                     .send(new TimeDataEvent(TimeDataEvent.TYPE_RECEIVE,
                //                                             time,
                //                                             setTimeSuccess ? 1 : 0));

                if (socketEventListener != null) {
                    socketEventListener.onTimeData(new TimeDataEvent(TimeDataEvent.TYPE_RECEIVE,
                                                                     time,
                                                                     setTimeSuccess ? 1 : 0));
                }

            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } finally {
            if (skt != null) {
                skt.close();
            }
        }
        return true;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void sendDatagramPacket() throws InterruptedException {
        broadCastIp = getIp();
        if (broadCastIp == null) {
            return;
        }

        try {
            skt = new DatagramSocket(PORT);
            skt.setBroadcast(true);
            while (true) {
                long time = System.currentTimeMillis();
                byte[] bytes = ByteUtil.long2Bytes(time);
                dataPkt = new DatagramPacket(bytes,
                                             bytes.length,
                                             InetAddress.getByName(broadCastIp),
                                             PORT);
                skt.send(dataPkt);
                Log.d(TAG, "sendDatagramPacket: send -> ");
                //                RxBus.getInstance().send(new TimeDataEvent(TimeDataEvent
                //                .TYPE_SEND, time, 0));
                if (socketEventListener != null) {
                    socketEventListener.onTimeData(new TimeDataEvent(TimeDataEvent.TYPE_SEND,
                                                                     time,
                                                                     0));
                }
                //间隔发送
                Thread.sleep(5 * 1000);
            }
        } catch (IOException e) {
            Log.e(TAG, "sendDatagramPacket: send io exception to exit", e);
        }
    }

    public class MyBinder extends Binder {

        public MyBinder() {
        }

        public String getSocketStatus() {
            StringBuilder sb = new StringBuilder();
            if (sendThread != null && sendThread.isAlive()) {
                sb.append("正在发送...");
            }
            if (receiveThread != null && receiveThread.isAlive()) {
                sb.append("正在接收...");
            }
            if (sb.length() < 1) {
                sb.append("停止");
            }
            return sb.toString();

        }

        public void startSend() {
            //需要关闭socket才能关闭阻塞的receive
            if (receiveThread.isAlive()) {
                if (skt != null) {
                    skt.close();
                }
            }
            startSendThread();
        }

        public void startReceive() {
            if (sendThread != null && sendThread.isAlive()) {
                sendThread.interrupt();
                if (skt != null) {
                    skt.close();
                }
            }
            startReceiveThread();
        }

        public void stopSocket() {
            if (sendThread != null && sendThread.isAlive()) {
                sendThread.interrupt();
            }
            if (receiveThread != null && receiveThread.isAlive()) {
                receiveThread.interrupt();
            }
            if (skt != null) {
                skt.close();
            }
        }

        public void setSocketEventListener(SocketEventListener listener) {
            socketEventListener = listener;
        }
    }


}
