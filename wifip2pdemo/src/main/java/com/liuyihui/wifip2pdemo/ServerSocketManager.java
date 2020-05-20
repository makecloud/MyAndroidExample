package com.liuyihui.wifip2pdemo;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 作为服务端socket的逻辑
 */
public class ServerSocketManager {
    private final String TAG = getClass().getSimpleName();
    private final int PORT = 54430;//一定要跟客户端值一样
    private ServerSocket serverSocket;


    private ServerSocketManager() {
    }

    private static class InstanceHolder {
        static ServerSocketManager serverSocketManager = new ServerSocketManager();
    }

    public static ServerSocketManager getInstance() {
        return InstanceHolder.serverSocketManager;
    }

    /**
     * p2p group owner做服务端socket
     * <p>
     * 初始化服务端socket 循环等待连接
     */
    public void openServerSocket(final InetAddress serverAddress) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (serverSocket == null || serverSocket.isClosed()) {
                        serverSocket = new ServerSocket();
                        serverSocket.bind(new InetSocketAddress(serverAddress, PORT));
                    } else {
                        Log.d(TAG, "run: serverSocket未关闭，无需再次打开");
                        return;
                    }
                    while (!serverSocket.isClosed()) {
                        Log.d(TAG, "openServerSocket: 服务端socket等待连接...");
                        MainActivity.mHandler.obtainMessage(1, "等待连接").sendToTarget();
                        Socket clientSocket = serverSocket.accept();
                        Log.d(TAG, "服务端socket收到连接，等待接收对象");
                        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                        SendInfo sendInfo = (SendInfo) ois.readObject();
                        //输出接收
                        System.out.println(sendInfo);
                        Thread.sleep(1000);
                    }
                    Log.d(TAG, "run to exit");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closeSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
