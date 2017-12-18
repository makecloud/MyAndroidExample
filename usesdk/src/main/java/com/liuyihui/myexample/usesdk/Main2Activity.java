package com.liuyihui.myexample.usesdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.oohlink.player.OOHLink;

public class Main2Activity extends AppCompatActivity {

    private RelativeLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        adLayout = findViewById(R.id.place_sdk_view);
        if (adLayout != null) {
            //启动sdk播放
            OOHLink.instance().startPlay(this, adLayout);
        }
    }
}
