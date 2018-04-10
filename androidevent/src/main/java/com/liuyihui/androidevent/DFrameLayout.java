package com.liuyihui.androidevent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by liuyi on 2018/3/2.
 */

public class DFrameLayout extends FrameLayout {
    public DFrameLayout(@NonNull Context context) {
        super(context);
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
