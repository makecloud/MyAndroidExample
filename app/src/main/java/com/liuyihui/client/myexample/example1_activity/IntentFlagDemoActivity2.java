package com.liuyihui.client.myexample.example1_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.liuyihui.client.myexample.R;

public class IntentFlagDemoActivity2 extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_flag_demo1);
        getSupportActionBar().setTitle(getClass().getSimpleName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    public void startActivityWithFlag(View view) {
        Intent intent = new Intent(this, IntentFlagDemoActivity3.class);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void close(View view) {
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
