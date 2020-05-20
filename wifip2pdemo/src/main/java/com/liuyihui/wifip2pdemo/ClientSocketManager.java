package com.liuyihui.wifip2pdemo;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 作为客户端socket的逻辑
 */
public class ClientSocketManager {
    private final String TAG = getClass().getSimpleName();
    private final int PORT = 54430;
    private Socket clientSocket;
    private SocketAddress serverSocketAddress;


    private ClientSocketManager() {
    }

    private static class InstanceHolder {
        static ClientSocketManager ClientSocketManager = new ClientSocketManager();
    }

    public static ClientSocketManager getInstance() {
        return InstanceHolder.ClientSocketManager;
    }

    /**
     * 初始化
     *
     * @param serverInetAddress 服务端地址
     */
    public void initClientSocket(final InetAddress serverInetAddress) {
        serverSocketAddress = new InetSocketAddress(serverInetAddress, PORT);
    }

    /**
     * 客户端socket发送对象
     */
    public void handleSend() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (clientSocket == null || clientSocket.isClosed()) {
                        clientSocket = new Socket();
                    }
                    //连接服务器
                    Log.d(TAG, "initClientSocket: 客户端socket尝试连接...");
                    clientSocket.connect(serverSocketAddress, 5000);
                    System.out.println("已连接服务端socket");
                    //发送对象
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    SendInfo sendInfo = new SendInfo();
                    sendInfo.setName("good morning teacher");
                    sendInfo.setValue(1);
                    oos.writeObject(sendInfo);
                    //每次发完关闭socket，每次发创建新socket对象.(自己实践测试后总结的模式)
                    clientSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    //连接server端后。存在server端重新启动侦听情况，此时client端无法感知，但是输出流写入时抛 connection reset异常
                    //抛connection reset异常时，只能给用户手动断开连接，重新连接，再发送？
                }
            }
        }).start();
    }

    public void closeSocket() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
