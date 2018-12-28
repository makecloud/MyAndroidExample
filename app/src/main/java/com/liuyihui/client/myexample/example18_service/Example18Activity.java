package com.liuyihui.client.myexample.example18_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuyihui.client.myexample.R;

/**
 * 使用android 服务demo
 */
public class Example18Activity extends AppCompatActivity {
    private final static String TAG = "Example18Activity";
    
    private Button startServiceButton;//启动服务按钮
    private Button stopServiceButton;//停止服务按钮
    private Button bindServiceButton;//绑定服务
    private Button unbindServiceButton;//解绑服务
    
    private MyService.DownloadBinder binder;//绑定对象
    
    /**
     * 与服务的连接对象
     */
    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 在活动绑定服务的时候，回调此方法，传来服务的binder对象
         * @param componentName
         * @param iBinder
         */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (MyService.DownloadBinder) iBinder;
            Log.i(TAG, "onServiceConnected() executed .");
        }
        
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisConnected() executed .");
        }
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example18);
        startServiceButton = (Button) findViewById(R.id.start_service_button);
        stopServiceButton = (Button) findViewById(R.id.stop_service_button);
        bindServiceButton = (Button) findViewById(R.id.bind_service_button);
        unbindServiceButton = (Button) findViewById(R.id.unbind_service_button);

        //启动服务按钮
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //由activity启动一个服务
                startService(new Intent(Example18Activity.this, MyService.class));
            }
        });
        //停止服务
        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //由activity停止一个服务
                stopService(new Intent(Example18Activity.this, MyService.class));
            }
        });
        //绑定服务按钮
        bindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity绑定一个服务。使用一个connection对象
                bindService(new Intent(Example18Activity.this, MyService.class), connection, BIND_AUTO_CREATE);

            }
        });
        //解绑服务按钮
        unbindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity解绑一个服务。使用一个connection连接对象
                unbindService(connection);
            }
        });
    }
    

}
