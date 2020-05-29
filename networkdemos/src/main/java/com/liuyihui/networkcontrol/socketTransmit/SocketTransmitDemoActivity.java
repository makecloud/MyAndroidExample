package com.liuyihui.networkcontrol.socketTransmit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyihui.mylibrary.MyThreadHandler;
import com.liuyihui.networkcontrol.R;


/**
 * socket监听、发送demo（udp协议下的报文传送）
 */
public class SocketTransmitDemoActivity extends AppCompatActivity {
    private SocketReceiveHandler socketReceiveHandler;
    private TextView ipText;

    public static MyThreadHandler<SocketTransmitDemoActivity> mHandler = new MyThreadHandler<>((m, msg) -> {
        Toast.makeText(m, msg + "", Toast.LENGTH_SHORT).show();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_transmit_demo);
        ipText = findViewById(R.id.ipStr);

        mHandler.setTargetInstance(this);
        socketReceiveHandler = new SocketReceiveHandler();
        ipText.setText("我的ip：" + IpUtil.getMyIp());

    }

    //socket发送报文
    public void socketSend(View view) {

    }

    //socket监听接收
    public void socketReceive(View view) {
        if (socketReceiveHandler == null) {
            Toast.makeText(this, "空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (socketReceiveHandler.isAlive()) {
            Toast.makeText(this, "isAlive！", Toast.LENGTH_SHORT).show();
            return;
        }
        socketReceiveHandler.start();
    }

    //关闭socket
    public void closeAllSocket(View view) {

    }
}
