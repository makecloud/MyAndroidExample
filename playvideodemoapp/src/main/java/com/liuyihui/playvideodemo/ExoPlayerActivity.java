package com.liuyihui.playvideodemo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

/**
 * google exoPlayer使用demo。注意这是在andoridx兼容包下的api调用方式
 * <p>
 * support包下对应使用旧版本exoPlayer，api有所不同
 */
public class ExoPlayerActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private Button startButton;
    private Button stopButton;
    private Button changeSrcButton;
    private SurfaceView surfaceView;
    private SimpleExoPlayer player;
    private PlayerView exoPlayerView;
    private boolean first = true;
    private DataSource.Factory dataSourceFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        startButton = findViewById(R.id.button1);
        stopButton = findViewById(R.id.button2);
        changeSrcButton = findViewById(R.id.button3);
        surfaceView = findViewById(R.id.sufaceView);
        exoPlayerView = findViewById(R.id.exoPlayerView);

        player = new SimpleExoPlayer.Builder(this).build();
        exoPlayerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"));
    }

    public void prepareVideo() {
        Uri videoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Android/data" + "/com" +
                                                     ".oohlink" + ".smartbillborad/files/mat/video"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }

    public void exoPlayerStop(View view) {
        player.stop();
        //exoPlayerView.setVisibility(View.INVISIBLE);
    }

    public void exoPlayerStart(View view) {
        exoPlayerView.setVisibility(View.VISIBLE);
        prepareVideo();

        if (first) {
            first = false;
            player.setPlayWhenReady(true);
        }
    }

    //测试播放中调用prepare准备其他视频，会怎样?
    //结果之前的视频停止，并显示下一个视频的第一帧
    public void changeSrcWhenPlaying(View view) {
        player.setPlayWhenReady(false);
        Uri videoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Android/data/com" +
                                                     ".oohlink" + ".smartbillborad/files/mat/video2"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}
