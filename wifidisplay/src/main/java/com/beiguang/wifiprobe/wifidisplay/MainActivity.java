package com.beiguang.wifiprobe.wifidisplay;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayManager displayManager= (DisplayManager) getSystemService(DISPLAY_SERVICE);
    }
}
