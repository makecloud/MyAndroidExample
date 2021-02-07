package com.liuyihui.playvideodemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放视频
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE,
                                                             Manifest.permission.ACCESS_NETWORK_STATE,
                                                             Manifest.permission.INTERNET,
                                                             Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private VideoView myVideoView;


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

    /** 调用系统自带播放器 播放视频 */
    public void playVideoDemo4CLick(View v) {
        startActivity(new Intent(MainActivity.this, VideoViewActivity.class));
    }

    /** mediaPlayer + surfaceView播放视频 */
    public void playVideoDemo2CLick(View v) {
        startActivity(new Intent(MainActivity.this, DoubleSurfaceViewPlayActivity.class));
    }

    /** mediaplayer + textureView播放视频 */
    public void playVideoDemo3CLick(View v) {
        startActivity(new Intent(MainActivity.this, TextureViewPlayActivity.class));
    }

    /** IjkPlayer使用 */
    public void playVideoDemo6Lick(View v) {
        startActivity(new Intent(MainActivity.this, IjkPLayerActivity.class));
    }

    /** PLDroidPlayer使用 */
    public void playVideoDemo7Lick(View v) {
        startActivity(new Intent(MainActivity.this, PLDroidPlayerActivity.class));
    }

    /** PLDroidPlayer使用 */
    public void playVideoDemo8Lick(View v) {
        startActivity(new Intent(MainActivity.this, UniversalVideoViewActivity.class));
    }

    public void exoPlayerDemoClick(View view) {
        startActivity(new Intent(this, ExoPlayerActivity.class));
    }

    public void switchVideoViewDemoClick(View view) {
        startActivity(new Intent(this, TestSwitchVideoViewActivity.class));
    }


    public void requestPermission(View view) {
        List<String> p = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this,
                                                  permission) != PackageManager.PERMISSION_GRANTED) {
                p.add(permission);
            }
        }
        if (p.size() > 0) {
            requestPermissions(p.toArray(new String[p.size()]), 0);
        }
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            //PERMISSION_GRANTED 授予
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (hasAllPermissionsGranted(grantResults)) {
                //有权限
                Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();

            } else {
                // 没有获取权限
                Toast.makeText(this, "no get permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
