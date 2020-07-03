package com.liuyihui.bar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.oohlink.wifiprobe.everybar.R;

public class BarHideDemoActivity extends AppCompatActivity {
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_hide_demo);
        appBarLayout = findViewById(R.id.appbarlayout);
    }

    private boolean open = true;

    public void fabClick(View view) {
        if (open) {

            appBarLayout.setExpanded(false);
            open = false;
        } else {
            appBarLayout.setExpanded(true);
            open = true;
        }
    }
}
