package com.liuyihui.client.myexample.example7_custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义不规则多边形view
 * <p>
 * Created by liuyh on 2017/1/5.
 */
public class CustomView extends View {
    private final String TAG = getClass().getSimpleName();

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
