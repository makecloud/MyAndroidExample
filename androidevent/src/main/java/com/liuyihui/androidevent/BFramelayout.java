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

public class BFramelayout extends FrameLayout {
    private final String TAG = "BFramelayout";

    public BFramelayout(@NonNull Context context) {
        super(context);
    }

    public BFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, " ");
        Log.i(TAG, "\ndispatchTouchEvent called。EventAction：" + ev.getAction());
        Log.d(TAG, "dispatchTouchEvent: return super");
        return super.dispatchTouchEvent(ev);

//        Log.d(TAG, "dispatchTouchEvent: return true");
//        return true;

//        Log.d(TAG, "dispatchTouchEvent: return false;");
//        return false;
    }
    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent called。EventAction："+ev.getAction());
        Log.d(TAG, "onInterceptTouchEvent: return super");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent called。EventAction："+event.getAction());
        Log.d(TAG, "onTouchEvent: return super");
        return super.onTouchEvent(event);
//        return true;
    }
}
