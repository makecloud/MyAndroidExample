package com.liuyihui.client.myexample.example1_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.R;

public class Demo3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        getSupportActionBar().setTitle(getClass().getSimpleName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void close(View view) {
        startActivity(getSupportParentActivityIntent());
        finish();
    }
}
