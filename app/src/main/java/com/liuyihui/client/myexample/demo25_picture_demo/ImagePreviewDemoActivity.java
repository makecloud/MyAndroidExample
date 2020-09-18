package com.liuyihui.client.myexample.demo25_picture_demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.liuyihui.photoview.NoZoomMovedYListener;
import com.liuyihui.photoview.PhotoView;
import com.liuyihui.photoview.TouchUpLIstener;
import com.liuyihui.client.myexample.R;

/**
 * 单张图片展示demo
 * <p>
 * 可拖动效果 demo
 */
public class ImagePreviewDemoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    FrameLayout rootLayout;
    PhotoView photoView;

    private boolean movedYEnoughToExit = false;
    private double responseHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_preview_demo);

        //沉浸状态栏. 此时content view 扩展到 status bar覆盖的下面了，但是status bar的背景色还会遮挡 content view
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //设置 status bar的背景透明
        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));

        //get some used size
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenHeight = metric.heightPixels;

        responseHeight = screenHeight * 0.25;

        //view
        rootLayout = findViewById(R.id.root_layout);
        photoView = findViewById(R.id.image);

        photoView.setMinimumScale(1.0f);
        photoView.setMediumScale(1.5f);
        photoView.setMaximumScale(2f);

        //set demo picture
        photoView.setImageResource(R.drawable.testphoto);


        photoView.setNoZoomMovedYListener(new NoZoomMovedYListener() {
            @Override
            public void onMovedYRate(double moveRate) {

                if (moveRate > 0) {
                    // dy为屏幕y轴滑动距离。透明度的响应距离为，屏幕y轴的25%。即x轴滑动超过25%就全透明.
                    int degree = 255 - (int) (moveRate * 255);
                    if (degree < 0) {
                        degree = 0;
                        movedYEnoughToExit = true;
                    } else {
                        movedYEnoughToExit = false;
                    }
                    Log.d(TAG, "onMove: moveRate:" + moveRate + ",degree:" + degree);
                    changeTransparencyDegree(degree);
                }
            }
        });

        photoView.setTouchUpListener(new TouchUpLIstener() {
            @Override
            public void onTouchUp() {
                if (movedYEnoughToExit) {
                    photoView.setWillBeExit(true);
                    finish();
                } else {
                    changeTransparencyDegree(255);
                }
            }
        });


    }

    /**
     * 改变根布局背景透明度
     *
     * @param degree 0-255; 255完全不透明
     */
    private void changeTransparencyDegree(int degree) {
        //改变背景透明度
        Drawable backgroundDrawable = rootLayout.getBackground();
        if (0 <= degree && degree <= 255) {
            backgroundDrawable.setAlpha(degree);
        }
    }


}
