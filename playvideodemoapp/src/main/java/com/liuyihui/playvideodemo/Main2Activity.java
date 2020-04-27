package com.liuyihui.playvideodemo;

import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;

import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class Main2Activity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

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
        surfaceView = findViewById(R.id.sufaceView);
        startPlayButton = findViewById(R.id.startPlay);
        stopPlayButton = findViewById(R.id.stopPlay);
        pausePlayButton = findViewById(R.id.pausePlay);

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
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
    }


    public void playVideo() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                //视频源文件路径
                String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                //        String videoFilePath = sdcardDir + "/DCIM/qqqq";
                String videoFilePath = Environment.getExternalStorageDirectory() + "/Android/data"
                        + "/com" + ".oohlink.smartbillborad/files/mat/video";


                //        String videoFilePath = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
                //        String videoFilePath = sdcardDir +
                //        "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
                //        String videoFilePath = sdcardDir + "/360/mcdown.mp4";
                //        String videoFilePath = sdcardDir + "/360/dianshiju.mp4";
                //        String videoFilePath = sdcardDir + "/360/video1";
                //        String videoFilePath = sdcardDir + "/360/video2";
                //        String videoFilePath = sdcardDir + "/360/video3";
                //        String videoFilePath = sdcardDir +
                //        "/360/C06FABBCF84118FD003D0D1269762C0E";
                //        String videoFilePath = sdcardDir + "/oohlink/player/
                //        .screen/C06FABBCF84118FD003D0D1269762C0E";
                //        String videoFilePath = sdcardDir + "/oohlink/player/
                //        .screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

                Log.e(TAG, videoFilePath);
                File file = new File(videoFilePath);
                Log.e(TAG, String.valueOf(file.exists()));

                //播放
                try {
                    //mediaPlayer.setAudioStreamType(AudioManager.audi);
                    mediaPlayer.setDataSource(videoFilePath);
                    mediaPlayer.setDisplay(surfaceView.getHolder());
                    //mediaPlayer.prepare();
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //                    mediaPlayer.start();
                            //                    mediaPlayer.reset();

                        }
                    });
                    Thread.sleep(2000);

                    mediaPlayer.setLooping(true);

                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
