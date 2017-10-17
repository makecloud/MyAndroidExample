package com.liuyihui.playvideodemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.liuyihui.mylibrary.activity.MySuperActivity;

import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class Main2Activity extends MySuperActivity {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private Button startPlayButton;
    private Button stopPlayButton;
    private Button pausePlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("MediaPlayer+SurfaceView播放视频");
        surfaceView = (SurfaceView) findViewById(R.id.sufaceView);
        startPlayButton = (Button) findViewById(R.id.startPlay);
        stopPlayButton = (Button) findViewById(R.id.stopPlay);
        pausePlayButton = (Button) findViewById(R.id.pausePlay);

        init();

    }

    public void init() {
        mediaPlayer = new MediaPlayer();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                playVideo();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }


    public void playVideo() {
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String path = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
//        String path = sdcardDir + "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
//        String path = sdcardDir + "/360/C06FABBCF84118FD003D0D1269762C0E";
        String path = sdcardDir + "/oohlink/player/.screen/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

        Log.e(TAG, path);
        File file = new File(path);
        Log.e(TAG, String.valueOf(file.exists()));

        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path);
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
