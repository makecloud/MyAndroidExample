package com.liuyihui.client.myexample.example7_custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 自定义布局。区别于自定义viewgroup，自定义布局是继承sdk自带的XxxLayout
 */
public class CustomLayout extends FrameLayout {
    private final String TAG = getClass().getSimpleName();

    public CustomLayout(@NonNull Context context) {
        super(context);
    }

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG,
              "onMeasure: modeWidth:" + modeWidth + ",modeHeight:" + modeHeight + ",sizeWidth:" + sizeWidth + ",sizeHeight:" + sizeHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "====onMeasure: ");
        Log.d(TAG,
              "onMeasure: modeWidth:" + modeWidth + ",modeHeight:" + modeHeight + ",sizeWidth:" + sizeWidth + ",sizeHeight:" + sizeHeight);

        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "====onLayout: ");
        Log.d(TAG,
              "onLayout: changed:" + changed + ",left:" + left + ",right:" + right + ",top:" + top + "," + "bottom:" + bottom);
        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());

        //这里改变参数后，实际运行结果会相应改变
        setBottom(bottom - 100);
        setLeft(left + 500);

    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        Log.d(TAG, "====onViewAdded: ");
        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "====onAttachedToWindow: ");
        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "====onDraw: ");
        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "====onFinishInflate: ");
        Log.d(TAG, "X:" + getX());
        Log.d(TAG, "Y:" + getY());
        Log.d(TAG, "Top:" + getTop());
        Log.d(TAG, "Bottom:" + getBottom());
        Log.d(TAG, "Left:" + getLeft());
        Log.d(TAG, "Right:" + getRight());
        Log.d(TAG, "Width:" + getWidth());
        Log.d(TAG, "Height:" + getHeight());
    }
}
