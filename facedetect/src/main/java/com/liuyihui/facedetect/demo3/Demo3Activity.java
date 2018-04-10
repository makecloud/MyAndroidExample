package com.liuyihui.facedetect.demo3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.liuyihui.facedetect.R;

import java.io.IOException;
import java.util.List;

public class Demo3Activity extends AppCompatActivity {
    private static final String TAG = "Demo3Activity";
    private static final int REQUEST_CAMERA_CODE_TEACHER = 1;// 拍照    Camera camera;
    Camera camera;
    Button startButton;
    SurfaceView surfaceView;
    SurfaceView frontSurfaceView;
    SurfaceHolder frontSurfaceHolder;
    Paint paint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        getPerssiom();
        surfaceView = findViewById(R.id.surfaceview);
        frontSurfaceView = findViewById(R.id.front_surfaceview);
        frontSurfaceView.setBackgroundColor(Color.TRANSPARENT);
        frontSurfaceView.bringToFront();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);

        startFrontSurfaceView();

        //surfaceHolder是surface联系到surfaceView对象的纽带
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        //surfaceHolder添加surface周期
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            //surface创建回调
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    //设置相机对象往哪里绘制，即surfaceHolder，而surfaceHolder又作为联系到surface的纽带。说往surface绘制也无问题。
                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //surface改变回调
            @Override
            public void surfaceChanged(final SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                //camera开始在surface对象上绘制帧
                Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
                List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
                Camera.Size optionSize = getOptimalPreviewSize(sizeList, surfaceView.getWidth(), surfaceView.getHeight());//获取一个最为适配的camera.size
                parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
                camera.setParameters(parameters);//把parameters设置给camera
                camera.setDisplayOrientation(90);
                Log.i(TAG, "surfaceChanged");
                final BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
                bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;

                camera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                    @Override
                    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                        for (Camera.Face face : faces) {
                            Canvas canvas = frontSurfaceHolder.lockCanvas();
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            canvas.drawRect(20, 20, 100, 100, paint);
                            frontSurfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                });
                camera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] bytes, Camera camera) {
                        Canvas canvas = frontSurfaceHolder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//貌似这句实现移除方框
                        frontSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                });
                camera.startFaceDetection();
                camera.startPreview();

                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean b, Camera camera) {

                    }
                });
            }

            //surface销毁回调
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                //camera停止在surfa上绘制帧
                //                activity_capture.stopPreview();
                if (camera != null) {
                    camera.stopPreview();
                }
                camera.release();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
        }
    }

    /**
     * android6.0 申请拍照权限
     */
    private void getPerssiom() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CAMERA_CODE_TEACHER);
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

    public void startFrontSurfaceView() {
        frontSurfaceHolder = frontSurfaceView.getHolder();
        frontSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

    }
}
