package com.oohlink.liuyihui.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oohlink.liuyihui.qrcode.qrcodegeneratebase.DensityUtils;
import com.oohlink.liuyihui.qrcode.qrcodegeneratebase.QRcodeUtil;
import com.oohlink.liuyihui.qrcode.qrcodescanbase.activity.CaptureActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private final int IMAGE_CODE = 0;
    private final String IMAGE_TYPE = "image/*";
    private static final int IMAGE_HALFWIDTH = 20;
    int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
    private Bitmap mBitmap;

    private EditText qrcodeContentEt;
    private Bitmap generatedQrCodeBitmap;
    private ImageView qrImgImageView;
    private EditText sizeInput;
    private RadioGroup sizeUnitInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrcodeContentEt = findViewById(R.id.qr_code_content);
        qrImgImageView = findViewById(R.id.gen_qr_img);
        sizeInput = findViewById(R.id.sizeInput);
        sizeUnitInput = findViewById(R.id.sizeUnitInput);
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
            if (contentString != null && contentString.trim().length() > 0) {
                // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                generatedQrCodeBitmap = QRcodeUtil.EncodeQRCode(contentString,
                                                                350,
                                                                Color.BLACK,
                                                                Color.WHITE,
                                                                null);

                //get input size
                try {
                    String[] sizeStr = sizeInput.getText().toString().split("x");
                    int width = Integer.parseInt(sizeStr[0]);
                    int height = Integer.parseInt(sizeStr[1]);
                    int selectSizeUnitId = sizeUnitInput.getCheckedRadioButtonId();


                    LinearLayout.LayoutParams lp =
                            (LinearLayout.LayoutParams) qrImgImageView.getLayoutParams();
                    if (selectSizeUnitId == R.id.px) {
                        lp.width = width;
                        lp.height = height;
                    } else if (selectSizeUnitId == R.id.dp) {
                        lp.width = DensityUtils.dp2px(this, width);
                        lp.height = DensityUtils.dp2px(this, height);
                    }
                    qrImgImageView.setLayoutParams(lp);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //show image src
                qrImgImageView.setImageBitmap(generatedQrCodeBitmap);
            } else {
                Toast.makeText(MainActivity.this, "Text can not be empty", Toast.LENGTH_SHORT)
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
        if (qrImgImageView.getDrawable() == null) {
            Toast.makeText(getApplication(), "请先生成二维码", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //        intent.setType(IMAGE_TYPE);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_CODE);
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
                }
                break;
            case IMAGE_CODE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                String picturePath = UriUtil.getFilePathByUri(this, data.getData());
                Log.d(TAG, "onActivityResult: " + picturePath);

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                generatedQrCodeBitmap = QRcodeUtil.addLogoToQRCode(generatedQrCodeBitmap, bitmap);
                qrImgImageView.setImageBitmap(generatedQrCodeBitmap);
                break;
            default:
                break;
        }
    }

    public void saveQrCode(View view) {
        saveJpeg(generatedQrCodeBitmap, System.currentTimeMillis() + "");
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param name
     */
    public void saveJpeg(Bitmap bm, String name) {

        //存入内部存储Picture下，微信里能看到
        String publicPicturePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                              .getAbsolutePath();
        File file = new File(publicPicturePath, name);
        if (!file.exists()) {
            //这个存粗到sdcard/Picture下了
            MediaStore.Images.Media.insertImage(getContentResolver(), bm, name, "no");
        }

        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();

        //存到DCIM微信还不能看到，微信能看到DCIM/Camera下的
        /*String DCIMPath = Environment.getExternalStoragePublicDirectory(Environment
        .DIRECTORY_DCIM)
                                     .getAbsolutePath();
        String jpegName = DCIMPath + System.currentTimeMillis() + ".jpg";
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
        }*/
    }
}
