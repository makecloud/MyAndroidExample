package com.liuyihui.client.myexample.demo7_custom_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.liuyihui.client.myexample.R;

/**
 * 显示自定义view
 */
public class Example7Activity extends AppCompatActivity {
    private RelativeLayout rootLayout;
    private CustomViewGroup customViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example7);

        rootLayout = findViewById(R.id.rootLayout);
        customViewGroup = findViewById(R.id.custom_viewGroup);

    }


}
