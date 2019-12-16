package com.liuyihui.viewdrag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 可自由拖拽view到任何位置的demo
 */
public class FreeDragViewActivity extends AppCompatActivity {
    private FreeMovingContainer freeMovingContainer;
    private MovingView movingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_drag_view);
        freeMovingContainer = findViewById(R.id.container);
    }

    public void callReLayout(View view) {
        freeMovingContainer.requestLayout();
    }
}
