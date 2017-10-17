package com.liuyihui.playvideodemo;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.liuyihui.mylibrary.activity.MySuperActivity;

import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+TextureView播放视频
 */
public class Main3Activity extends MySuperActivity {

    private TextureView textureView;
    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("MediaPlayer+TextureView播放视频");

        textureView = (TextureView) findViewById(R.id.textureView);
        imageView = (ImageView) findViewById(R.id.imageView);

        init();

    }

    public void init() {
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
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

    public void playVideo() {
        mediaPlayer = new MediaPlayer();
        String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String path = sdcardDir + "/DCIM/YiCarCamera/2016.MP4";
//        String path = sdcardDir + "/360/30a58ee0d1b3be9313367ef8033f5483.mp4";
//        String path = sdcardDir + "/360/C06FABBCF84118FD003D0D1269762C0E";
        String path = sdcardDir + "/oohlink/player/.screen/C06FABBCF84118FD003D0D1269762C0E";
//        String path = sdcardDir + "/oohlink/player/.screen/E51A96E2BCBEAA3873EA8CA2CCFB8257";

        Log.e(TAG, path);
        File file = new File(path);
        Log.e(TAG, String.valueOf(file.exists()));

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setSurface(surface);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    imageView.setVisibility(View.GONE);
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
