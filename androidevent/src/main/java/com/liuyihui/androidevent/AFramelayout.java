package com.liuyihui.androidevent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by liuyi on 2018/3/2.
 */

public class AFramelayout extends FrameLayout {
    private final String TAG = "AFramelayout";

    public AFramelayout(@NonNull Context context) {
        super(context);
    }

    public AFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent called");
        return super.onInterceptTouchEvent(ev);
//        return true;//true代表告诉系统我AFrameLayout拦截了event. 结果导致系统不再拿事件去回调任何AFrameLayout的子view
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent called");
        return super.onTouchEvent(event);
    }
}
