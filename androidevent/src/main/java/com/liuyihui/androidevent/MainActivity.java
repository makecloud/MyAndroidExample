package com.liuyihui.androidevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

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

    }


    /**
     * 分发事件. 分发?
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "------------------");
        Log.i(TAG, "dispatchTouchEvent");

        //表明该事件将会被分发。此时当前ViewGroup的onIntercepterTouchEvent方法会捕获该事件，判断需不需要进行事件的拦截
        return super.dispatchTouchEvent(ev);
        //表明该事件已经被分发处理。事件会被当前view/activity的dispatchTouchEvent给消费掉。不会再进行传递，事件到此结束
//        return true;
        //表明事件不会被进行分发。事件会以冒泡的方式被传递给上层的view的onTouchEvent方法进行消费掉。
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent called");
        return super.onTouchEvent(event);
    }
}
