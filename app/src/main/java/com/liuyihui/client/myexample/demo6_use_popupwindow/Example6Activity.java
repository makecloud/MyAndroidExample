package com.liuyihui.client.myexample.demo6_use_popupwindow;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.liuyihui.client.myexample.R;

/**
 * 弹popupWindow
 */
public class Example6Activity extends AppCompatActivity {
    private final String TAG = "Example6Activity";
    private Button clickBtn;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);
        //点击弹窗按钮
        clickBtn = (Button) findViewById(R.id.btn_click_pop);
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(view);
            }
        });
        //红色容器布局
        container = (LinearLayout) findViewById(R.id.container);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showWindow(View anchorView) {
        View v = getLayoutInflater().inflate(R.layout.popup_window_1, null);

        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(v);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//不设置背景透明，点外面popupWindow也不消失
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //显示方式一: anchorView方式,这个clickBtn的意义是anchorView,作为锚点
        //popupWindow.showAsDropDown(clickBtn, 15, 15);
        //显示方式二:anchorView方式,
        popupWindow.showAsDropDown(clickBtn, 0, v.getHeight(), Gravity.CENTER);
        //显示方式三:parentView方式
//        int[] anchorViewPosition = new int[2];
//        anchorView.getLocationOnScreen(anchorViewPosition);
//        popupWindow.showAtLocation(container, Gravity.NO_GRAVITY, anchorViewPosition[0] + anchorView.getWidth(), anchorViewPosition[1]);
        //显示方式四: parentView方式,这个clickBtn的意义是parentView
//        popupWindow.showAtLocation(clickBtn, Gravity.NO_GRAVITY, 2, 5);
    }
}
