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


    /**
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, " ");
        Log.i(TAG, "\n\rdispatchTouchEvent called。EventAction：" + ev.getAction());

        //表明该事件将会被分发。此时当前ViewGroup的onIntercepterTouchEvent方法会捕获该事件，判断需不需要进行事件的拦截
        Log.d(TAG, "dispatchTouchEvent: return super");
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
        Log.d(TAG, "onTouchEvent called");
        Log.d(TAG, "onTouchEvent: return super");
        return super.onTouchEvent(event);
        //
        //return true;
        //
        //return false;
    }
}
