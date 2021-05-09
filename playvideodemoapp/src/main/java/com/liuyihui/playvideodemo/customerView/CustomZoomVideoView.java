package com.liuyihui.playvideodemo.customerView;

import android.content.Context;
import android.util.Log;
import android.widget.VideoView;

/**
 * 自定义拉伸的视频view
 */
public class CustomZoomVideoView extends VideoView {
    private final String TAG = "CustomZoomVideoView";
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
        //设置固定的宽高
        //setMeasuredDimension(width, height);

        //以下好像也管用
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + w + "," + h);
        setMeasuredDimension(w, h);

        //setMeasuredDimension(800, 800);
    }
}
