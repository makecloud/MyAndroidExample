package com.liuyihui.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 启动其他activity
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void viewAnimatorWidgetDemo(View view) {
        startActivity(new Intent(this, ViewAnimatorActivity.class));
    }

    public void AnimationDrawableDemo(View view) {
        startActivity(new Intent(this, AnimationDrawableActivity.class));
    }


    public void LayoutChangeAnimateDemo(View view) {
        startActivity(new Intent(this, LayoutChangeAnimateActivity.class));
    }

    public void stateListAnimatorDemo(View view) {
        startActivity(new Intent(this, StateListAnimatorDemoActivity.class));
    }


}
