package com.liuyihui.client.myexample.demo3_everyBar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.R;


/**
 * system ui control
 */
public class SystemUIDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_ui_demo);

        getWindow().getDecorView()
                   .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                       @Override
                       public void onSystemUiVisibilityChange(int visibility) {
                           if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                               // TODO: The system bars are visible. Make any desired
                               // adjustments to your UI, such as showing the action bar or
                               // other navigational controls.
                               showActionBar();
                           } else {
                               // TODO: The system bars are NOT visible. Make any desired
                               // adjustments to your UI, such as hiding the action bar or
                               // other navigational controls.
                               hideActionBar();
                           }
                       }
                   });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void bt1Click(View view) {
        hideStatusBar();
    }

    public void bt2Click(View view) {
        showStatusBar();
    }

    public void bt3Click(View view) {
        hideActionBar();
    }

    public void bt4Click(View view) {
        showActionBar();
    }

    public void bt5Click(View view) {
        layoutBehindStatusBar();
    }

    /*
    这些flag 都是互相覆盖的，要想同时设置几种不同效果，使用 ｜ 来组合flag
     */

    public void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void showStatusBar() {
        View decorView = getWindow().getDecorView();
        //        int uiOption = 0;
        int uiOption = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);//0 means clear all flags include FullScreen
    }

    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    public void layoutBehindStatusBar() {
        View decorView = getWindow().getDecorView();
        // layout behind status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
