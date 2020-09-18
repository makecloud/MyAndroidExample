package com.liuyihui.client.myexample.demo22_awakenSelf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.MainActivity;
import com.liuyihui.client.myexample.R;

public class Example22Activity extends AppCompatActivity {
    
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example22);
    }
    
    public void postAwake(View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Example22Activity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
