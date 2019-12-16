package com.liuyihui.client.myexample.example25_picture_demo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.github.chrisbanes.photoview.PhotoView;

public class DragablePhotoView extends PhotoView {

    private final String TAG = getClass().getSimpleName();

    //按下时的view布局位置
    private int actionDownViewLeft;
    private int actionDownViewRight;
    private int actionDownViewTop;
    private int actionDownViewBottom;

    //按下时的xy
    private float actionDownRawX = 0;
    private float actionDownRawY = 0;

    //移动量xy
    float moveDx;
    float moveDy;

    //实时移动中的布局位置
    int resultLeft = 0;
    int resultTop = 0;
    int resultRight = 0;
    int resultBottom = 0;

    //父布局宽高
    int parentHeight;
    int parentWidth;

    //单点，多点标志。0：未确定 1：单点 2：多点
    private int multiTouchFlag = 0;

    public DragablePhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() < 2) {
            asSingleEventHandle(event);
            return true;
        }
        return super.onTouchEvent(event);
    }


    public void asSingleEventHandle(MotionEvent event) {
        switch (event.getAction()) {
            //单点按下
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "asSingleEventHandle: ACTION_DOWN");
                // 按下时记录以下值：
                actionDownViewLeft = getLeft();
                actionDownViewRight = getRight();
                actionDownViewTop = getTop();
                actionDownViewBottom = getBottom();

                actionDownRawX = event.getRawX();
                actionDownRawY = event.getRawY();

                moveDx = 0;
                moveDy = 0;
                break;
            //单点移动
            case MotionEvent.ACTION_MOVE:
                //一旦单点移动,设置单点标志，屏蔽多点事件
                multiTouchFlag = 1;

                //移动增量xy
                moveDx = event.getRawX() - actionDownRawX;
                moveDy = event.getRawY() - actionDownRawY;

                Log.d(TAG, "asSingleEventHandle: moveD:" + moveDx + "," + moveDy);

                //计算出 移动结果 布局参数
                resultLeft = actionDownViewLeft + (int) moveDx;
                resultTop = actionDownViewTop + (int) moveDy;
                resultRight = actionDownViewRight + (int) moveDx;
                resultBottom = actionDownViewBottom + (int) moveDy;

                //实时改变布局，产生移动效果
                layout(resultLeft, resultTop, resultRight, resultBottom);

                if (moveListener != null) {
                    moveListener.onMove((int) moveDx, (int) moveDy);
                }
                Log.i(TAG,
                      "asSingleEventHandle:layout to:" + resultLeft + "," + resultRight + "," + resultTop + "," + resultBottom);
                break;
            //单点触摸，抬起
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "asSingleEventHandle: ACTION_UP");
                //重制标志
                multiTouchFlag = 0;
                //回调
                if (touchUpListerner != null) {
                    touchUpListerner.onTouchUp((int) moveDx, (int) moveDy);
                }
                // 归位
                layout(actionDownViewLeft,
                       actionDownViewTop,
                       actionDownViewRight,
                       actionDownViewBottom);

                break;
        }

    }

    public interface MoveListener {
        void onMove(int dx, int dy);
    }

    private MoveListener moveListener;

    public void setMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    public interface TouchUpListerner {
        void onTouchUp(int moveDx, int moveDy);
    }

    private TouchUpListerner touchUpListerner;

    public void setTouchUpListerner(TouchUpListerner touchUpListerner) {
        this.touchUpListerner = touchUpListerner;
    }
}
