package com.liuyihui.playvideodemo;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+TextureView播放视频
 * <p>
 * 附带 缩放功能
 */
public class TextureViewPlayActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private TextureView textureView;
    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("MediaPlayer+TextureView播放视频");

        textureView = findViewById(R.id.textureView);
        imageView = findViewById(R.id.imageView);

        init();

    }

    public void init() {
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                surface = new Surface(surfaceTexture);
                playVideo();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                textureView = null;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void playVideo() {
        mediaPlayer = new MediaPlayer();

        //视频源文件路径
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String path = sdcardDir + "/DCIM/qqqq";
        String path = Environment.getExternalStorageDirectory() + "/Android/data"
                + "/com" + ".oohlink.smartbillborad/files/mat/video";
//        String path = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
//        String path = sdcardDir + "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
//        String path = sdcardDir + "/360/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/360/mcdown.mp4";
//        String path = sdcardDir + "/360/dianshiju.mp4";
//        String path = sdcardDir + "/oohlink/player/.screen/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

        Log.e(TAG, path);
        File file = new File(path);
        Log.e(TAG, String.valueOf(file.exists()));

        //播放
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setSurface(surface);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //控制视频按比例缩小或者拉伸
                    {
                        // 首先取得video的宽和高
                        int vWidth = mediaPlayer.getVideoWidth();
                        int vHeight = mediaPlayer.getVideoHeight();
                        Log.i(TAG, "video Self size:" + vWidth + "*" + vHeight);

                        // 该LinearLayout的父容器 android:orientation="vertical" 必须
                        FrameLayout linearLayout = findViewById(R.id.ll_parent);
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
                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(vWidth, vHeight);
                            lp.gravity = Gravity.CENTER;
                            //如果用的surfaceView 则设置surfaceView的布局参数
//                    surfaceView.setLayoutParams(lp);
                            //如果用的videoView 则设置surfaceView的布局参数
                            textureView.setLayoutParams(lp);
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
                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(vWidth, vHeight);
                            lp.gravity = Gravity.CENTER;
                            //如果用的surfaceView 则设置surfaceView的布局参数
//                    surfaceView.setLayoutParams(lp);
                            //如果用的videoView 则设置surfaceView的布局参数
                            textureView.setLayoutParams(lp);

                        }

                    }
                    imageView.setVisibility(View.GONE);
                    mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    return false;
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
