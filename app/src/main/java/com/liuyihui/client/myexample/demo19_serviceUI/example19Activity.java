package com.liuyihui.client.myexample.demo19_serviceUI;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

import java.util.List;

/**
 * 启动serviceUI服务，这个服务显示一个悬浮窗
 */
public class example19Activity extends AppCompatActivity {

    Button startServiceButton;
    Button bindServiceButton;
    Button hideViewButton;
    Button resumeViewButton;
    Button unbindServiceButton;
    Button stopServiceButton;
    Button testButton;

    ServiceUI.MyBinder binder;
    ServiceConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example19);
        startServiceButton = (Button) findViewById(R.id.start_service);
        stopServiceButton = (Button) findViewById(R.id.stop_service);
        bindServiceButton = (Button) findViewById(R.id.bind_service);
        unbindServiceButton = (Button) findViewById(R.id.unbind_service);
        hideViewButton = (Button) findViewById(R.id.hide_view);
        resumeViewButton = (Button) findViewById(R.id.resume_view);
        testButton = (Button) findViewById(R.id.test_button);


        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(example19Activity.this, "startService", Toast.LENGTH_SHORT).show();
                startService(new Intent(example19Activity.this, ServiceUI.class));
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(example19Activity.this, ServiceUI.class));

            }
        });
        bindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder == null) {
                    Intent intent = new Intent(example19Activity.this, ServiceUI.class);
                    connection = new MyServiceConnect();
                    bindService(intent, connection, BIND_AUTO_CREATE);
                } else {
                    Toast.makeText(example19Activity.this, "已绑定", Toast.LENGTH_SHORT).show();
                }
            }
        });
        unbindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connection != null) {
                    unbindService(connection);
                } else {
                    Toast.makeText(example19Activity.this, "未绑定服务", Toast.LENGTH_SHORT).show();
                }

            }
        });
        hideViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder != null) {
                    binder.hideView();
                } else {
                    Toast.makeText(example19Activity.this, "未绑定服务", Toast.LENGTH_SHORT).show();
                }

            }
        });
        resumeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder != null) {
                    binder.resumeView();
                } else {
                    Toast.makeText(example19Activity.this, "未绑定服务", Toast.LENGTH_SHORT).show();
                }

            }
        });
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(example19Activity.this, "can click ", Toast.LENGTH_SHORT).show();

            }
        });


        //查看已经运行的服务
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);

        Log.i("liuyihui", "applicationId:" + this.getPackageName());
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            Log.i("liuyihui", " ");
            Log.i("liuyihui", "getClassName:" + aInfo.service.getClassName());
            Log.i("liuyihui", "getPackageName:" + aInfo.service.getPackageName());
            Log.i("liuyihui", "getShortClassName:" + aInfo.service.getShortClassName());
        }
    }

    public class MyServiceConnect implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (ServiceUI.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    }
}
