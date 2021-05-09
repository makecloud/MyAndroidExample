package com.liuyihui.client.myexample.demo26_progressbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

/**
 * 进度条 progressBar demo
 */
public class ProgressBarDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_progress_bar_demo);

        //？
        setProgressBarIndeterminateVisibility(true);
        setProgress(2000);

    }

    //标题栏进度条
    public void showTitleBarProgressBar(View view) {
        setProgressBarIndeterminateVisibility(true);
    }

    //水平进度条
    public void horizontalProgressBar(View view) {
        ProgressBar progressBar = findViewById(R.id.progressbar01);
        progressBar.incrementProgressBy(1);
    }

    /**
     * 进度条对话框 用例
     */
    public void progressDialogUsage(View view) {
        //实例化
        ProgressDialog mypDialog = new ProgressDialog(this);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置ProgressDialog 标题
        mypDialog.setTitle("Google");
        //设置ProgressDialog 提示信息
        mypDialog.setMessage("message");
        //设置ProgressDialog 标题图标
        mypDialog.setIcon(R.drawable.wb_search_icon);
        //设置ProgressDialog 的一个Button
        mypDialog.setButton("Google", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ProgressBarDemoActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        //设置ProgressDialog 的进度条是否不明确
        mypDialog.setIndeterminate(false);
        //设置ProgressDialog 是否可以按退回按键取消
        mypDialog.setCancelable(true);
        //让ProgressDialog显示
        mypDialog.show();
    }
}
