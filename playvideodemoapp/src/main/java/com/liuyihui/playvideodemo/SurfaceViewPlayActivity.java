package com.liuyihui.playvideodemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class SurfaceViewPlayActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private SurfaceView surfaceView;
    private ProgressBar progressBar;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("MediaPlayer+SurfaceView播放视频");
        surfaceView = findViewById(R.id.sufaceView);

        init();
    }

    public void init() {
        mediaPlayer1 = new MediaPlayer();
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

                mediaPlayer1.stop();
                mediaPlayer1.release();
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
                String path = "/sdcard/oohlink/player/.screen/0A638DE2475566D0691CECD3F00B19D3";
                //播放
                try {
                    //mediaPlayer1.setAudioStreamType(AudioManager.audi);
                    mediaPlayer1.setDataSource(path);
                    mediaPlayer1.setDisplay(surfaceView.getHolder());
                    //mediaPlayer1.prepare();
                    mediaPlayer1.prepareAsync();
                    mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //                    mediaPlayer1.start();
                            //                    mediaPlayer1.reset();

                        }
                    });
                    Thread.sleep(2000);

                    mediaPlayer1.setLooping(true);

                    mediaPlayer1.start();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void prepareVideo1(View view) {
        long s1 = System.currentTimeMillis();
        String path = "/sdcard/oohlink/player/.screen/549A2C1EBCC166B1CD6104B4BC0609A9";

        try {
            mediaPlayer1.reset();
            mediaPlayer1.setDataSource(path);
            mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    Log.d(TAG, "onPrepared: " + (System.currentTimeMillis() - s1));
                }
            });
            mediaPlayer1.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onInfo: " + (System.currentTimeMillis() - s1));
                    return false;
                }
            });
            mediaPlayer1.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.d(TAG, "onBufferingUpdate: " + percent);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            mediaPlayer1.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepareVideo: ", e);
        }

    }

    public void prepareVideo2(View view) {
        String path = "/sdcard/oohlink/player/.screen/0A638DE2475566D0691CECD3F00B19D3";
        try {
            mediaPlayer2.setDataSource(path);
            mediaPlayer2.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepareVideo2: ", e);
        }
    }

    public void Player1Start(View view) {
        mediaPlayer1.start();
    }

    public void Player2Start(View view) {
        mediaPlayer2.start();
    }

    public void Player1SetView(View view) {
        mediaPlayer1.setDisplay(surfaceView.getHolder());
    }

    public void Player2SetView(View view) {
        mediaPlayer2.setDisplay(surfaceView.getHolder());
    }
}
