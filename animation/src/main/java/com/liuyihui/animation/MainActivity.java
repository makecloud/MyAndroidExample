package com.liuyihui.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 安卓内的动画
 */
public class MainActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView animationTestImageView = findViewById(R.id.iv_animation_test);
        /*
         * 这种动画, 属于FrameAnimation,只能单纯按时间切换帧.
         */
        animationDrawable = (AnimationDrawable) animationTestImageView.getBackground();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * 不能在onCreate中启动动画, 一般在onWindowFocusChanged中
             */
            animationDrawable.start();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


}
