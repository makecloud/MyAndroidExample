package com.liuyihui.playvideodemo.oohlinkplayerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * screen布局 容器布局 容纳分屏层view LayerView
 */
public class ScreenLayout extends ViewGroup {

    public ScreenLayout(Context context) {
        this(context, null);
    }

    public ScreenLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Find rightmost and bottom-most child
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childRight;
                int childBottom;

                ScreenLayout.LayoutParams lp = (ScreenLayout.LayoutParams) child.getLayoutParams();

                childRight = lp.x + child.getMeasuredWidth();
                childBottom = lp.y + child.getMeasuredHeight();

                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }

        // Check against minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                             resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ScreenLayout.LayoutParams lp = (ScreenLayout.LayoutParams) child.getLayoutParams();
            child.layout(lp.x,
                         lp.y,
                         lp.x + child.getMeasuredWidth(),
                         lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof ScreenLayout.LayoutParams;
    }

    @Override
    protected ScreenLayout.LayoutParams generateDefaultLayoutParams() {
        return new ScreenLayout.LayoutParams(ScreenLayout.LayoutParams.WRAP_CONTENT,
                                             ScreenLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ScreenLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ScreenLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override
    protected ScreenLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ScreenLayout.LayoutParams(p);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        private int x;
        private int y;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
