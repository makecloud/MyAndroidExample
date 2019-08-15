package com.liuyihui.exeshell;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import util.ShellUtils;

/**
 * 父类写一些子类公共的方法，减少子类代码
 */
public class BaseActivity extends AppCompatActivity {
    protected TextView resultTextView;

    protected void showString(String result) {
        resultTextView.setText(result);
    }

    protected void showCommandResult(ShellUtils.CommandResult commandResult) {
        String info = "resultCode:" + commandResult.result + "\n" +
                ",successMsg:" + commandResult.successMsg + "\n" +
                ",errorMsg:" + commandResult.errorMsg;
        resultTextView.setText(info);
    }
}
