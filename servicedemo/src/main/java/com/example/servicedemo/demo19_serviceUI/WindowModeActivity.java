package com.example.servicedemo.demo19_serviceUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.servicedemo.R;


/**
 * 将activity显示为一个窗口。 原理是应用一个窗口性质的主题
 */
public class WindowModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_mode);
    }
}
