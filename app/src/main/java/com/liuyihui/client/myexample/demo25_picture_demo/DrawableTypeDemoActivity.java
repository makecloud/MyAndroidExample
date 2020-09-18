package com.liuyihui.client.myexample.demo25_picture_demo;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.R;

public class DrawableTypeDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_type_demo);
    }

    public void transitionDrawableTest(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
//        transitionDrawable.startTransition(500);
        transitionDrawable.reverseTransition(500);
    }
}
