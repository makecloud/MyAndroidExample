package com.liuyihui.playvideodemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.customerView.MyIjkSurfaceVideoView;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class MySurfaceVideoViewDemoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private MyIjkSurfaceVideoView mySurfaceVideoView;
    private RelativeLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysurfacevideoviewdemo);
        getSupportActionBar().setTitle("自定义的MediaPlayer+SurfaceView播放器demo");

        rootLayout = findViewById(R.id.root_layout);

        mySurfaceVideoView = new MyIjkSurfaceVideoView(this);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                                                   RelativeLayout.LayoutParams.MATCH_PARENT);
        mySurfaceVideoView.setLayoutParams(layoutParams);
        rootLayout.addView(mySurfaceVideoView);

        //mySurfaceVideoView = findViewById(R.id.sufaceView);

        init();
    }

    public void init() {
        String path = "/sdcard/oohlink/player/.screen/8B9357192E4DBF750B99FD3C4C5CA1CC";
        mySurfaceVideoView.prepareVideo(path);
        //mySurfaceVideoView.start();
    }


    public void startPlay(View view) {
        mySurfaceVideoView.start();
        Log.d(TAG, "startPlay: ");
    }

    public void stopPlay(View view) {
        mySurfaceVideoView.getMediaPlayer().stop();
        Log.d(TAG, "stopPlay: ");
    }

    public void pausePlay(View view) {
        mySurfaceVideoView.getMediaPlayer().pause();
        Log.d(TAG, "pausePlay: ");
    }

    public void seekTo(View view) {
        Log.d(TAG, "seekTo: 0");
        mySurfaceVideoView.getMediaPlayer().seekTo(0);
    }

    public void getInfo(View view) {
        Log.d(TAG, "getInfo: isPlaying=" + mySurfaceVideoView.getMediaPlayer().isPlaying());
    }


}
