package com.liuyihui.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewAnimator;

public class ViewAnimatorActivity extends AppCompatActivity {

    public ViewAnimator viewAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator);

        viewAnimator = findViewById(R.id.view_animator);
    }

    public void previous(View view) {
        viewAnimator.showPrevious();
    }


    public void next(View view) {
        viewAnimator.showNext();
    }
}
