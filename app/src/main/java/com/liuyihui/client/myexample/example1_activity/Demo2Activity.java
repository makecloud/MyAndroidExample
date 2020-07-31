package com.liuyihui.client.myexample.example1_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.liuyihui.client.myexample.R;

public class Demo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        getSupportActionBar().setTitle(getClass().getSimpleName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void startNextActivity(View view) {
        Intent intent = new Intent(this, Demo3Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void close(View view) {
        finish();
    }
}
