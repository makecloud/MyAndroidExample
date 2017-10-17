package com.liuyihui.client.myexample.example6_use_popupwindow;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.liuyihui.client.myexample.R;

/**
 * 弹popupWindow
 */
public class Example6Activity extends AppCompatActivity {
    private Button clickBtn;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);

        container = (LinearLayout) findViewById(R.id.container);
        clickBtn = (Button) findViewById(R.id.btn_click_pop);
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(view);
            }
        });
    }


    public void showWindow(View view) {
        View v = getLayoutInflater().inflate(R.layout.popup_window_1, null);
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(v);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//不设置背景透明，点外面popupWindow也不消失
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
//        popupWindow.showAsDropDown(clickBtn, 5, 5);
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        popupWindow.showAtLocation(container, Gravity.NO_GRAVITY, position[0] + view.getWidth(), position[1]);
//        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 2, 5);
    }
}
