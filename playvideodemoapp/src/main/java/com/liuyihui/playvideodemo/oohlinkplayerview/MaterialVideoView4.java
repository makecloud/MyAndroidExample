package com.liuyihui.playvideodemo.oohlinkplayerview;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 多video view 无缝切换模式
 *
 * @author makecloudl
 */
public class MaterialVideoView4 extends FrameLayout {
    private final String TAG = "MaterialVideoView";
    private Context mContext;
    private String mCurrentPath;
    private boolean needStopPreMatViewAtVideoPlay;
    private LayerAdView parentLayerView;
    private VideoViewPool videoViewPool;
    private Animation exitAnimation;

    public MaterialVideoView4(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initVideoView();

    }

    public MaterialVideoView4(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initVideoView();
    }

    public MaterialVideoView4(@NonNull Context context,
                              @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initVideoView();
    }

    private void initVideoView() {
        videoViewPool = new VideoViewPool();
        //setBackgroundColor(Color.YELLOW);
    }

    public void setResourcePath(String path) {
        if (TextUtils.equals(path, this.mCurrentPath)) {
            Log.d(TAG, "setResourcePath: path is same, just replay");

            if (videoViewPool.getPickedVideoView() != null) {
                //设置的路径跟上次播的相同，则重播而不是切换videoView
                videoViewPool.getPickedVideoView().seekTo(0);
                //解决重播时卡住
                if (!videoViewPool.getPickedVideoView().isPlaying()) {
                    Log.d(TAG, "setResourcePath: catch notPlaying, start play");
                    videoViewPool.getPickedVideoView().start();
                }
            }

            return;
        }

        this.mCurrentPath = path;
        Log.d(TAG, "setResourcePath: ");

        //从池里取一个videoView
        final VideoView videoView = videoViewPool.pickFreeVideoView(mContext);

        //设置videoView出错回调
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //videoView.stopPlayback();
                return true;
            }
        });

        //设置videoView准备回调
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "pointer video onPrepared: ");
            }
        });

        //设置videoView info回调（主要是开始渲染第一帧回调）
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                //开始显示回调
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    Log.d(TAG, "pointer player onInfo: render start");
                    bringToFront();

                    //上一个videoView退出动画
                    /*if (videoViewPool.getFreedVideoView() != null) {
                        exitAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //！一停video view就黑了
                                if (videoViewPool.getFreedVideoView() != null) {
                                    videoViewPool.getFreedVideoView().stopPlayback();
                                }

                                //视频播出后，再停掉 需要停的之前的MaterialView
                                if (parentLayerView != null && needStopPreMatViewAtVideoPlay) {
                                    Log.d(TAG, "onPlayerStateChanged: needStop ");
                                    parentLayerView.stopPreviousMaterialView();
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        videoViewPool.getFreedVideoView().startAnimation(exitAnimation);
                    }*/

                    //移除上个videoView
                    removeView(videoViewPool.getFreedVideoView());

                    //上个videoView停止(不加动画的时候，在此处停止)
                    if (videoViewPool.getFreedVideoView() != null) {
                        videoViewPool.getFreedVideoView().stopPlayback();
                    }

                    //视频播出后，再停掉 需要停的之前的MaterialView
                    if (parentLayerView != null && needStopPreMatViewAtVideoPlay) {
                        Log.d(TAG, "onPlayerStateChanged: needStop ");
                        //parentLayerView.stopPreviousMaterialView();
                    }

                }
                return false;
            }
        });


        //添加 该videoView
        if (videoView.getParent() != null) {
            ((ViewGroup) videoView.getParent()).removeView(videoView);
        }
        addView(videoView);

        //设置path
        videoView.setVideoPath(path);

        //开启播放
        videoView.start();
    }

    public void playOn() {
        Log.d(TAG,
              "playOn: " + videoViewPool.getPickedVideoView().getHolder().getSurfaceFrame().top);
        Log.d(TAG,
              "playOn: " + videoViewPool.getPickedVideoView().getHolder().getSurfaceFrame().bottom);
        try {
            Log.d(TAG, "playOn: ");
            if (getVisibility() != VISIBLE) {
                setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连续使用MaterialVideoView播视频时，不要stopOff。
     * 只在切到其他类型素材时调用MaterialVideoView的stopOff
     */
    public void stopOff() {
        setVisibility(View.INVISIBLE);
        //延迟删除view，防止闪屏
        postDelayed(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
                Log.d(TAG, "stopOff: ");
                //被stopOff代表切到其他类型素材，视频池清空
                if (videoViewPool != null) {
                    videoViewPool.clearPool();
                }
            }
        }, 1000);
        //防止切到其他matView后再切matVideoView遇到相同path，从而导致重播失败因为stopOff已经释放了videoView
        this.mCurrentPath = null;
        Log.d(TAG, "delay stopOff: ");
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        //videoViewA.setOnCompletionListener(onCompletionListener);
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        //        videoViewA.setOnPreparedListener(onPreparedListener);
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        //        videoViewA.setOnErrorListener(onErrorListener);
    }

    public void release() {
        if (videoViewPool != null) {
            videoViewPool.release();
            videoViewPool = null;
        }
        mCurrentPath = null;
    }

    public void setStopPreMatViewAtVideoPlay(boolean needStopPreviousMaterialView) {
        this.needStopPreMatViewAtVideoPlay = needStopPreviousMaterialView;
    }

    public boolean getNeedStopPreMatViewAtVideoPlay() {
        return needStopPreMatViewAtVideoPlay;
    }

    public void setParentLayerView(LayerAdView parentLayerView) {
        this.parentLayerView = parentLayerView;
    }

    public LayerAdView getParentLayerView() {
        return parentLayerView;
    }

    class VideoViewPool {
        //两个videoView
        private VideoView videoViewA;
        private VideoView videoViewB;
        //两个指针
        private VideoView pickedVideoView;
        private VideoView freedVideoView;

        public VideoViewPool() {
        }

        //获取一个未使用的videoView
        public synchronized VideoView pickFreeVideoView(Context context) {
            if (videoViewA == null) {
                videoViewA = createNewVideoView(context);
                pickedVideoView = videoViewA;
                freedVideoView = videoViewB;
                return pickedVideoView;
            }
            if (videoViewB == null) {
                videoViewB = createNewVideoView(context);
                pickedVideoView = videoViewB;
                freedVideoView = videoViewA;
                return pickedVideoView;
            }

            VideoView ret = freedVideoView;

            //两个指针交换指向
            if (freedVideoView == videoViewA) {
                freedVideoView = videoViewB;
                pickedVideoView = videoViewA;
            } else {
                freedVideoView = videoViewA;
                pickedVideoView = videoViewB;
            }

            return ret;
        }

        public VideoView getPickedVideoView() {
            return pickedVideoView;
        }

        public VideoView getFreedVideoView() {
            return freedVideoView;
        }

        public VideoView createNewVideoView(Context context) {
            VideoView mVideoView = new VideoView(context);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                 ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                 Gravity.CENTER);
            mVideoView.setLayoutParams(layoutParams);
            return mVideoView;
        }

        public void clearPool() {
            release();
        }

        public void release() {
            if (videoViewA != null) {
                videoViewA.stopPlayback();
                videoViewA = null;
            }
            if (videoViewB != null) {
                videoViewB.stopPlayback();
                videoViewB = null;
            }
        }
    }
}
