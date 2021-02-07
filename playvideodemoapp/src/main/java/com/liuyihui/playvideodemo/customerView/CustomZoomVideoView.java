package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.widget.VideoView;

/**
 * 自定义拉伸的视频view
 */
public class CustomZoomVideoView extends VideoView {
    private int width;
    private int height;

    public CustomZoomVideoView(Context context) {
        super(context);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);

        //以下好像也管用
        /*int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w, h);*/
    }
}
