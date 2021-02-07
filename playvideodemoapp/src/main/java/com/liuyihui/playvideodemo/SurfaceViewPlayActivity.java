package com.liuyihui.playvideodemo;

import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class SurfaceViewPlayActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("MediaPlayer+SurfaceView播放视频");
        init();
    }

    public void init() {
        surfaceView = findViewById(R.id.sufaceView);
        mediaPlayer = new MediaPlayer();
        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceCreated: ");
                //playVideo();
                mediaPlayer.setDisplay(surfaceView.getHolder());

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
            }
        });

        //视频源文件路径
        //String path = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
        String path = "/sdcard/oohlink/player/.screen/bbf929e236d53822899d9f3ff968602d";
        //播放
        try {
            //mediaPlayer.setAudioStreamType(AudioManager.audi);
            mediaPlayer.setDataSource(path);

            mediaPlayer.prepare();
//            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                }
            });

            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        } catch (IOException e) {
            Log.e(TAG, "run: ", e);
        }
    }

}
