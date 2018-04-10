package com.liuyihui.facedetect.demo4_backgrounddetect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.liuyihui.facedetect.R;

import java.util.List;

public class BackgroundDetectActivity extends AppCompatActivity {
    final static String TAG = "BackgroundDetectAc";
    Camera camera;
    int widthPixels;
    int heightPixels;
    int screenWidth;
    int screenHeight;
    Button takephotoButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_detect);
        imageView = findViewById(R.id.imageView);
        takephotoButton = findViewById(R.id.takephoto);
        takephotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean b, Camera camera) {
                    }
                });
                camera.takePicture(null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        getAndroiodScreenProperty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(1);
        Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, widthPixels, heightPixels);//获取一个最为适配的camera.size
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
        camera.setParameters(parameters);//把parameters设置给camera
        camera.setDisplayOrientation(90);
        camera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                Log.e(TAG, "onFaceDetection");
            }
        });
        camera.startPreview();
        camera.startFaceDetection();
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }


    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;         // 屏幕宽度（像素）
        heightPixels = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (widthPixels / density);  // 屏幕宽度(dp)
        screenHeight = (int) (heightPixels / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + widthPixels);
        Log.d("h_bl", "屏幕高度（像素）：" + heightPixels);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }
}
