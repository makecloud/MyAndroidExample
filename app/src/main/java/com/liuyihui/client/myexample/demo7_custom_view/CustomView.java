package com.liuyihui.client.myexample.demo7_custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义不规则多边形view
 * <p>
 * Created by liuyh on 2017/1/5.
 */
public class CustomView extends View {
    private final String TAG = "CustomView";

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
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureSpecWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureSpecHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: measureSpecWidth=" + measureSpecWidth);
        Log.d(TAG, "onMeasure: measureSpecHeight=" + measureSpecHeight);


        Log.d(TAG, "onMeasure: getMeasuredWidth()=" + getMeasuredWidth());
        Log.d(TAG, "onMeasure: getMeasuredHeight()=" + getMeasuredHeight());


        int blockWidth = getWidth();
        int blockHeight = getHeight();
        Log.d(TAG, "onMeasure: getWidth()=" + blockWidth);
        Log.d(TAG, "onMeasure: getHeight()=" + blockHeight);

        setMeasuredDimension(200, 200);
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

    @Override
    protected void onFocusChanged(boolean gainFocus,
                                  int direction,
                                  @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.d(TAG, "onFocusChanged: ");
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Log.d(TAG, "onDrawForeground: ");
    }
}
