package com.liuyihui.client.myexample.example25_picture_demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.liuyihui.client.myexample.R;
import com.liuyihui.client.myexample.example25_picture_demo.customview.DragableImageView;
import com.liuyihui.client.myexample.example25_picture_demo.customview.DragablePhotoView;

/**
 * 单张图片展示demo
 * <p>
 * 可拖动效果 demo
 */
public class ImagePreviewDemoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    FrameLayout rootLayout;
    //    DragableImageView imageView;
    DragablePhotoView imageView;

    int screenHeight;
    double responseHeight;

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
        imageView = findViewById(R.id.image);

        imageView.setMinimumScale(0.5f);
        imageView.setMediumScale(1.2f);
        imageView.setMaximumScale(1.5f);
        /*imageView.setOnViewDragListener(new OnViewDragListener() {
            @Override
            public void onDrag(float dx, float dy) {
                if (dy > 0) {
                    // dy为屏幕y轴滑动距离。透明度的响应距离为，屏幕y轴的25%。即x轴滑动超过25%就全透明.
                    int degree = (int) (255 - 255 * dy / responseHeight);
                    Log.d(TAG, "onMove: dx:" + dy + ",degree:" + degree);
                    changeTransparencyDegree(degree);
                }
            }
        });*/
        //set demo picture
        imageView.setImageResource(R.drawable.testphoto);


        //
        imageView.setMoveListener(new DragablePhotoView.MoveListener() {
            @Override
            public void onMove(int dx, int dy) {
                if (dy > 0) {
                    /*
                     * dy为屏幕y轴滑动距离。透明度的响应距离为，屏幕y轴的25%。即x轴滑动超过25%就全透明.
                     */
                    int degree = (int) (255 - 255 * dy / responseHeight);
                    Log.d(TAG, "onMove: dx:" + dy + ",degree:" + degree);
                    changeTransparencyDegree(degree);
                }
            }
        });

        //
        imageView.setTouchUpListerner(new DragablePhotoView.TouchUpListerner() {
            @Override
            public void onTouchUp(int moveDx, int moveDy) {
                if (moveDy < responseHeight) {
                    changeTransparencyDegree(255);
                } else {
                    finish();
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
