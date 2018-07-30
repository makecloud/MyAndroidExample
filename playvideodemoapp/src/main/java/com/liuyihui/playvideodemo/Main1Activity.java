package com.liuyihui.playvideodemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.io.File;

/**
 * 使用videoView 播放视频
 */
public class Main1Activity extends AppCompatActivity {
    private final String TAG = "Main1Activity";
    private VideoView myVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        myVideoView = findViewById(R.id.myVideoView);
        init();
    }

    private void init() {
        //视频源文件路径
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String path = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
//        String path = sdcardDir + "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
        String path = sdcardDir + "/360/mcdown.mp4";
//        String path = sdcardDir + "/360/dianshiju.mp4";
//        String path = sdcardDir + "/360/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

        Log.e(TAG, path);
        File file = new File(path);
        Log.e(TAG, "视频文件存在:" + String.valueOf(file.exists()));

        //设置videoview, 播放
        myVideoView.setVideoPath(path);
        myVideoView.start();
        myVideoView.requestFocus();
        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                myVideoView.stopPlayback();
            }
        });
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

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
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(vWidth, vHeight);
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
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(vWidth, vHeight);
                        lp.gravity = Gravity.CENTER;
                        //如果用的surfaceView 则设置surfaceView的布局参数
//                    surfaceView.setLayoutParams(lp);
                        //如果用的videoView 则设置surfaceView的布局参数
                        myVideoView.setLayoutParams(lp);
                    }

                }
            }
        });

    }
}
