package com.liuyihui.playvideodemo.useAndroidVideoView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.R;

/**
 * 测试videoView流畅切换
 */
public class TestSwitchVideoViewActivity extends AppCompatActivity {
    private final String TAG = "switchVideoView";
    private Animation exitAnimation;
    private PathMenu pathMenu;
    private VideoViewPool videoViewPool;
    private FrameLayout adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_switch_video_view);
        adView = findViewById(R.id.adView);
        //adView移除前一个videoView
        exitAnimation = AnimationUtils.loadAnimation(TestSwitchVideoViewActivity.this,
                                                     R.anim.exit_lefttoright);
        pathMenu = new PathMenu();
        videoViewPool = new VideoViewPool();
        startPlayerThread();
    }

    public void startPlayerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                while (true) {
                    //prepare
                    prepareNew(pathMenu.getNextPath());
                    //wait 5s
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e);
                    }
                }
            }
        }).start();

    }


    public void switchVideo(View view) {
        //触发切换
        prepareNew(pathMenu.getNextPath());
    }


    public void prepareNew(String path) {
        VideoView videoView = videoViewPool.pickFreeVideoView(this);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "pointer video onPrepared: ");
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                //开始显示回调
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    Log.d(TAG, "pointer player onInfo: render start");
                    //执行退出动画
                    if (videoViewPool.getFreedVideoView() != null) {
                        exitAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //！一停video view就黑了
                                videoViewPool.getFreedVideoView().stopPlayback();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        videoViewPool.getFreedVideoView().startAnimation(exitAnimation);
                    }

                    //移除上个videoView
                    adView.removeView(videoViewPool.getFreedVideoView());

                    //上个videoView停止(不加动画的时候，在此处停止)
                    /*if (videoViewPool.getFreedVideoView() != null) {
                        videoViewPool.getFreedVideoView().stopPlayback();
                    }*/
                }

                return false;
            }
        });


        //添加videoView到adView
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adView.addView(videoView);
            }
        });

        //设置path
        videoView.setVideoPath(path);

        //开启播放
        videoView.start();
        Log.d(TAG, "pointer videoView: 启动播放");
    }

    public void addImageOnVideo(View view) {
        String imagePath = "/sdcard/oohlink/player/.screen/9C36542268153DD03ED692B30DE2D021";
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ImageView imageView = new ImageView(this);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(lp);
        imageView.setImageBitmap(bitmap);

        adView.addView(imageView);
    }

    public void removeTopView(View view) {
        if (adView.getChildCount() > 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adView.removeViewAt(adView.getChildCount() - 1);
                }
            });
        }
    }

    public void bringToFront(View view) {
        if (adView.getChildCount() > 1) {
            adView.getChildAt(0).bringToFront();
        }
    }


    /**
     * 双videoView切换池
     */
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
                return videoViewA;
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
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                                 FrameLayout.LayoutParams.MATCH_PARENT,
                                                                                 Gravity.CENTER);
            mVideoView.setLayoutParams(layoutParams);
            return mVideoView;
        }
    }


    class PathMenu {
        private String[] paths = new String[]{"/sdcard/oohlink/player/" + ".screen" +
                                                      "/0A638DE2475566D0691CECD3F00B19D3",
                                              "/sdcard/oohlink/player/" + ".screen" +
                                                      "/549A2C1EBCC166B1CD6104B4BC0609A9"};

        private int i = 0;

        public String getNextPath() {
            String path;
            if (i > (paths.length - 1)) {
                i = 0;
            }
            path = paths[i];
            i += 1;
            return path;
        }
    }

}

