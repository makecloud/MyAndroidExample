package com.liuyihui.client.myexample.demo21_framelayout_hierarchy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.liuyihui.client.myexample.R;

/**
 * 在一个frameLayout中，将被遮挡的控件放到上层显示
 */
public class Example21Activity extends AppCompatActivity {
    private final String TAG = "Example21Activity";
    FrameLayout containerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example21);

        containerFrameLayout = (FrameLayout) findViewById(R.id.containerFramelayout);
    }

    /**
     * 添加view进去
     *
     * @param view
     */
    public void addViewToFrameLayout(View view) {

        Button button1 = new Button(this);
        button1.setText(System.currentTimeMillis() + "");
        button1.setTag(1);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);


        containerFrameLayout.addView(button1);


        Button button2 = new Button(this);
        button2.setText(System.currentTimeMillis() + "");
        button2.setTag(2);
        containerFrameLayout.addView(button2);

        Button button3 = new Button(this);
        button3.setText(System.currentTimeMillis() + "");
        button3.setTag(3);
        containerFrameLayout.addView(button3);

        Button button4 = new Button(this);
        button4.setText(System.currentTimeMillis() + "");
        button4.setTag(4);
        containerFrameLayout.addView(button4,2);


        for (int i = 0; i < containerFrameLayout.getChildCount(); i++) {
            Log.d(TAG, "addViewToFrameLayout: " + containerFrameLayout.getChildAt(i).getTag());
        }

    }


    public void bringChildViewToFront(View view) {

    }

}
