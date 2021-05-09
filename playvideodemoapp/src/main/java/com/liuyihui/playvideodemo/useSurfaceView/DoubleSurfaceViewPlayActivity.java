package com.liuyihui.playvideodemo.useSurfaceView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.liuyihui.playvideodemo.R;
import com.liuyihui.playvideodemo.RequestPermissionActivity;

import java.io.File;
import java.io.IOException;

/**
 * MediaPlayer+SurfaceView播放视频
 */
public class DoubleSurfaceViewPlayActivity extends RequestPermissionActivity {
    private final String TAG = getClass().getSimpleName();

    private SurfaceView surfaceView;
    private FrameLayout container;
    private ProgressBar progressBar;
    //private SurfaceHolder holder;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_surfaceview);
        getSupportActionBar().setTitle("MediaPlayer+SurfaceView播放视频");
        //surfaceView = findViewById(R.id.sufaceView);
        container = findViewById(R.id.sufaceView);
        progressBar = findViewById(R.id.progressBar);
        mediaPlayer1 = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();

        //init();
        requestPermission(null);
    }

    public void init() {
        /*holder = surfaceView.getHolder();



        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceCreated: ");
                //playVideo();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceDestroyed: ");
                mediaPlayer1.stop();
                mediaPlayer1.release();
                mediaPlayer2.stop();
                mediaPlayer2.release();
            }
        });*/
    }


    public void playVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //视频源文件路径
                String path = "/sdcard/oohlink/player/.screen/8722BCED441D6A712F323CE39D456AE0";
                //播放
                try {
                    mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer1.start();
                        }
                    });

                    mediaPlayer1.setDataSource(path);
                    mediaPlayer1.setDisplay(surfaceView.getHolder());
                    mediaPlayer1.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void prepareVideo1(View view) {
        long s1 = System.currentTimeMillis();
        //String path = "/sdcard/DCIM/8722BCED441D6A712F323CE39D456AE0";
        String path = getExternalFilesDir(null) + "/8722BCED441D6A712F323CE39D456AE0";

        SurfaceView surfaceView = new SurfaceView(this);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "video1 surfaceCreated: ");
                try {

                    mediaPlayer1.reset();
                    mediaPlayer1.setDataSource(path);
                    mediaPlayer1.setDisplay(holder);
                    mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.d(TAG, "video1 onPrepared: ");
                            Log.d(TAG, "video1 onPrepared: " + (System.currentTimeMillis() - s1));
                            mp.start();
                        }
                    });
                    mediaPlayer1.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            Log.d(TAG, "video1 onInfo: " + (System.currentTimeMillis() - s1));
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                                //移除上面一层View
                                if (container.getChildCount() > 1) {
                                    container.removeViewAt(1);
                                }
                            }
                            return false;
                        }
                    });
                    mediaPlayer1.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                            Log.d(TAG, "video1 onBufferingUpdate: " + percent);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    mediaPlayer1.prepareAsync();
                } catch (Exception e) {
                    Log.e(TAG, "video1 surfaceCreated: ", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "video1 surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "video1 surfaceDestroyed: ");
            }
        });
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(surfaceView, 0, lp);
        Log.d(TAG, "prepareVideo1: add a surfaceView");
    }


    public void Player1Start(View view) {
        mediaPlayer1.start();
    }

    public void player1Stop(View view) {
        mediaPlayer1.stop();
    }

    public void Player1SetView(View view) {
        mediaPlayer1.setDisplay(surfaceView.getHolder());
    }

    public void prepareVideo2(View view) {
        //String path = "/sdcard/oohlink/player/.screen/8722BCED441D6A712F323CE39D456AE0";
        String path = getExternalFilesDir(null) + "/CB07A1E2B42522C0580958B4757CED23";

        SurfaceView surfaceView = new SurfaceView(this);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "video2 surfaceCreated: ");
                try {

                    mediaPlayer2.reset();
                    mediaPlayer2.setDataSource(path);
                    mediaPlayer2.setDisplay(holder);
                    mediaPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.d(TAG, "video2 onPrepared: ");
                            mp.start();
                        }
                    });
                    mediaPlayer2.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                                //移除上面一层View
                                if (container.getChildCount() > 1) {
                                    container.removeViewAt(1);
                                }
                            }
                            return false;
                        }
                    });
                    mediaPlayer2.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                            Log.d(TAG, "video2 onBufferingUpdate: " + percent);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    mediaPlayer2.prepareAsync();
                } catch (Exception e) {
                    Log.e(TAG, "video2 surfaceCreated: ", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "video2 surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "video2 surfaceDestroyed: ");
            }
        });
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(surfaceView, 0, lp);
        Log.d(TAG, "prepareVideo2: add a surfaceView");

    }

    public void Player2Start(View view) {

    }

    public void player2Stop(View view) {
        mediaPlayer2.stop();
    }

    public void Player2SetView(View view) {
        mediaPlayer2.setDisplay(surfaceView.getHolder());
    }
}
