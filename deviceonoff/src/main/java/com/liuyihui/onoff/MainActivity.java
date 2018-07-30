package com.liuyihui.onoff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.oohlink.wifiprobe.deviceonoff.R;
import com.wits.serialport.AutoOnoffUtil;

/**
 * 测试定时开关机的demo
 */
public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * 设置开机时间
     *
     * @param view
     */
    public void setOnTime(View view) {
        //设置定时开机?
        new AutoOnoffUtil().setOnTime("17:15:00", new AutoOnoffUtil.SetSuccessListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "success");
                Toast.makeText(MainActivity.this, "设置开机倒计时成功", Toast.LENGTH_SHORT).show();
                /*Intent intentSetOff = new Intent();
                intentSetOff.setAction("wits.com.simahuan.shutdown");
                sendBroadcast(intentSetOff);*/

            }
        });
    }

    public void setOffTime(View view){

        new AutoOnoffUtil().setOffTime(this,"17:13:00");

    }
}
