package com.oohlink.liuyihui.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.oohlink.liuyihui.qrcode.qrcodegeneratebase.QRcodeUtil;
import com.oohlink.liuyihui.qrcode.qrcodescanbase.activity.CaptureActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private final int IMAGE_CODE = 0;
    private final String IMAGE_TYPE = "image/*";
    private static final int IMAGE_HALFWIDTH = 20;
    int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
    private Bitmap mBitmap;

    private EditText qrcodeContentEt;
    private ImageView qrImgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrcodeContentEt = findViewById(R.id.qr_code_content);
        qrImgImageView = findViewById(R.id.gen_qr_img);
    }

    public void startQrcode(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    /**
     * 生成二维码
     * <p>
     * 二维码图片自动保存到了一个目录下了
     *
     * @param view
     */
    public void genQrcode(View view) {
        String contentString = qrcodeContentEt.getText().toString();
        try {
            if (contentString != null
                    && contentString.trim().length() > 0) {
                // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                Bitmap qrCodeBitmap = QRcodeUtil.EncodeQRCode(contentString,
                                                              350,
                                                              Color.BLACK,
                                                              Color.WHITE,
                                                              null);

                qrImgImageView.setImageBitmap(qrCodeBitmap);
                saveJpeg(qrCodeBitmap);
            } else {
                Toast.makeText(MainActivity.this,
                               "Text can not be empty", Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (Exception e) {
            Log.e(TAG, "生成二维码错误", e);
        }
    }

    /**
     * 添加图片按钮
     * <p>
     * 二维码图片自动保存到了一个目录下了
     *
     * @param view
     */
    public void addImage(View view) {
        String contentString = qrcodeContentEt.getText().toString();
        if (!TextUtils.isEmpty(contentString)) {
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType(IMAGE_TYPE);
            startActivityForResult(getAlbum, IMAGE_CODE);
        } else {
            Toast.makeText(getApplication(), "未填写信息", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 处理上一个页面关闭返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String QRcodeContentStr = bundle.getString("result");//得到二维码内容字符串
                    Bitmap qrPicture = data.getParcelableExtra("bitmap");//得到二维码里的照片
                    Toast.makeText(this, QRcodeContentStr, Toast.LENGTH_SHORT).show();

                    String contentString = qrcodeContentEt.getText().toString();
                    if (TextUtils.isEmpty(contentString)) {
                        Toast.makeText(this, "输入内容为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Bitmap qrCodeBitmap = QRcodeUtil.EncodeQRCode(contentString,
                                                                  350,
                                                                  Color.BLACK,
                                                                  Color.WHITE,
                                                                  qrPicture);
                    qrImgImageView.setImageBitmap(qrCodeBitmap);
                    saveJpeg(qrCodeBitmap);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 保存图片
     *
     * @param bm
     */
    public void saveJpeg(Bitmap bm) {
        long dataTake = System.currentTimeMillis();
        String jpegName = initSavePath() + dataTake + ".jpg";
        // File jpegFile = new File(jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            // Bitmap newBM = bm.createScaledBitmap(bm, 600, 800, false);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            Log.e(TAG, "保存图片异常", e);
            e.printStackTrace();
        }
    }

    /**
     * 初始化保存路径
     *
     * @return
     */
    public String initSavePath() {
        File dateDir = Environment.getExternalStorageDirectory();
        String path = dateDir.getAbsolutePath() + "/RectPhoto/";
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return path;
    }

}