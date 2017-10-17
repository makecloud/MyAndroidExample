package com.liuyihui.playvideodemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

/**
 * 使用videoView播放视频
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private VideoView myVideoView;
    private Button playVideoDemo2;
    private Button playVideoDemo3;
    private Button playVideoDemo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myVideoView = (VideoView) findViewById(R.id.myVideoView);
        playVideoDemo2 = (Button) findViewById(R.id.playVideoDemo2);
        playVideoDemo3 = (Button) findViewById(R.id.playVideoDemo3);
        playVideoDemo4 = (Button) findViewById(R.id.playVideoDemo4);

        init();
    }

    private void init() {
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String path = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
//        String path = sdcardDir + "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
//        String path = sdcardDir + "/360/C06FABBCF84118FD003D0D1269762C0E";
        String path = sdcardDir + "/oohlink/player/.screen/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

        Log.e(TAG, path);
        File file = new File(path);
        Log.e(TAG, String.valueOf(file.exists()));

        myVideoView.setVideoPath(path);
        myVideoView.start();
        myVideoView.requestFocus();
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                myVideoView.stopPlayback();
            }
        });

    }

    public void playVideoDemo2CLick(View v) {
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
    }

    public void playVideoDemo3CLick(View v) {
        startActivity(new Intent(MainActivity.this, Main3Activity.class));
    }

    public void playVideoDemo4CLick(View v) {

    }
}
