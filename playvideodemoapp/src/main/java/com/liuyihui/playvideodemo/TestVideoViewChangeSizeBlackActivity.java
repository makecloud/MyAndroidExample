package com.liuyihui.playvideodemo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.oohlinkplayerview.LayerAdView;
import com.liuyihui.playvideodemo.oohlinkplayerview.LayerTimeView;
import com.liuyihui.playvideodemo.oohlinkplayerview.ScreenLayout;

/**
 * 测试videoView播放中从全屏缩小后，缩小的部分黑屏
 */
public class TestVideoViewChangeSizeBlackActivity extends AppCompatActivity {
    private final String TAG = "switchVideoView";
    private ScreenLayout screenLayout;
    private LayerAdView layerView;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_test_video_view_changesizeblack);
        screenLayout = findViewById(R.id.containerLayout);


        layerView = new LayerAdView(this);
        ScreenLayout.LayoutParams lp = new ScreenLayout.LayoutParams(1080, 1920);
        lp.setPosition(0, 0);
        screenLayout.addView(layerView, lp);

        layerView.prepare();
        layerView.playOn();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeNewVideo();
            }
        }, 35 * 1000);

    }


    public void changeNewVideo() {


        String videoPath = "/sdcard/oohlink/player/.screen/72EE7BAD3659665BFF44C5EF23CF32B4";


        //新视广告层view
        LayerAdView layerAdView = new LayerAdView(this);
        ScreenLayout.LayoutParams lp = new ScreenLayout.LayoutParams(1080, 1190);
        lp.setPosition(0, 0);
        screenLayout.addView(layerAdView, lp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            layerAdView.prepare();
                            layerAdView.playOn();
                        }
                    });
                    try {
                        Thread.sleep(35 * 1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e);
                    }
                }
            }
        }).start();


        //时间层view
        LayerTimeView layerTimeView = new LayerTimeView(this);
        ScreenLayout.LayoutParams lp2 = new ScreenLayout.LayoutParams(500, 50);
        lp2.setPosition(200, 1500);
        screenLayout.addView(layerTimeView, lp2);


        //移除前一个screen
        /**
         * 删除释放上一个逻辑分屏图层\
         */
        screenLayout.removeView(layerView);
        layerView.release();

    }


}

