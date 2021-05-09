package com.liuyihui.playvideodemo.useTextureView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.R;
import com.liuyihui.playvideodemo.RequestPermissionActivity;
import com.liuyihui.playvideodemo.customerView.CustomFromTextureView;

import java.io.File;

/**
 * 使用自定义TextureView的demo
 */
public class CustomTextureViewPlayActivity extends RequestPermissionActivity {
    private final String TAG = getClass().getSimpleName();
    private CustomFromTextureView customFromTextureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_textureview);
        getSupportActionBar().setTitle("使用自定义TextureView播放demo");

        requestPermission(null);

        customFromTextureView = findViewById(R.id.textureView);

        playVideo();
    }

    public void playVideo() {

        //视频源文件路径
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        //        String path = sdcardDir + "/DCIM/qqqq";
        //        String path = Environment.getExternalStorageDirectory() + "/Android/data"
        //                + "/com" + ".oohlink.smartbillborad/files/mat/video";
        String path = sdcardDir + "/oohlink/player/.screen/7373A6508D9791C53DFE3E0F2B272DF7";

        File file = new File(path);
        Log.e(TAG, String.valueOf(file.exists()));

        //播放
        customFromTextureView.setVideoPath(path);

    }

    public void startPlay(View view) {
        playVideo();
    }
}
