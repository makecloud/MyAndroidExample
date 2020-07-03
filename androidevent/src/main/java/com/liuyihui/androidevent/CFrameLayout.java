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

public class CFrameLayout extends FrameLayout {
    private final String TAG = "CFrameLayout";
    private int actionDownLeft;
    private int actionDownRight;
    private int actionDownTop;
    private int actionDownBottom;

    private float actionDownRawX = 0;
    private float actionDownRawY = 0;

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
        sb.append("onInterceptTouchEvent called。EventAction："+ev.getAction());
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



        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionDownLeft = getLeft();
            actionDownRight = getRight();
            actionDownTop = getTop();
            actionDownBottom = getBottom();

            actionDownRawX = event.getRawX();
            actionDownRawY = event.getRawY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d(TAG, "onTouchEvent: action move event xy: " + event.getX() + "," + event.getY());
            Log.d(TAG,
                  "onTouchEvent: action move event rawxy: " + event.getRawX() + "," + event.getRawY());


            int dy = (int) (event.getRawY() - actionDownRawY);
            int dx = (int) (event.getRawX() - actionDownRawX);

            layout(actionDownLeft + dx,
                   actionDownTop + dy,
                   actionDownRight + dx,
                   actionDownBottom + dy);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            //归位可以在抬起事件里做。
            layout(actionDownLeft, actionDownTop, actionDownRight, actionDownBottom);
        }

//        return super.onTouchEvent(event);

        Log.i(TAG, "onTouchEvent: return true;");
        return true;//true代表事件被我CFrameLayout自己消耗掉, 系统不会再拿此event回调其他view的onTouchEvent
//        return false;
    }
}
