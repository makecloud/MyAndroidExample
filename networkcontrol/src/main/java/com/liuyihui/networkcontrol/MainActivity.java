package com.liuyihui.networkcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * wifi、热点 控制
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    Button openHotspotButton;
    Button connectWifiButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHotspotButton = findViewById(R.id.open_hotspot);
        connectWifiButton = findViewById(R.id.connect_wifi);

        //通过按钮事件设置热点
        openHotspotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试有效.
                ApManager.openAp(MainActivity.this, "Test_open_hotspot", "12345678");
            }
        });

        //连接指定wifi
        connectWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                WifiControlUtil.connectToSpecifiedWifi(MainActivity.this, "oohlink_office", "oohlink.1306", WifiControlUtil.EWifiCipherType.WPA);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
