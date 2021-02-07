package com.liuyihui.playvideodemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.customerView.IjkVideoView;
import com.liuyihui.playvideodemo.customerView.TotalVideoPlayerListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPLayerActivity extends AppCompatActivity {
    private final static String TAG = "IjkPLayerActivity";

    public MediaController mediaController;
    public IjkVideoView ijkVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_player);
        ijkVideoView = findViewById(R.id.ijkPlayerView);

        //加载ijkplayer 库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            Log.e(TAG, ",", e);
        }


        ijkVideoView.setListener(new TotalVideoPlayerListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            }

            @Override
            public void onCompletion(IMediaPlayer mp) {
            }

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                Log.e(TAG, "onError: what:" + what + ",extra:" + extra);
                return false;
            }

            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public void onPrepared(IMediaPlayer mp) {
                // 视频准备好播放了，但是他不会自动播放，需要手动让他开始。
                mp.start();

                //0到1  0:静音,1:系统设置多大音量就多大音量
                //mp.setVolume(1f, 1f);
            }

            @Override
            public void onSeekComplete(IMediaPlayer mp) {

            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer mp,
                                           int width,
                                           int height,
                                           int sar_num,
                                           int sar_den) {
                //在此可以获取到视频的宽和高
            }
        });

        //设置url
        //String path = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
        String path = "/mnt/internal_sd/oohlink/player/.screen/125C3E76400B7F629665C39C95DA207E";
        //String path = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
        ijkVideoView.setVideoPath(path);


    }

    @Override
    protected void onStop() {
        super.onStop();
        ijkVideoView.release();
        IjkMediaPlayer.native_profileEnd();
    }
}
