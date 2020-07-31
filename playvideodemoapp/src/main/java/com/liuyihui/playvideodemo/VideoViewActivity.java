package com.liuyihui.playvideodemo;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用videoView 播放视频
 */
public class VideoViewActivity extends AppCompatActivity {
    private final String TAG = "VideoViewActivity";
    private VideoView myVideoView;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        myVideoView = findViewById(R.id.myVideoView);
        init();
    }

    private void init() {
        //视频源文件路径
        //设置videoview
        //        myVideoView.setVideoURI(Uri.parse("http://2449.vod.myqcloud
        //        .com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"));
        //        myVideoView.setVideoURI(Uri.parse("https://ygsd-test.oss-cn-beijing.aliyuncs" +
        //        ".com" + "/material/40" +
        //                                                  "/BFA91EE06E2EE723A2C08B4B656605D8
        //                                                  .mp4"));
        myVideoView.setVideoURI(Uri.parse("https://ygsd-test.oss-cn-beijing.aliyuncs" + ".com" +
                                                  "/material/40/BFA91EE06E2EE723A2C08B4B656605D8" + ".mp4"));
        //播放结束回调
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onCompletion: ");
            }
        });

        //加载素材回调. 未执行start也会回调，应该是设置了资源就会准备，准备到可以播放了就会回调
        myVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared: ");
                //控制视频按比例缩小或者拉伸
                {
                    // 首先取得video的宽和高
                    int vWidth = mediaPlayer.getVideoWidth();
                    int vHeight = mediaPlayer.getVideoHeight();
                    Log.i(TAG, "video Self size:" + vWidth + "*" + vHeight);

                    // 该LinearLayout的父容器 android:orientation="vertical" 必须
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_parent);
                    int lw = linearLayout.getWidth();
                    int lh = linearLayout.getHeight();
                    Log.i(TAG, "linearLayout Self size:" + lw + "*" + lh);

                    // 如果video的宽或者高超出了当前屏幕的大小，选择大的一个边缩放
                    if (vWidth > lw || vHeight > lh) {
                        float wRatio = (float) vWidth / (float) lw;
                        float hRatio = (float) vHeight / (float) lh;

                        // 选择大的一个进行缩放
                        float ratio = Math.max(wRatio, hRatio);
                        vWidth = (int) Math.ceil((float) vWidth / ratio);
                        vHeight = (int) Math.ceil((float) vHeight / ratio);

                        Log.i(TAG, "fitted size:" + vWidth + "*" + vHeight);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(vWidth,
                                                                                     vHeight);
                        lp.gravity = Gravity.CENTER;
                        //如果用的surfaceView 则设置surfaceView的布局参数
                        //                    surfaceView.setLayoutParams(lp);
                        //如果用的videoView 则设置surfaceView的布局参数
                        myVideoView.setLayoutParams(lp);
                    }
                    //如果video的宽高全部小于屏幕宽高
                    else if (vWidth < lw && vHeight < lh) {
                        //选择大的边拉伸
                        // 选择大的一个进行缩放
                        float wRatio = (float) vWidth / (float) lw;
                        float hRatio = (float) vHeight / (float) lh;

                        // 选择大的一个进行缩放
                        float ratio = Math.max(wRatio, hRatio);
                        vWidth = (int) Math.ceil((float) vWidth / ratio);
                        vHeight = (int) Math.ceil((float) vHeight / ratio);

                        Log.i(TAG, "fitted size:" + vWidth + "*" + vHeight);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(vWidth,
                                                                                     vHeight);
                        lp.gravity = Gravity.CENTER;
                        //如果用的surfaceView 则设置surfaceView的布局参数
                        //                    surfaceView.setLayoutParams(lp);
                        //如果用的videoView 则设置surfaceView的布局参数
                        myVideoView.setLayoutParams(lp);
                    }

                }
            }
        });

        //info？ 回调。start后会回调：MEDIA_INFO_VIDEO_RENDERING_START
        myVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                //MediaPlayer.MEDIA_INFO_AUDIO_NOT_PLAYING;//804
                //MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START;//3
                //MediaPlayer.MEDIA_INFO_STARTED_AS_NEXT;//2
                Log.d(TAG, "onInfo: " + what);
                return false;
            }
        });

        //设置 控制器view
        mediaController = new MediaController(this);
        myVideoView.setMediaController(mediaController);


        // 启动播放
        //myVideoView.start();
        //myVideoView.requestFocus();

    }

    //按钮启动播放
    public void startPlay(View view) {
        myVideoView.start();
        //        myVideoView.requestFocus();
    }
}
