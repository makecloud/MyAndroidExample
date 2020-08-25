package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 自定义平滑切换视频的ijkPlayerView
 * <p>
 * Created by liuyi on 2018/9/4.
 */

public class SmoothSwitchIjkVideoView extends FrameLayout {
    private final String TAG = "IjkVideoView";
    /**
     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
     */
    private IjkMediaPlayer ijkPlayer = null;
    /**
     * 视频文件地址
     */
    private String mPath = "";

    private SurfaceView surfaceView;

    private TotalVideoPlayerListener totalListener;
    private Context mContext;

    public SmoothSwitchIjkVideoView(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public SmoothSwitchIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public SmoothSwitchIjkVideoView(@NonNull Context context,
                                    @Nullable AttributeSet attrs,
                                    @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        mContext = context;

        //获取焦点，不知道有没有必要~。~
        setFocusable(true);
    }

    /**
     * 设置视频地址。（入口）
     * ↓
     * 创建surfaceView
     * ↓
     * 在surfaceHolder创建回调中创建player，➞player设置sourcePath，➞player设置display为该surface，➞已步准备视频
     *
     * <p>
     * 根据是否第一次播放视频，做不同的操作。
     *
     * @param path the path of the video.
     */

    public void setVideoPath(String path) {
        if (TextUtils.equals("", mPath)) {
            //如果是第一次播放视频，那就创建一个新的surfaceView
            mPath = path;
            createSurfaceView();
        } else {
            //否则就直接load
            mPath = path;
            ijiPlayerLoadVideo();
        }
    }

    public void setNewPath() {
        createPlayer();

        //ijkplayer设置资源路径
        try {
            ijkPlayer.setDataSource(mPath);
        } catch (IOException e) {
            Log.e(TAG, "ijiPlayerLoadVideo: ", e);
        }

        //给mediaPlayer设置视图
        ijkPlayer.setDisplay(surfaceView.getHolder());


        //异步准备
        ijkPlayer.prepareAsync();
    }

    /*
     * 新建一个surfaceview
     */
    private void createSurfaceView() {
        //生成一个新的surface view
        surfaceView = new SurfaceView(mContext);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");
                //surfaceview创建成功后，加载视频
                ijiPlayerLoadVideo();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
            }
        });

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                     LayoutParams.MATCH_PARENT,
                                                     Gravity.CENTER);
        surfaceView.setLayoutParams(layoutParams);
        this.addView(surfaceView);
    }

    /*
     * 加载视频
     */
    private void ijiPlayerLoadVideo() {
        //每次都要重新创建IMediaPlayer
        createPlayer();

        //ijkplayer设置资源路径
        try {
            ijkPlayer.setDataSource(mPath);
        } catch (IOException e) {
            Log.e(TAG, "ijiPlayerLoadVideo: ", e);
        }

        //给mediaPlayer设置视图
        ijkPlayer.setDisplay(surfaceView.getHolder());

        //异步准备
        ijkPlayer.prepareAsync();
    }

    /**
     * 创建一个新的player
     */
    private void createPlayer() {
        if (ijkPlayer != null) {
            ijkPlayer.stop();
            ijkPlayer.setDisplay(null);
            ijkPlayer.release();
        }
        //ijkplayer native日志级别
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        //开启硬解码
        // ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        //创建ijkplayer
        ijkPlayer = new IjkMediaPlayer();

        if (totalListener != null) {
            ijkPlayer.setOnPreparedListener(totalListener);
            ijkPlayer.setOnInfoListener(totalListener);
            ijkPlayer.setOnSeekCompleteListener(totalListener);
            ijkPlayer.setOnBufferingUpdateListener(totalListener);
            ijkPlayer.setOnErrorListener(totalListener);
        }
    }


    public void setTotalPlayerListener(TotalVideoPlayerListener listener) {
        this.totalListener = listener;
        if (ijkPlayer != null) {
            ijkPlayer.setOnPreparedListener(listener);
            ijkPlayer.setOnInfoListener(listener);
            ijkPlayer.setOnSeekCompleteListener(listener);
            ijkPlayer.setOnBufferingUpdateListener(listener);
            ijkPlayer.setOnErrorListener(listener);
        }
    }

    /* *
     * -------======--------- 下面封装了一下控制视频的方法

     */
    public void start() {
        if (ijkPlayer != null) {
            ijkPlayer.start();
        }
    }

    public void release() {
        if (ijkPlayer != null) {
            ijkPlayer.reset();
            ijkPlayer.release();
            ijkPlayer = null;
        }
    }

    public void pause() {
        if (ijkPlayer != null) {
            ijkPlayer.pause();
        }
    }

    public void stop() {
        if (ijkPlayer != null) {
            ijkPlayer.stop();
        }
    }


    public void reset() {
        if (ijkPlayer != null) {
            ijkPlayer.reset();
        }
    }


    public long getDuration() {
        if (ijkPlayer != null) {
            return ijkPlayer.getDuration();
        } else {
            return 0;
        }
    }


    public long getCurrentPosition() {
        if (ijkPlayer != null) {
            return ijkPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }


    public void seekTo(long l) {
        if (ijkPlayer != null) {
            ijkPlayer.seekTo(l);
        }
    }

}
