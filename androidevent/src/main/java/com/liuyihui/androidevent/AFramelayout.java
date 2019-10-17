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
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, " ");
        Log.i(TAG, "\ndispatchTouchEvent called。EventAction：" + ev.getAction());

        //表明该事件将会被分发。此时当前ViewGroup的onIntercepterTouchEvent方法会捕获该事件，判断需不需要进行事件的拦截
        Log.d(TAG, "dispatchTouchEvent: return super");
        return super.dispatchTouchEvent(ev);

        //表明该事件已经被当前view/activity的dispatchTouchEvent给消费掉。之后系统不再传递此事件，事件到此消失
//        Log.d(TAG, "dispatchTouchEvent: return true");
//        return true;

        //返回false事件不再被分发给下级即子view，被activity拦截。但是，事件会继续以冒泡的方式被传递给外层的view/activity的onTouchEvent方法进行处理
//        Log.d(TAG, "dispatchTouchEvent: return false;");
//        return false;
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent called。EventAction："+ev.getAction());
        Log.d(TAG, "onInterceptTouchEvent: return super");
        return super.onInterceptTouchEvent(ev);
//        return true;//true代表告诉系统我AFrameLayout拦截了event. 结果导致系统不再拿事件去回调任何AFrameLayout的子view
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent called。EventAction："+event.getAction());
        Log.d(TAG, "onTouchEvent: return super");
        return super.onTouchEvent(event);
    }
}
