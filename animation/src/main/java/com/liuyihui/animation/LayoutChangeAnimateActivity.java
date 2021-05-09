package com.liuyihui.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liuyihui.animation.databinding.ActivityLayoutChangeAnimateBinding;

/**
 * viewGroup子类即布局内view元素变化时的动画
 */
public class LayoutChangeAnimateActivity extends AppCompatActivity {

    ActivityLayoutChangeAnimateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLayoutChangeAnimateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void addButtonIn(View view) {
        Button button = new Button(this);
        button.setText("addedbutton");
        button.setAllCaps(false);
        binding.layoutUseDefaultAnimate.addView(button);
    }

    public void removeAButton(View view) {

        if (binding.layoutUseDefaultAnimate.getChildCount() > 0) {
            binding.layoutUseDefaultAnimate.removeViewAt(binding.layoutUseDefaultAnimate.getChildCount() - 1);
        }
    }
}
