package com.liuyihui.androidevent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.security.AlgorithmConstraints;

/**
 * Created by liuyi on 2018/3/2.
 */

public class CFrameLayout extends FrameLayout {
    private final String TAG = "CFrameLayout";

    public CFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent called");

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.i(TAG, "move event: " + event.getX() + "," + event.getY());
        }

//        return super.onTouchEvent(event);
        return true;//true代表事件被我CFrameLayout自己消耗掉, 系统不会再拿此event回调其他view的onTouchEvent
//        return false;
    }
}
