package com.liuyihui.client.myexample.demo25_picture_demo.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * 可拖动的imageView。默认父布局为frame layout。
 * <p>
 * Created by liuyi on 2018/3/2.
 */

public class DragableImageView extends android.support.v7.widget.AppCompatImageView {
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


    //多点触摸，按下时的点坐标
    private MotionEvent.PointerCoords downPointerCoords0 = new MotionEvent.PointerCoords();
    private MotionEvent.PointerCoords downPointerCoords1 = new MotionEvent.PointerCoords();
    //多点触摸，按下时的两点组成的矩形的 left top right bottom
    private int downRectLeft;
    private int downRectTop;
    private int downRectRight;
    private int downRectBottom;

    //多点触摸，滑动过程中的两个点坐标
    private MotionEvent.PointerCoords movingPointerCoords0 = new MotionEvent.PointerCoords();
    private MotionEvent.PointerCoords movingPointerCoords1 = new MotionEvent.PointerCoords();
    //多点触摸，滑动时的两点组成的矩形的 left top right bottom
    private int movingRectLeft;
    private int movingRectTop;
    private int movingRectRight;
    private int movingRectBottom;

    public DragableImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取多点 点数
        int pointerCount = event.getPointerCount();
        Log.d(TAG, "onTouchEvent: pcount:" + pointerCount);
        if (pointerCount > 5) {
            pointerCount = 5;
        }

        //获取父布局宽高
        parentHeight = ((ViewGroup) getParent()).getHeight();
        parentWidth = ((ViewGroup) getParent()).getWidth();

        //根据 单多点标志 选择处理单点or多点
        if (multiTouchFlag == 1) {
            asSingleEventHandle(event);
        } else if (multiTouchFlag == 2) {
            asMultiEventHandle(event);
        } else {
            asSingleEventHandle(event);
            if (multiTouchFlag != 0) {
                //首次判断走单点处理时，防止走到多点
                return true;
            }
            asMultiEventHandle(event);
        }


        return true;//true代表事件被我CFrameLayout自己消耗掉, 系统不会再拿此event回调其他view的onTouchEvent
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

    public void asMultiEventHandle(MotionEvent event) {
        switch (event.getActionMasked()) {
            // 多点按下，第n>1个点按下
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "asMultiEventHandle: ACTION_POINTER_DOWN");
                //一旦多点按下，设置多点标志，屏蔽处理单点
                multiTouchFlag = 2;

                //记录多点按下时的两个点
                event.getPointerCoords(0, downPointerCoords0);
                event.getPointerCoords(1, downPointerCoords1);


                /*
                * 算法1：分上下左右，未完待续
                //给按下的pointer分左右上下：
                if ((downPointerCoords0.x - downPointerCoords1.x) > 0) {
                    //点0 为右，点1 为左
                } else {
                    //点0 为左，点1 为右
                }

                if ((downPointerCoords0.y - downPointerCoords1.y) > 0) {
                    //点0 为上，点1 为下
                } else {
                    //点0 为下，点1 为上

                }

                //分不出左右，分上下
                */

                // 算法2： 算初始的left top right bottom, 然后算移动后的，按各个差值，改变image的left top
                // right bottom
                if (downPointerCoords0.x <= downPointerCoords1.x) {
                    downRectLeft = (int) downPointerCoords0.x;
                    downRectRight = (int) downPointerCoords1.x;
                } else {
                    downRectLeft = (int) downPointerCoords1.x;
                    downRectRight = (int) downPointerCoords0.x;
                }

                if (downPointerCoords0.y <= downPointerCoords1.y) {
                    downRectTop = (int) downPointerCoords0.y;
                    downRectBottom = (int) downPointerCoords1.y;
                } else {
                    downRectTop = (int) downPointerCoords1.y;
                    downRectBottom = (int) downPointerCoords0.y;
                }

                break;
            // 多点移动
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() < 2) {
                    break;
                }
                //多点move事件。
                Log.d(TAG, "asMultiEventHandle: ACTION_MOVE");
                //获取移动中的两个点坐标
                event.getPointerCoords(0, movingPointerCoords0);
                event.getPointerCoords(1, movingPointerCoords1);


                Log.d(TAG,
                      "asMultiEventHandle: " + String.format("%s,%s",
                                                             downPointerCoords0.x,
                                                             downPointerCoords0.y));
                Log.d(TAG,
                      "asMultiEventHandle: " + String.format("%s,%s",
                                                             movingPointerCoords0.x,
                                                             movingPointerCoords0.y));
                Log.d(TAG,
                      "asMultiEventHandle: " + String.format("%s,%s,%s,%s",
                                                             downRectLeft,
                                                             downRectTop,
                                                             downRectRight,
                                                             downRectBottom));

                //根据移动后的两点的rect-按下时的两点的rect，改变image布局实现缩放
                if (movingPointerCoords0.x <= movingPointerCoords1.x) {
                    movingRectLeft = (int) movingPointerCoords0.x;
                    movingRectRight = (int) movingPointerCoords1.x;
                } else {
                    movingRectLeft = (int) movingPointerCoords1.x;
                    movingRectRight = (int) movingPointerCoords0.x;
                }

                if (movingPointerCoords0.y <= movingPointerCoords1.y) {
                    movingRectTop = (int) movingPointerCoords0.y;
                    movingRectBottom = (int) movingPointerCoords1.y;
                } else {
                    movingRectTop = (int) movingPointerCoords1.y;
                    movingRectBottom = (int) movingPointerCoords0.y;
                }


                Log.d(TAG,
                      "asMultiEventHandle: " + String.format("%s,%s,%s,%s",
                                                             movingRectLeft,
                                                             movingRectTop,
                                                             movingRectRight,
                                                             movingRectBottom));

                int dLeft = movingRectLeft - downRectLeft;
                int dTop = movingRectTop - downRectTop;
                int dRight = movingRectRight - downRectRight;
                int dBottom = movingRectBottom - downRectBottom;

                layout(actionDownViewLeft + dLeft,
                       actionDownViewTop + dTop,
                       actionDownViewRight + dRight,
                       actionDownViewBottom + dBottom);


                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "asMultiEventHandle: ACTION_UP");
                //重制标志
                multiTouchFlag = 0;
                break;
        }

    }

    /*public void changeSize(int dLeft, int dRight, int dTop, int dBottom) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
        Log.d(TAG, "changeSize: " + lp.width + "," + lp.height);
        lp.width += dx;
        lp.height += dy;
        setLayoutParams(lp);
    }*/

    //执行到这时，参数为移动之前的位置。
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
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
