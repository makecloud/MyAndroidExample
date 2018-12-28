package com.liuyihui.dialog_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * 进度条示例
 */
public class ProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_progress_bar);

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


}
