package com.liuyihui.viewdrag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by liuyi on 2018/3/2.
 */

public class MovingView extends View {
    private final String TAG = getClass().getSimpleName();

    private int actionDownLeft;
    private int actionDownRight;
    private int actionDownTop;
    private int actionDownBottom;

    private float actionDownRawX = 0;
    private float actionDownRawY = 0;

    float moveDx;
    float moveDy;
    int resultLeft = 0;
    int resultTop = 0;
    int resultRight = 0;
    int resultBottom = 0;

    int parentHeight;
    int parentWidth;

    public MovingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        //Log.d(TAG, "onTouchEvent called。EventAction：" + event.getAction());

        parentHeight = ((ViewGroup) getParent()).getHeight();
        parentWidth = ((ViewGroup) getParent()).getWidth();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionDownLeft = getLeft();
            actionDownRight = getRight();
            actionDownTop = getTop();
            actionDownBottom = getBottom();

            actionDownRawX = event.getRawX();
            actionDownRawY = event.getRawY();


        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //            Log.d(TAG, "onTouchEvent: action move event xy: " + event.getX() + ","
            //            + event.getY());
            //            Log.d(TAG,
            //                  "onTouchEvent: action move event rawxy: " + event.getRawX() + ","
            //                  + event.getRawY());

            moveDx = event.getRawX() - actionDownRawX;
            moveDy = event.getRawY() - actionDownRawY;
            Log.d(TAG, "onTouchEvent: moveD:" + moveDx + "," + moveDy);

            resultLeft = actionDownLeft + (int) moveDx;
            resultTop = actionDownTop + (int) moveDy;
            resultRight = actionDownRight + (int) moveDx;
            resultBottom = actionDownBottom + (int) moveDy;

            layout(resultLeft, resultTop, resultRight, resultBottom);
            Log.i(TAG,
                  "onTouchEvent:layout to:" + resultLeft + "," + resultRight + "," + resultTop +
                          "," + resultBottom);
        }

        //抬起归位
        /*if (event.getAction() == MotionEvent.ACTION_UP) {
            layout(actionDownLeft, actionDownTop, actionDownRight, actionDownBottom);
        }*/

        //抬起
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 判断移出边界，恢复
            boolean needRecover = false;
            if (resultLeft < 0) {
                moveDx += -resultLeft;
                resultLeft = 0;
                needRecover = true;
            }
            if (resultTop < 0) {
                moveDy += -resultTop;
                resultTop = 0;
                needRecover = true;
            }
            if (resultRight > parentWidth) {
                moveDx -= resultRight - parentWidth;
                resultRight = parentWidth;
                needRecover = true;
            }
            if (resultBottom > parentHeight) {
                moveDy -= resultBottom - parentHeight;
                resultBottom = parentHeight;
                needRecover = true;
            }
            if (needRecover) {
                Log.i(TAG,
                      "onTouchEvent: relayout:" + resultLeft + "," + resultRight + "," + resultTop + "," + resultBottom);
                layout(resultLeft, resultTop, resultRight, resultBottom);
            }
            //固定layoutParam到此位置,防止父容器重新布局导致的位置恢复
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayoutParams();
            Log.i(TAG, "onTouchEvent: lp:" + lp.topMargin + "," + lp.leftMargin);
            lp.topMargin += (int) moveDy;
            lp.leftMargin += (int) moveDx;
            setLayoutParams(lp);
            Log.i(TAG, "onTouchEvent: lp:" + lp.topMargin + "," + lp.rightMargin);
        }
        return true;//true代表事件被我CFrameLayout自己消耗掉, 系统不会再拿此event回调其他view的onTouchEvent
    }

    //执行到这时，参数为移动之前的位置。
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
