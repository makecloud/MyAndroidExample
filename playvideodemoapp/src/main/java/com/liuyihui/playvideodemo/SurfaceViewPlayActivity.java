package com.liuyihui.playvideodemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class SurfaceViewPlayActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private SurfaceView surfaceView;
    private ProgressBar progressBar;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
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
        mediaPlayer2 = new MediaPlayer();
        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceCreated: ");
                //playVideo();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceDestroyed: ");

                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer2.stop();
                mediaPlayer2.release();
            }
        });
    }


    public void playVideo() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //视频源文件路径
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

    public void prepareVideo(View view) {
        long s1 = System.currentTimeMillis();
        String videoFilePath = Environment.getExternalStorageDirectory() + "/oohlink/player/" +
                ".screen/B00D01FECB38E8F56AFECB9E4B33B992";
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoFilePath);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    Log.d(TAG, "onPrepared: " + (System.currentTimeMillis() - s1));
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onInfo: " + (System.currentTimeMillis() - s1));
                    return false;
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.d(TAG, "onBufferingUpdate: " + percent);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void prepareVideo2(View view) {

        String videoFilePath = Environment.getExternalStorageDirectory() + "/Android/data" +
                "/com" + ".oohlink.smartbillborad/files/material/video2";
        try {
            mediaPlayer2.setDataSource(videoFilePath);
            mediaPlayer2.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlayerStart(View view) {
        mediaPlayer.start();
    }

    public void Player2Start(View view) {
        mediaPlayer2.start();
    }

    public void PlayerSetView(View view) {
        mediaPlayer.setDisplay(surfaceView.getHolder());
    }

    public void Player2SetView(View view) {
        mediaPlayer2.setDisplay(surfaceView.getHolder());
    }
}
