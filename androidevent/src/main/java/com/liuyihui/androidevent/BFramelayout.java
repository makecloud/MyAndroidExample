package com.liuyihui.androidevent;

import android.app.Activity;
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


    public BFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "BFramelayout: " + (context instanceof Activity));
    }

    /**
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        StringBuilder sb = new StringBuilder();
        sb.append("dispatchTouchEvent called。EventAction：" + ev.getAction());
        sb.append(" dispatchTouchEvent: return super");
        Log.d(TAG, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("onInterceptTouchEvent called。EventAction：" + ev.getAction());
        sb.append(" onInterceptTouchEvent: return super");
        Log.d(TAG, sb.toString());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("onTouchEvent called。EventAction：" + event.getAction());
        sb.append(" onTouchEvent: return super");
        Log.d(TAG, sb.toString());

        return super.onTouchEvent(event);
        //        return true;
    }
}
