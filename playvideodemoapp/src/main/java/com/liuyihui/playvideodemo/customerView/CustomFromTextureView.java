package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;

/**
 * 自定义视频view，根据TextureView。
 * <p>
 * 发现使用textureView的rotation属性才能旋转视频内容，surfaceView不能。
 */
public class CustomFromTextureView extends FrameLayout {
    private final String TAG = "CustomFromTextureView";
    private Context context;
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface surface;
    private String path;
    private int width;
    private int height;

    public CustomFromTextureView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomFromTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomFromTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    public void init() {
        setBackgroundColor(Color.BLACK);
    }

    public void setVideoPath(String path) {
        this.path = path;
        TextureView oldTextureView = textureView;
        //add a new texture view. on texture available ,create media player play a file
        textureView = new TextureView(context);
        addView(textureView);
        removeView(oldTextureView);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                surface = new Surface(surfaceTexture);
                Log.d(TAG, "onSurfaceTextureAvailable: ");
                playThePath();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                surfaceTexture = null;
                mediaPlayer.stop();
                mediaPlayer.release();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
    }

    public void playThePath() {
        mediaPlayer = new MediaPlayer();
        Log.d(TAG, path);
        Log.d(TAG, "setVideoPath: " + (surface == null));

        //播放
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setSurface(surface);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    mediaPlayer.start();
                    if (onPreparedListener != null) {
                        onPreparedListener.onPrepared(mediaPlayer);
                    }

                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    if (onInfoListener != null) {
                        onInfoListener.onInfo(mediaPlayer, i, i1);
                    }
                    return false;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (onErrorListener != null) {
                        onErrorListener.onError(mp, what, extra);
                    }
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (onCompletionListener != null) {
                        onCompletionListener.onCompletion(mp);
                    }
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "setVideoPath: end");
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        } else {
            return false;
        }
    }

    public void stopPlayback() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            Log.d(TAG, "start: ");
            mediaPlayer.start();
        }
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return -1;
        }
    }

    private MediaPlayer.OnPreparedListener onPreparedListener;
    private MediaPlayer.OnInfoListener onInfoListener;
    private MediaPlayer.OnErrorListener onErrorListener;
    private MediaPlayer.OnCompletionListener onCompletionListener;

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        this.onPreparedListener = onPreparedListener;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener onInfoListener) {
        this.onInfoListener = onInfoListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 重写 让视频尺寸拉伸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + w + "," + h);
        setMeasuredDimension(w, h);
    }*/
}
