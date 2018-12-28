package com.liuyihui.playvideodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

/**
 * 使用videoView播放视频
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private VideoView myVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /** 调用系统自带播放器 播放视频 */
    public void playVideoDemo4CLick(View v) {
        startActivity(new Intent(MainActivity.this, VideoViewActivity.class));
    }

    /** mediaPlayer + surfaceView播放视频 */
    public void playVideoDemo2CLick(View v) {
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
    }

    /** mediaplayer + textureView播放视频 */
    public void playVideoDemo3CLick(View v) {
        startActivity(new Intent(MainActivity.this, Main3Activity.class));
    }

    /** JCVideoPlayer使用 */
    public void playVideoDemo5Lick(View v) {
        startActivity(new Intent(MainActivity.this, JCVideoPlayerActivity.class));
    }

    /** IjkPlayer使用 */
    public void playVideoDemo6Lick(View v) {
        startActivity(new Intent(MainActivity.this, IjkPLayerActivity.class));
    }

    /** PLDroidPlayer使用 */
    public void playVideoDemo7Lick(View v) {
        startActivity(new Intent(MainActivity.this, PLDroidPlayerActivity.class));
    }

    /** PLDroidPlayer使用 */
    public void playVideoDemo8Lick(View v) {
        startActivity(new Intent(MainActivity.this, UniversalVideoViewActivity.class));
    }
}
