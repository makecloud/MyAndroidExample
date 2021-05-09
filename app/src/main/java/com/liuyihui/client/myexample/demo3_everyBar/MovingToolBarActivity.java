package com.liuyihui.client.myexample.demo3_everyBar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.liuyihui.client.myexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 可以移动折叠的工具栏 基本用法案例1
 */
public class MovingToolBarActivity extends AppCompatActivity {

    private static final String TAG = "MovingToolBarActivity";
    @BindView(R.id.appbarlayout)
    AppBarLayout appBarLayout;
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
        for (int i = 0; i < 60; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   80));
            textView.setText(String.valueOf(i));
            nestedScrollView.addView(textView);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fabClick(View view) {
    }

}
