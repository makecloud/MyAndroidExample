package com.liuyihui.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * view动画
 */
public class MainActivity extends AppCompatActivity {
    private Button animateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animateView = findViewById(R.id.animation_target);
    }


    /**
     * 属性动画。
     * <p>
     * 宽高，left top，alpha值，等变化
     * <p>
     * 其他的，得自己去找view有哪些属性可改变，形成动画
     *
     * @param view
     */
    public void animate1(View view) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                                                                      R.animator.property_animator);
        set.setTarget(animateView);
        set.start();
    }

    public void animate2(View view) {

    }

    public void animate3(View view) {

    }

    public void animate4(View view) {

    }

}
