package com.liuyihui.bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oohlink.wifiprobe.everybar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 可以移动折叠的工具栏 基本用法案例1
 */
public class MovingToolBarActivity extends AppCompatActivity {

    private static final String TAG = "MovingToolBarActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nestedscrollview)
    LinearLayout nestedScrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉本activity的titleBar
        setContentView(R.layout.activity_moving_tool_bar);
        ButterKnife.bind(MovingToolBarActivity.this);


        toolbar.setTitle("toolBar Title");
//        setSupportActionBar(toolbar);


        //往滑动view里添加item,使有足够数据滑动
        for (int i = 0; i < 30; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   80));
            textView.setText(String.valueOf(i));
            nestedScrollView.addView(textView);
        }
    }




}
