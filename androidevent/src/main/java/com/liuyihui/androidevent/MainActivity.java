package com.liuyihui.androidevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    FrameLayout aframeLayout;
    FrameLayout bframeLayout;
    FrameLayout cFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aframeLayout = findViewById(R.id.a_framelayout);
        bframeLayout = findViewById(R.id.b_framelayout);
        cFrameLayout = findViewById(R.id.c_framelayout);


        aframeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
    /*
    首先弄清，android里的view怎么分层的，谁是最上层最先收到touch event，谁是最下层最后收到touch event ！
    结果：按布局包含原理来，外层布局包含内层布局view，外层先收到event
     */


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //表明该事件将会被分发。此时当前ViewGroup的onIntercepterTouchEvent方法会捕获该事件，判断需不需要进行事件的拦截
        StringBuilder sb = new StringBuilder();
        sb.append("dispatchTouchEvent called。EventAction：" + ev.getAction());
        sb.append(" dispatchTouchEvent: return super");
        Log.d(TAG, sb.toString());

        return super.dispatchTouchEvent(ev);

        //表明该事件已经被当前view/activity的dispatchTouchEvent给消费掉。之后系统不再传递此事件，事件到此消失
        //Log.d(TAG, "dispatchTouchEvent: return true");
        //return true;

        //返回false事件不再被分发给下级即子view，被activity拦截。但是，事件会继续以冒泡的方式被传递给外层的view/activity的onTouchEvent方法进行处理
        //Log.d(TAG, "dispatchTouchEvent: return false;");
        //return false;
    }

    /**
     * 返回true false super 分别的含义，区别
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("onTouchEvent called");
        sb.append(" onTouchEvent: return super");
        Log.d(TAG, sb.toString());
        return super.onTouchEvent(event);
        //
        //return true;
        //
        //return false;
    }
}
