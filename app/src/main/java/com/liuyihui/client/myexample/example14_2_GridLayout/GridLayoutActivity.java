package com.liuyihui.client.myexample.example14_2_GridLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;

import com.liuyihui.client.myexample.R;

/**
 * gridlayout使用
 */
public class GridLayoutActivity extends AppCompatActivity {
    private final String TAG = "GridLayoutActivity";
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);

        gridLayout = findViewById(R.id.gridLayout);

        /*
         * Spec参数总结：
         * start：第几个。从0开始。
         * size：大小，跨越几个格，如果rowSpec里的size为2就是这个cell高度占两行。
         * weight，大小的权重。
         *
         *
         * gridlayout控制子view位置和其单元格跨度，需要传多个明确的layout参数来决定view的坐标和大小。
         * 在代码内不实用，造成代码冗余复杂，在布局xml文件实现宫格还可以。
         */

        Button button1 = new Button(this);
        GridLayout.Spec rowSpec = GridLayout.spec(0, 1f);
        GridLayout.Spec columnSpec = GridLayout.spec(0, 1f);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        button1.setLayoutParams(layoutParams);
        button1.setText("textview1");


        Button button2 = new Button(this);
        rowSpec = GridLayout.spec(0, 1f);
        columnSpec = GridLayout.spec(1, 1f);
        layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        button2.setLayoutParams(layoutParams);
        button2.setText("textview2");

        Button button3 = new Button(this);
        rowSpec = GridLayout.spec(1, 1f);
        columnSpec = GridLayout.spec(0, 1f);
        layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        button3.setLayoutParams(layoutParams);
        button3.setText("textview3");

//        Button button4 = new Button(this);
//        button4.setText("textview4");


        gridLayout.addView(button1);
        gridLayout.addView(button2);


        gridLayout.addView(button3);
//        gridLayout.addView(button4);


        Log.i(TAG, "onCreate: " + gridLayout.getChildCount());
    }
}
