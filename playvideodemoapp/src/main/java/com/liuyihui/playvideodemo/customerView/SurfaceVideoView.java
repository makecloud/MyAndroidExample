package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;


import java.io.IOException;

public class SurfaceVideoView extends FrameLayout {
    private final String TAG = "SurfaceVideoView";
    private Context mContext;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private String mCurrentPath;

    public SurfaceVideoView(Context context) {
        super((context));
        this.mContext = context;
        mediaPlayer = new MediaPlayer();
        surfaceView = new SurfaceView(mContext);
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
            }
        });
        mediaPlayer.setDisplay(holder);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                     LayoutParams.MATCH_PARENT,
                                                     Gravity.CENTER);
        surfaceView.setLayoutParams(layoutParams);
        addView(surfaceView);
        //setBackgroundColor(Color.BLACK);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setResourcePath(String path) {
        this.mCurrentPath = path;

    }

    public void playOn() {
        try {
            mediaPlayer.setDataSource(mCurrentPath);
        } catch (IOException e) {
            Log.e(TAG, "setResourcePath: ", e);
        }
        mediaPlayer.start();
    }

    public void stopOff() {
        setVisibility(View.INVISIBLE);
        mediaPlayer.pause();
        mediaPlayer.stop();
    }

    public void release() {
        setVisibility(View.INVISIBLE);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
        mediaPlayer.setOnCompletionListener(null);
        mediaPlayer.setOnPreparedListener(null);
    }
}
