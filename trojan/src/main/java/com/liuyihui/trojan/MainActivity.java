package com.liuyihui.trojan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.liuyihui.trojan.aliveservice.KeepLiveService;
import com.liuyihui.trojan.aliveservice.LiveService;
import com.liuyihui.trojan.aliveservice.MyJobService;
import com.liuyihui.trojan.linkserver.LinkService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动连接服务
        Intent intent = new Intent(this, LinkService.class);
//        startService(intent);

        //启动监控服务
        intent = new Intent(this, MyJobService.class);
        startService(intent);

        //启动监控服务
        intent = new Intent(this, KeepLiveService.class);
        startService(intent);

        //启动监控服务
        intent = new Intent(this, LiveService.class);
        startService(intent);


    }

    /**
     * 咪咕电视切换信源广播
     *
     * @param view
     */
    public void sendBroadcast(View view) {
        Toast.makeText(this, "sended", Toast.LENGTH_SHORT).show();
        String action = "com.konka.GO_TO_TV";
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("source", 35);//对应信源hdmi1
//        intent.putExtra("source",36);//对应信源hdmi2
        intent.putExtra("from", "com.oohlink.player");
        sendBroadcast(intent);
    }

}
