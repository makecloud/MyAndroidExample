package com.oohlink.wifiprobe.playaudiodemo;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /** 播放控件 */
    private MediaPlayer mediaPlayer;
    /** 类似mediaplayer的音频控件 */
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //使用soundPool只能播短声音?
//        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        soundPool.load(this, R.raw.moto, 1);
//        soundPool.play(1, 1, 1, 0, 0, 1);
//        soundPool.play(AudioManager.ADJUST_MUTE,1,1,0,0,1);

        //使用mediaPlayer播放声音
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/" + "TheSprings.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(MainActivity.this, "Error whern Playing Audio !", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mediaPlayer.start();


    }
}
