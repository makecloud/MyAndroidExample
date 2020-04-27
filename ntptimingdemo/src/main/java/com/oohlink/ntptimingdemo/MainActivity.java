package com.oohlink.ntptimingdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    private final static int TASK_CYCLE_TIME = 30 * 1000;
    private static Timer mCycleTimer;
    private static TimerTask mCycleTask;
    private static SntpClient mNtpClient;
    private static final int TIME_OUT_MILLION = 5 * 1000;
    //    private final static String DATETIME_SNTP_SERVER = "s1a.time.edu.cn";//北京邮电大学
    //    private final static String DATETIME_SNTP_SERVER = "s1b.time.edu.cn";//清华大学
    private static final String DATETIME_SNTP_SERVER = "cn.ntp.org.cn";//CHINA
    private static String TAG = "MainActivity";
    private static Context context;

    private TextView timeTextView;
    //在点击一次start时，计算一次finalHost
    private static String finalHost = DATETIME_SNTP_SERVER;
    private static ResultListViewAdapter<String> resultListAdapter;
    private static Handler mHandler = new MyHandler();
    private EditText hostInputEditText;
    private Spinner hostSpinner;
    private ListView listView;
    private SpinnerAdapter spinnerAdapter;

    /**
     * 校准时间
     * 从ntp服务器中获取时间
     *
     * @param mhostAddress
     * @param cycleTime
     */
    public static void startCalibrateTime(final String mhostAddress, final int cycleTime) {
        Log.d(TAG, "startCalibrateTime()");
        if (mCycleTimer != null) {
            mCycleTask.cancel();
            mCycleTimer.cancel();
            mCycleTimer.purge();
            mCycleTask = null;
            mCycleTimer = null;
        }


        mCycleTask = new TimerTask() {

            @Override
            public void run() {
                Log.d(TAG, "run: ");
                long time = getTimeFromNtpServer(finalHost);//从获取ntp服务器上获取时间
                if (time == -1) {
                    //获取时间失败
                    Log.e(TAG, "run: get time failed");

                } else {
                    //设置时间
                    boolean success = SystemClock.setCurrentTimeMillis(time);//设置系统时间
                    if (success) {
                        addToListView("设置系统时间 成 功 ！");
                    } else {
                        addToListView("设置系统时间失败");
                    }
                }

            }

            @Override
            public boolean cancel() {
                Log.d(TAG, "task cancel()");
                return super.cancel();
            }
        };
        //        mCycleTimer = new NgnTimer();
        mCycleTimer = new Timer();
        mCycleTimer.schedule(mCycleTask, 0, cycleTime);
        Log.d(TAG, "startOnClick ntp timer  time:" + cycleTime / 1000 + "秒");
    }

    public static long getTimeFromNtpServer(String hostAddress) {
        Log.d(TAG, "getTimeFromNtpServer()");
        if (TextUtils.isEmpty(hostAddress)) {
            Log.e(TAG, "Ntp host is empty !");
            return -1;
        }
        if (mNtpClient == null) {
            mNtpClient = new SntpClient();
        }
        SntpClient.RequestTimeResult requestTimeResult = mNtpClient.requestTime(hostAddress,
                                                                                TIME_OUT_MILLION);
        Log.d(TAG, "requestTimeSuccessful:" + requestTimeResult.success);
        if (requestTimeResult.success) {
            Log.d(TAG, "async time success.");
            long now = mNtpClient.getNtpTime();//now就是获取的时间
            addToListView("获取时间 成 功 !");
            return now;
        } else {
            Log.e(TAG, "async time failed.");
            addToListView("获取时间失败：" + requestTimeResult.msg);
        }
        return -1;
    }

    private static void toast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void addToListView(final String content) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultListAdapter.add(String.format("%s:%s",
                                                    (resultListAdapter.getCount() + 1),
                                                    content));

            }
        });
    }

    public static void stopCalibrateTimer() {
        if (mCycleTimer != null) {
            mCycleTask.cancel();
            mCycleTimer.cancel();
            mCycleTimer.purge();
            mCycleTask = null;
            mCycleTimer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;

        spinnerAdapter = new HostSpinnerAdapter(this);
        resultListAdapter = new ResultListViewAdapter<>(this,
                                                        android.R.layout.simple_list_item_1,
                                                        new ArrayList<String>());


        timeTextView = findViewById(R.id.time);
        hostInputEditText = findViewById(R.id.hostInput);
        hostSpinner = findViewById(R.id.hostSpinner);
        hostSpinner.setAdapter(spinnerAdapter);
        listView = findViewById(R.id.listView);
        listView.setAdapter(resultListAdapter);

        hostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostInputEditText.setText(spinnerAdapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this, Manifest.permission.SET_TIME, Manifest.permission.SET_TIME_ZONE);
        }

        boolean isRoot = ReallyShellUtil.canRunRootCommands();
        Log.i(TAG, "isRoot = " + isRoot);

    }

    public void startOnClick(View view) {
        Toast.makeText(this, "Click Start", Toast.LENGTH_SHORT).show();

        stopCalibrateTimer();

        //如果有输入，使用输入host
        String inputHostStr = hostInputEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(inputHostStr)) {
            finalHost = inputHostStr;
        }

        startCalibrateTime(finalHost, TASK_CYCLE_TIME);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final String timestr = new SimpleDateFormat("hh:mm:ss.SSS").format(new Date());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timeTextView.setText(timestr);
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(false);
        t.start();
    }

    public void clearLogs() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultListAdapter.clear();
            }
        });
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 以下4个方法，使用PermissionGen 框架，针对android 6.x sdk 获取系统某些权限
     * by liuyihui
     *
     * @param activity    活动实例
     * @param permissions 不定长权限数组
     */
    public void getPermission(Activity activity, String... permissions) {
        PermissionGen.with(activity).addRequestCode(100).permissions(permissions).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(MainActivity.this,
                                                 requestCode,
                                                 permissions,
                                                 grantResults);

    }

    @PermissionSuccess(requestCode = 100)
    public void onSuccess() {
        //ToastUtil.toast("已获取权限");
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

        }
    }


}
