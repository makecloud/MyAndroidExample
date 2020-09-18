package com.liuyihui.client.myexample.demo14_gridview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

public class CustomGridView extends GridView {
    private final String TAG = getClass().getSimpleName();

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,
              "onMeasure: widthMeasureSpec:" + widthMeasureSpec + "," + "heightMeasureSpec:" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onMeasure: " + blockWidth + "," + blockHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onLayout: " + blockWidth + "," + blockHeight);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onViewAdded: " + blockWidth + "," + blockHeight);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onAttachedToWindow: " + blockWidth + "," + blockHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onDraw: " + blockWidth + "," + blockHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        double blockWidth = getWidth();
        double blockHeight = getHeight();
        Log.d(TAG, "onFinishInflate: " + blockWidth + "," + blockHeight);
    }
}
