package com.liuyihui.client.myexample.example25_picture_demo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

/**
 * 对bitmap的各种操作
 */
public class BitmapOperationActivity extends AppCompatActivity {
    private static final String TAG = "BitmapOperationActivity";
    public final static int REQUEST_CODE_PICTURE = 1;
    //要操作的照片预览
    ImageView selectedImageImageView;

    //要操作的照片bitmap
    Bitmap selectedImageBitmap;

    //裁剪按钮
    Button doCropButton;

    //打水印按钮
    Button doWaterMaskButton;

    //操作后的图片展示
    ImageView operatedResultImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_operation);

        selectedImageImageView = findViewById(R.id.selected_image_preview);
        doCropButton = findViewById(R.id.do_crop);
        doWaterMaskButton = findViewById(R.id.do_water_mask);
        operatedResultImageView = findViewById(R.id.operated_result_image_show);
        operatedResultImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BitmapOperationActivity.this, "aaa", Toast.LENGTH_SHORT).show();
            }
        });


        //选择照片
        selectedImageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动系统相册
                Intent i = new Intent(Intent.ACTION_PICK,
                                      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_PICTURE);
            }
        });

        //裁剪按钮
        doCropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageBitmap == null) {
                    Toast.makeText(BitmapOperationActivity.this,
                                   "selected bitmap == null",
                                   Toast.LENGTH_SHORT).show();
                    return;
                }

                int cropWidth = 200;
                int cropHeight = 200;
                //在重建一个bitmap,传参数,实现裁剪
                Bitmap cropedBitmap = Bitmap.createBitmap(selectedImageBitmap,
                                                          500,
                                                          500,
                                                          cropWidth,
                                                          cropHeight,
                                                          null,
                                                          false);
                assert cropedBitmap != null;
                operatedResultImageView.setImageBitmap(cropedBitmap);
            }
        });

        //打水印按钮
        doWaterMaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageBitmap == null) {
                    Toast.makeText(BitmapOperationActivity.this,
                                   "selected bitmap == null",
                                   Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap resultBitmap = ImageUtil.drawTextToRightBottom(BitmapOperationActivity.this,
                                                                      selectedImageBitmap,
                                                                      "23413\nrer",
                                                                      16,
                                                                      Color.GRAY,
                                                                      0,
                                                                      0);

                operatedResultImageView.setImageBitmap(resultBitmap);
            }
        });
    }


    /**
     * 图库选择照片或拍照返回结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICTURE && null != data) {
                Uri selectedImageUri = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver()
                               .query(selectedImageUri, filePathColumns, null, null, null);
                assert c != null;
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                //拿到图片路径
                String picturePath = c.getString(columnIndex);
                c.close();
                //弹窗显示图片路径
                Toast.makeText(this, picturePath, Toast.LENGTH_SHORT).show();


                //显示到view
                selectedImageImageView.setImageURI(selectedImageUri);

                //设置当前bitmap
                setCurrentBitmap(picturePath);

            }
        }
    }

    /**
     * @param imagePath
     */
    public void setCurrentBitmap(String imagePath) {
        //制作bitmap对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 200;
        options.outHeight = 200;
        selectedImageBitmap = BitmapFactory.decodeFile(imagePath, options);
    }
}
