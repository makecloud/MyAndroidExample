package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 自定义的mediaplayer+surfaceview view
 */
public class MyIjkSurfaceVideoView extends SurfaceView {
    private final String TAG = "MySurfaceVideoView";
    private Context context;
    private SurfaceHolder holder;
    private IjkMediaPlayer mediaPlayer;
    private IMediaPlayer.OnPreparedListener onPreparedListener;
    private IMediaPlayer.OnInfoListener onInfoListener;
    private IMediaPlayer.OnCompletionListener onCompletionListener;
    private IMediaPlayer.OnErrorListener onErrorListener;

    public MyIjkSurfaceVideoView(Context context) {
        super(context);
        init(context);
    }

    public MyIjkSurfaceVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyIjkSurfaceVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        holder = getHolder();
        mediaPlayer = new IjkMediaPlayer();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceCreated: ");
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceDestroyed: ");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });
    }

    public void prepareVideo(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.setOnPreparedListener(new IjkMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    if (onPreparedListener != null) {
                        onPreparedListener.onPrepared(mp);
                    }
                    mp.start();
                }
            });
            mediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onInfo: ");
                    if (onInfoListener != null) {
                        return onInfoListener.onInfo(mp, what, extra);
                    }
                    return false;
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                    Log.d(TAG, "onBufferingUpdate: " + percent);

                }
            });

            mediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    Log.d(TAG, "onCompletion: ");
                    if (onCompletionListener != null) {
                        onCompletionListener.onCompletion(iMediaPlayer);
                    }
                }

            });

            mediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(IMediaPlayer mp) {
                    Log.d(TAG, "onSeekComplete: ");
                }
            });

            mediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError: ");
                    if (onErrorListener != null) {
                        return onErrorListener.onError(mp,what,extra);
                    }
                    return false;
                }
            });

            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            Log.e(TAG, "prepareVideo: ", e);
        }

    }


    public IjkMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void start() {
        mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void seekTo(int msec) {
        mediaPlayer.seekTo(msec);
    }
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }


    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.onPreparedListener = onPreparedListener;
    }

    public void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.onInfoListener = onInfoListener;
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }
}
