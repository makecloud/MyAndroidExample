package com.liuyihui.client.myexample.example1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

/**
 * view的坐标研究
 */
public class ViewCoordinateActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    FrameLayout rootLayout;
    RelativeLayout relativeLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coordinate);
        rootLayout = findViewById(R.id.rootLayout);
        relativeLayout = findViewById(R.id.relativeLayout);
        textView = findViewById(R.id.textview);


    }

    @Override
    protected void onResume() {
        super.onResume();

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    public void show() {
        Log.i(TAG, "onCreate: rootLayout");
        System.out.println("X:" + rootLayout.getX());
        System.out.println("Y:" + rootLayout.getY());
        int[] onScreenXY = new int[2];
        rootLayout.getLocationOnScreen(onScreenXY);
        System.out.println("onScreenX:" + onScreenXY[0]);
        System.out.println("onScreenY:" + onScreenXY[1]);
        System.out.println("Top:" + rootLayout.getTop());
        System.out.println("Bottom:" + rootLayout.getBottom());
        System.out.println("Left:" + rootLayout.getLeft());
        System.out.println("Right:" + rootLayout.getRight());
        System.out.println("Width:" + rootLayout.getWidth());
        System.out.println("Height:" + rootLayout.getHeight());

        Log.i(TAG, "onCreate: relativeLayout");
        System.out.println("X:" + relativeLayout.getX());//看出getX是得到的是在父布局内的X偏移量
        System.out.println("Y:" + relativeLayout.getY());
        relativeLayout.getLocationOnScreen(onScreenXY);
        System.out.println("onScreenX:" + onScreenXY[0]);//onScreenX是相对屏幕边缘的，在屏幕内的X偏移量
        System.out.println("onScreenY:" + onScreenXY[1]);
        System.out.println("Top:" + relativeLayout.getTop());//是本relativeLayout顶边距离父布局顶边的距离
        System.out.println("Bottom:" + relativeLayout.getBottom());//是本relativeLayout底边距离父布局顶边的距离
        System.out.println("Left:" + relativeLayout.getLeft());//是本relativeLayout左边距离父布局左边的距离
        System.out.println("Right:" + relativeLayout.getRight());//是本relativeLayout右边距离父布局左边的距离
        System.out.println("Width:" + relativeLayout.getWidth());
        System.out.println("Height:" + relativeLayout.getHeight());


        Log.i(TAG, "onCreate: textview");
        System.out.println("X:" + textView.getX());
        System.out.println("Y:" + textView.getY());
        textView.getLocationOnScreen(onScreenXY);
        System.out.println("onScreenX:" + onScreenXY[0]);
        System.out.println("onScreenY:" + onScreenXY[1]);
        System.out.println("Top:" + textView.getTop());
        System.out.println("Bottom:" + textView.getBottom());
        System.out.println("Left:" + textView.getLeft());
        System.out.println("Right:" + textView.getRight());
        System.out.println("Width:" + textView.getWidth());
        System.out.println("Height:" + textView.getHeight());
    }
}