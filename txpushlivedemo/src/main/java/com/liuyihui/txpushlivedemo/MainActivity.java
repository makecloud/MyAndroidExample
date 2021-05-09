package com.liuyihui.txpushlivedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.liuyihui.txpushlivedemo.databinding.ActivityMainBinding;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    //private String rtmpUrl = "rtmp://2157.livepush.myqcloud.com/live/xxxxxx";
    private String server = "rtmp://zhibo.oohlink.com/oohlink/";
    private String secret = "161909378955440?auth_key=1619097389-0-0-daed36b53d50c181c486f842f1715a0d";
    private String rtmpUrl = server + secret;

    private TXLivePusher mLivePusher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mLivePusher = new TXLivePusher(MainActivity.this);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();

        mLivePusher.setConfig(mLivePushConfig);

        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {
                Log.d(TAG, "onPushEvent: " + CPushEvent.getDescribeFromValue(i));

            }

            @Override
            public void onNetStatus(Bundle bundle) {
                Log.d(TAG, "onNetStatus: ");
            }
        });


        binding.startScreenPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLivePusher.startPusher(rtmpUrl);
                mLivePusher.startScreenCapture();
            }
        });
    }


    //结束录屏直播，注意做好清理工作
    public void stopPublish() {
        mLivePusher.stopScreenCapture();
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        stopPublish();
    }


}
