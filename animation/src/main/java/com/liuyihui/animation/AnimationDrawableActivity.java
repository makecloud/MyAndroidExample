package com.liuyihui.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * AnimationDrawable demo
 * <p>
 * 一个动画drawable作为imageView的背景
 */
public class AnimationDrawableActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);

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
