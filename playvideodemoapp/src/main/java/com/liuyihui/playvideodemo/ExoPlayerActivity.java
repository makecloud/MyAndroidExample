package com.liuyihui.playvideodemo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class ExoPlayerActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private SurfaceView surfaceView;
    private SimpleExoPlayer player;
    private PlayerView exoPlayerView;
    private boolean first=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        surfaceView = findViewById(R.id.sufaceView);
        exoPlayerView = findViewById(R.id.exoPlayerView);

        player = new SimpleExoPlayer.Builder(this).build();
        exoPlayerView.setPlayer(player);

    }

    public void prepareVideo() {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                                                                            Util.getUserAgent(this,
                                                                                              "yourApplicationName"));

        Uri videoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
                                                     "/Android/data" + "/com" + ".oohlink" +
                                                     ".smartbillborad/files/mat/video"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                videoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }

    public void exoPlayerStop(View view) {
        player.stop();
        exoPlayerView.setVisibility(View.INVISIBLE);
    }

    public void exoPlayerStart(View view) {
        exoPlayerView.setVisibility(View.VISIBLE);
        prepareVideo();

        if (first) {
            first = false;
            player.setPlayWhenReady(true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}
