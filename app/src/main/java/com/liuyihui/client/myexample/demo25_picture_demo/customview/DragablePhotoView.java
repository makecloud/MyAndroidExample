package com.liuyihui.client.myexample.demo25_picture_demo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.liuyihui.photoview.OnSingleFlingListener;
import com.liuyihui.photoview.PhotoView;

/**
 * 基于com.github.chrisbanes.photoview的可拖拽图片
 */
public class DragablePhotoView extends PhotoView {
    private final String TAG = getClass().getSimpleName();

    public DragablePhotoView(Context context, AttributeSet attr) {
        super(context, attr);

        setOnSingleFlingListener(new OnSingleFlingListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                Log.d(TAG, "onFling: ");
                return false;
            }
        });
    }
}
