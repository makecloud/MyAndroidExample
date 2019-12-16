package com.liuyihui.dialog_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 对话框demo
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //启动标题栏进度条示例
    public void startProgressBarActivity(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }

    public void dialogFragmentDemo(View view) {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.setShowsDialog(true);
        dialogFragment.show(getSupportFragmentManager(), TAG);
    }

    public void alertDialogDemo(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("demo message").show();
    }

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
}
