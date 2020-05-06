package com.liuyihui.wifip2pdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketTrasmitManager {
    private final String TAG = getClass().getSimpleName();
    private final int PORT = 54430;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private SocketTrasmitManager() {

    }

    private static class InstanceHolder {
        static SocketTrasmitManager socketTrasmitManager = new SocketTrasmitManager();
    }

    public static SocketTrasmitManager getInstance() {
        return InstanceHolder.socketTrasmitManager;
    }

    /**
     * p2p group owner做服务端socket
     * <p>
     * 初始化服务端socket等待连接
     */
    public void initServerSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    serverSocket = new ServerSocket(PORT);
                    clientSocket = serverSocket.accept();
                    InputStream inputStream = clientSocket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    //输出接收
                    int c;
                    while ((c = inputStreamReader.read()) != -1) {
                        System.out.println((char) c);
                    }

                    //回传
                    OutputStreamWriter oswriter =
                            new OutputStreamWriter(clientSocket.getOutputStream());
                    oswriter.write("good morning class");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 客户端socket
     *
     * @param serverHost 服务端ip
     */
    public void initClientSocket(final String serverHost) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clientSocket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(serverHost, PORT);
                try {
                    //连接服务器
                    clientSocket.connect(socketAddress, 5000);
                    System.out.println("已连接 server端");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 客户端发送一段文字
     */
    public void handleSend() {
        if (clientSocket == null) {
            throw new IllegalStateException("请先初始化client socket");
        }
        String content = "good morning teacher";
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(clientSocket.getOutputStream());
            outputStreamWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleReceive() {

    }

    public void closeSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
