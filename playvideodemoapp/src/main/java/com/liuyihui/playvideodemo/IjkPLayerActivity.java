package com.liuyihui.playvideodemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;

import com.liuyihui.playvideodemo.customerView.IjkVideoView;
import com.liuyihui.playvideodemo.customerView.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPLayerActivity extends AppCompatActivity {
    private final static String TAG = "IjkPLayerActivity";

    public IjkMediaPlayer videoView;
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

        ijkVideoView.setListener(new VideoPlayerListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            }

            @Override
            public void onCompletion(IMediaPlayer mp) {
            }

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
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
            }

            @Override
            public void onSeekComplete(IMediaPlayer mp) {

            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                //在此可以获取到视频的宽和高
            }
        });

        //设置url
        //String videoUrl = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";//test line
//        String videoUrl = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";//test line
//        String videoUrl = "http://test.yungeshidai.com/material/eec76c455d081e4498c0ad7a55fb67e1.mp4";//test line
        String videoUrl = Environment.getExternalStorageDirectory()
                                     .getAbsolutePath() + "/DCIM/qqqq.mp4";
        Toast.makeText(this, videoUrl, Toast.LENGTH_SHORT)
             .show();
        ijkVideoView.setVideoPath(videoUrl);


    }


    @Override
    protected void onStop() {
        super.onStop();
        ijkVideoView.release();
        IjkMediaPlayer.native_profileEnd();
    }
}
