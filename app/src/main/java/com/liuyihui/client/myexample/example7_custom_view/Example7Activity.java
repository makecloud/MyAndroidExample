package com.liuyihui.client.myexample.example7_custom_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.liuyihui.client.myexample.R;

/**
 * 自定义view
 */
public class Example7Activity extends AppCompatActivity {
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example7);
        rootLayout = findViewById(R.id.activity_example7);

        CustomView customView = new CustomView(this);
        rootLayout.addView(customView);



        CustomLayout customLayout = findViewById(R.id.custom_layout);
    }


}
