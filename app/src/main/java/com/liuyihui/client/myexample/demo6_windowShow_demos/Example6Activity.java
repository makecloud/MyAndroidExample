package com.liuyihui.client.myexample.demo6_windowShow_demos;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

/**
 * 多种实现弹出窗口的用例
 * <p>
 * popupWindow 用例
 * alertDialog 用例
 * dialogFragment 用例
 */
public class Example6Activity extends AppCompatActivity {
    private final String TAG = "Example6Activity";
    private Button clickBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);
        //点击弹窗按钮
        clickBtn = findViewById(R.id.btn_click_pop);
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(view);
            }
        });
    }


    /**
     * 显示 popupWindow 用例1
     *
     * @param anchorView
     */
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
        //        popupWindow.showAtLocation(container, Gravity.NO_GRAVITY, anchorViewPosition[0] + anchorView
        //        .getWidth(), anchorViewPosition[1]);
        //显示方式四: parentView方式,这个clickBtn的意义是parentView
        //        popupWindow.showAtLocation(clickBtn, Gravity.NO_GRAVITY, 2, 5);
    }


    /**
     * 显示 popupWindow 用例2
     *
     * @param view
     */
    public void popupWindowDemo(View view) {
        PopupWindow popupWindow = new PopupWindow();
        TextView textView = new TextView(this);
        textView.setHint("popupwindowdemo");
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(400);
        popupWindow.setBackgroundDrawable(getDrawable(R.color.colorAccent));
        popupWindow.setHeight(200);
        popupWindow.setContentView(textView);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }


    /**
     * alertDialog 用例
     *
     * @param view
     */
    public void alertDialogDemo(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("demo message").show();
    }

    /**
     * dialogFragment 用例
     *
     * @param view
     */
    public void dialogFragmentDemo(View view) {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.setShowsDialog(true);
        dialogFragment.show(getSupportFragmentManager(), TAG);
    }
}
