package com.liuyihui.client.myexample.demo29_sharedpreferrence;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.R;

import util.SharedPreferencesUtils;

public class SpDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_demo);
        SharedPreferencesUtils.getInstance(this).put("aaa", "nihao");
    }

    public void deleteItem(View view) {
        SharedPreferencesUtils.getInstance(this).remove("aaa");
    }
}
