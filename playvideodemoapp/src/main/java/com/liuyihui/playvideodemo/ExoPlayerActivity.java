package com.liuyihui.playvideodemo;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
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

    private Button changeSrcButton;
    private SurfaceView surfaceView;
    private SimpleExoPlayer player1;
    private SimpleExoPlayer player2;
    private PlayerView exoPlayerView;
    private ProgressBar progressBar;
    private boolean first = true;
    private DataSource.Factory dataSourceFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        changeSrcButton = findViewById(R.id.button3);
        surfaceView = findViewById(R.id.sufaceView);
        exoPlayerView = findViewById(R.id.exoPlayerView);
        progressBar = findViewById(R.id.progressBar);

        player1 = new SimpleExoPlayer.Builder(this).build();
        player2 = new SimpleExoPlayer.Builder(this).build();
        //        exoPlayerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(this,
                                                         Util.getUserAgent(this,
                                                                           "yourApplicationName"));

        Player.EventListener eventListener = new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline,
                                          @Nullable Object manifest,
                                          int reason) {
                Log.d(TAG, "onTimelineChanged: ");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups,
                                        TrackSelectionArray trackSelections) {
                Log.d(TAG, "onTracksChanged: ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d(TAG, "onLoadingChanged: " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //                Player.STATE_READY;//3, 显示第一帧可以播放了应该是
                //                Player.STATE_BUFFERING;//2
                //                Player.STATE_IDLE;//1
                //                Player.STATE_ENDED;//4
                Log.d(TAG, "onPlayerStateChanged: " + playbackState);
                if (Player.STATE_BUFFERING == playbackState) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.d(TAG, "onRepeatModeChanged: ");
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                Log.d(TAG, "onShuffleModeEnabledChanged: ");
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.d(TAG, "onPlayerError: ");
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                Log.d(TAG, "onPositionDiscontinuity: ");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.d(TAG, "onPlaybackParametersChanged: ");
            }

            @Override
            public void onSeekProcessed() {
                Log.d(TAG, "onSeekProcessed: ");
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Log.d(TAG, "onIsPlayingChanged: " + isPlaying);
            }
        };

        player1.addListener(eventListener);
        player2.addListener(eventListener);

    }


    public void prepareVideo1(View view) {
        player1 = new SimpleExoPlayer.Builder(this).build();
        //        String path = getExternalFilesDir(null).getAbsolutePath() +
        //                "/8B9357192E4DBF750B99FD3C4C5CA1CC.mp4";
        String path = "/sdcard/oohlink/player/.screen/0A638DE2475566D0691CECD3F00B19D3";
        Uri videoUri = Uri.fromFile(new File(path));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                videoUri);
        player1.setRepeatMode(Player.REPEAT_MODE_OFF);
        // Prepare the player with the source.
        player1.prepare(videoSource);
    }

    public void prepareVideo2(View view) {
        player2 = new SimpleExoPlayer.Builder(this).build();
        //        String path = getExternalFilesDir(null).getAbsolutePath() + File.separator +
        //                "91AB45CDBFCADD696B37E49FD51F48C4.mp4";
        String path = "/sdcard/oohlink/player/.screen/549A2C1EBCC166B1CD6104B4BC0609A9";
        Uri videoUri = Uri.fromFile(new File(path));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                videoUri);
        // Prepare the player with the source.
        player2.setRepeatMode(Player.REPEAT_MODE_OFF);
        player2.prepare(videoSource);
    }

    public void exoPlayer1Start(View view) {
        player1.setPlayWhenReady(true);
    }

    public void exoPlayer2Start(View view) {
        player2.setPlayWhenReady(true);
    }

    //测试是否可以动态更改exoPlayer的view
    public void ViewSetexoPlayer1(View view) {
        exoPlayerView.setPlayer(player1);
        player2.release();
        exoPlayerView.setVisibility(View.VISIBLE);

    }

    //测试是否可以动态更改exoPlayer的view
    public void ViewSetexoPlayer2(View view) {
        exoPlayerView.setPlayer(player2);
        player1.release();
        exoPlayerView.setVisibility(View.VISIBLE);

    }

    public void player1stop(View view) {
        player1.stop(false);

    }

    public void player2stop(View view) {
        player2.stop();

    }


    //测试播放中调用prepare准备其他视频，会怎样?
    //结果之前的视频停止，并显示下一个视频的第一帧
    public void testChangeVideo1SrcWhenPlaying(View view) {
        player1.setPlayWhenReady(false);
        String path = getExternalFilesDir(null).getAbsolutePath() + File.separator +
                "91AB45CDBFCADD696B37E49FD51F48C4.mp4";
        Uri videoUri = Uri.fromFile(new File(path));

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                videoUri);
        //player.stop(false);
        // Prepare the player with the source.
        player1.prepare(videoSource);
        player1.addListener(new Player.EventListener() {});

    }

    //测试播放过程中改变另一个player
    public void testChangePlayerWhenPlaying() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player1.stop();
        player1.release();
        player2.stop();
        player2.release();
    }
}
