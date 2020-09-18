package com.liuyihui.client.myexample.demo7_custom_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.liuyihui.client.myexample.R;

/**
 * 自定义frameLayout，测试金吉鸟遥控器选台popupWindow不显示
 */
public class CustomFrameLayout extends FrameLayout {
    private final String TAG = "CustomFrameLayout";

    public CustomFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFrameLayout(@NonNull Context context,
                             @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.custom_frame_layout_child_layout, this, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(800, MeasureSpec.getMode(heightMeasureSpec)));

        int count = getChildCount();
        int maxChildHeight = 0;
        int maxChildWidth = 0;

        // Find rightmost and bottom-most child
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childRight;
                int childBottom;

                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();

                childRight = child.getMeasuredWidth();
                childBottom = child.getMeasuredHeight();

                maxChildWidth = Math.max(maxChildWidth, childRight);
                maxChildHeight = Math.max(maxChildHeight, childBottom);
            }
        }

        Log.d(TAG, "onMeasure: maxWidth=" + maxChildWidth);
        Log.d(TAG, "onMeasure: maxHeight=" + maxChildHeight);


        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure: getMeasuredWidth()=" + measuredWidth);
        Log.d(TAG, "onMeasure: getMeasuredHeight()=" + measuredHeight);


        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        Log.d(TAG, "onMeasure: getSuggestedMinimumWidth()=" + suggestedMinimumWidth);
        Log.d(TAG, "onMeasure: getSuggestedMinimumHeight()=" + suggestedMinimumHeight);


        setMeasuredDimension(maxChildWidth, maxChildHeight);

    }
}
