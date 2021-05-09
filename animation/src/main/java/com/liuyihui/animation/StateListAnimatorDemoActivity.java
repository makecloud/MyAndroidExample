package com.liuyihui.animation;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 使用 StateListAnimator 为视图状态更改添加动画效果
 *
 * 例如：在selector中定义各个状态的动画，然后给按钮设置，按钮就会在切换到不同状态时执行动画
 */
public class StateListAnimatorDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list_animator_demo);
    }
}
