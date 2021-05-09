package com.liuyihui.playvideodemo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.liuyihui.playvideodemo.UseIjkPlayer.IjkPlayerDemoActivity;
import com.liuyihui.playvideodemo.useAndroidVideoView.CustomZoomedVideoViewActivity;
import com.liuyihui.playvideodemo.useAndroidVideoView.TestSwitchVideoViewActivity;
import com.liuyihui.playvideodemo.useAndroidVideoView.VideoViewActivity;
import com.liuyihui.playvideodemo.useSurfaceView.DoubleSurfaceViewPlayActivity;
import com.liuyihui.playvideodemo.useSurfaceView.SurfaceViewPlayActivity;
import com.liuyihui.playvideodemo.useTextureView.CustomTextureViewPlayActivity;
import com.liuyihui.playvideodemo.useTextureView.TextureViewPlayActivity;

/**
 * dispatch to other activitys
 */
public class MainActivity extends RequestPermissionActivity {
    private final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE,
                                                             Manifest.permission.ACCESS_NETWORK_STATE,
                                                             Manifest.permission.INTERNET,
                                                             Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            requestPermission(null);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = MediaUtil.getLocalVideoBitmap(
                "/storage/emulated/0/oohlink/player/.screen/549A2C1EBCC166B1CD6104B4BC0609A9");

        Log.d(TAG, "onResume: " + bitmap);
    }

    /** 系统自带VideoView demo */
    public void androidVideoViewDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, VideoViewActivity.class));
    }

    /** 自定义 实现videoView缩放 demo */
    public void videoViewZoomDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, CustomZoomedVideoViewActivity.class));
    }

    /**
     * videoView 切换demo
     *
     * @param view
     */
    public void switchVideoViewDemoClick(View view) {
        startActivity(new Intent(this, TestSwitchVideoViewActivity.class));
    }

    /** mediaPlayer + surfaceView播放视频 demo */
    public void surfaceMediaPlayerDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, SurfaceViewPlayActivity.class));
    }

    /** 双surfaceView实现无缝切换播放视频 */
    public void doubleSurfaceDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, DoubleSurfaceViewPlayActivity.class));
    }

    /** textureView+mediaPlayer 播放视频 */
    public void textureViewMediaPlayerDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, TextureViewPlayActivity.class));
    }

    /** 使用自定义的TextureView播放视频demo */
    public void customTextureVideoDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, CustomTextureViewPlayActivity.class));
    }

    /** IjkPlayer使用 */
    public void ijkPlayerDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, IjkPlayerDemoActivity.class));
    }

    /** PLDroidPlayer使用 */
    public void PLDroidPlayerDemoButtonCLick(View v) {
        startActivity(new Intent(MainActivity.this, PLDroidPlayerActivity.class));
    }

    /** universalVideoView demo */
    public void universalVideoViewDemoButtonClick(View v) {
        startActivity(new Intent(MainActivity.this, UniversalVideoViewActivity.class));
    }

    /**
     * exoPlayer使用demo
     *
     * @param view
     */
    public void exoPlayerDemoButtonClick(View view) {
        startActivity(new Intent(this, ExoPlayerActivity.class));
    }

}
