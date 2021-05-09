package com.liuyihui.playvideodemo.useSurfaceView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.R;

import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class SurfaceViewPlayActivity extends AppCompatActivity {
    private final String TAG = "SurfaceViewPlayActivity";

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private MediaPlayer oldPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview_play);
        getSupportActionBar().setTitle("MediaPlayer+SurfaceView播放视频");
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }


    public synchronized void init() {
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
                if (mediaPlayer == null) {
                    return;
                }
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });


        //视频源文件路径
        //String path = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
        String path = "/sdcard/oohlink/player/.screen/82E68A2991B6E51C4B1E890922D67514";
        //播放
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onInfo: " + what);
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion: ");
                }
            });

            mediaPlayer.prepare();
            //mediaPlayer.prepareAsync();
            //mediaPlayer.setLooping(true);
            //mediaPlayer.start();

        } catch (IOException e) {
            Log.e(TAG, "run: ", e);
        }
    }


    public void useAnewPlayerPrepareNewPathOnSurfaceView(View view) {
        //必须先释放。测试至少要先将之前player reset 不然新player prepare报状态异常
        oldPlayer = mediaPlayer;
        oldPlayer.reset();
        oldPlayer.release();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource("/sdcard/oohlink/player/.screen/E29F4CA2A85A3ABF9BF566222B906176");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * start 按钮
     *
     * @param view
     */
    public void startButtonClick(View view) {
        mediaPlayer.start();
    }

}
