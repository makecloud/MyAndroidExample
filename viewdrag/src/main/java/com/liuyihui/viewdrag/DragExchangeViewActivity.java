package com.liuyihui.viewdrag;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 拖动交换视图demo
 *
 * @author liuyihui 2018年4月19日13:18:28
 */
public class DragExchangeViewActivity extends AppCompatActivity {
    private final static String TAG = "DragExchangeViewAc";
    /** 三个容器 */
    private LinearLayout view1_container;
    private LinearLayout view2_container;
    private LinearLayout view3_container;
    /** 三要个拖动view */
    private LinearLayout view1;
    private LinearLayout view2;
    private LinearLayout view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
        //三个容器
        view1_container = findViewById(R.id.view1_container);
        view2_container = findViewById(R.id.view2_container);
        view3_container = findViewById(R.id.view3_container);
        //三个可拖拽view
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        /**
         * 通用touch事件,开始拖拽
         */
        View.OnTouchListener onTouchListener1 = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //定义一个裁剪数据对象.随便定义一个就行,本demo用不到这个clipData.
                ClipData clipData = new ClipData(ClipData.newPlainText("view1", "aaa"));

                //定义一个拖拽阴影
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);

                //开始拖拽. 第三个参数在拖拽过程用来传递数据,这里我传view即被拖动的view自己,
                //后面可在DragEvent中获得这个参数view
                view.startDrag(clipData, myShadow, view, 0);
                return false;
            }
        };

        //所有被拖view设置触摸事件
        view1.setOnTouchListener(onTouchListener1);
        view2.setOnTouchListener(onTouchListener1);
        view3.setOnTouchListener(onTouchListener1);

        /**
         * 整理为一个通用的拖拽回调逻辑.所有容器view设置这个回调对象.
         *
         * 拖拽回调解释: 当一个view被拖拽的时候,系统会回调任何可见的view(包括被拖拽view自己)的OnDragListener,
         * 当然如果view没设置OnDragListener不会被系统回调.
         *
         */
        View.OnDragListener onDragListener1 = new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                Log.i(TAG, "container.onDrag called======");
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED://开始.当有view被拖的时候收到此事件
//                        Log.i(TAG, "开始");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED://进入.当被拖view手指按压的位置进入本view的瞬间收到此事件
//                        Log.i(TAG, "进入");
                        break;
                    case DragEvent.ACTION_DROP://丢入.当被拖view在本view上方手指释放开,收到此事件
//                        Log.i(TAG, "位于");

                        //此时dragEvent里包含要被拖view
                        View draggingView = (View) dragEvent.getLocalState();
                        //当前容器
                        ViewGroup thisContainer = ((ViewGroup) view);
                        //被拖拽view覆盖的view
                        View coveredView = thisContainer.getChildAt(0);
                        //当拖拽的view跟被覆盖的view不同时
                        if (draggingView != coveredView) {
                            ViewGroup draggingViewContainer = (ViewGroup) draggingView.getParent();
                            //两个容器交换子view
                            thisContainer.removeView(coveredView);
                            draggingViewContainer.removeView(draggingView);
                            thisContainer.addView(draggingView);
                            draggingViewContainer.addView(coveredView);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION://位于.当被拖view手指按压的位置在本view上方的时候收到此事件
//                        Log.i(TAG, "丢入");
                        Log.i(TAG, "丢入view_container2");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED://出去.当被拖view手指按压位置从本view拖出去收到此事件
//                        Log.i(TAG, "出去");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED://结束.手指释放拖拽结束收到此事件.
//                        Log.i(TAG, "结束");
                        break;
                    default:
                        break;
                }
                //一定要为true
                return true;
            }
        };

        //container设置拖拽监听, 感知其他view的拖动
        view1_container.setOnDragListener(onDragListener1);
        view2_container.setOnDragListener(onDragListener1);
        view3_container.setOnDragListener(onDragListener1);

    }
}
