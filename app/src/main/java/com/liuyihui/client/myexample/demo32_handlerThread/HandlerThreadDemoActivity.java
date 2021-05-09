package com.liuyihui.client.myexample.demo32_handlerThread;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.liuyihui.client.myexample.databinding.ActivityHandlerThreadDemoBinding;

/**
 * Handler Thread 用例
 */
public class HandlerThreadDemoActivity extends AppCompatActivity {
    private final String TAG = "HandlerThreadDemo";
    private ActivityHandlerThreadDemoBinding binding;
    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: " + Thread.currentThread());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHandlerThreadDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //HandlerThread 是包含了 消息循环 looper 的一个thread扩展类.
        //创建HandlerThread,然后start启动进入消息循环.
        //之后将任意handler挂到该线程的消息循环上,就能用该线程的消息循环执行逻辑了,从而实现了用子线程执行任务.
        HandlerThread handlerThread = new HandlerThread("");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "handleMessage: " + Thread.currentThread());
            }
        };

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发一条消息
                Message.obtain(handler).sendToTarget();
                Message.obtain(mainHandler).sendToTarget();

            }
        });
    }
}
