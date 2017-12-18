package com.liuyihui.myexample.usesdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.oohlink.player.MainOOHLinkActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button startPlay1Button;
    private Button startPlay2Button;
    private Button startPlay3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPlay1Button = (Button) findViewById(R.id.start_play1);
        startPlay2Button = (Button) findViewById(R.id.start_play2);
        startPlay3Button = (Button) findViewById(R.id.start_play3);

        startPlay1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlayWay1();
            }
        });
        startPlay2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlayWay2();
            }
        });
        startPlay3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlayWay3();
            }
        });
    }


    /**
     * 启动sdk的activity播放广告
     */
    public void startPlayWay1() {
        Intent intent = new Intent(this, MainOOHLinkActivity.class);
        startActivity(intent);
    }

    /**
     * TODO: 2017/10/26 启动sdk的悬浮窗播放广告
     */
    public void startPlayWay2() {

    }

    /**
     * TODO: 2017/10/26 指定view, 让sdk在指定view播放广告
     */
    public void startPlayWay3() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
