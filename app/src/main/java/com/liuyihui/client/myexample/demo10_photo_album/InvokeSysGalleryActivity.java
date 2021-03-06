package com.liuyihui.client.myexample.demo10_photo_album;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;
import com.liuyihui.mylibrary.util.UriUtil;

import java.util.ArrayList;

/**
 * 调用系统相册实例
 */
public class InvokeSysGalleryActivity extends AppCompatActivity {
    public final static String TAG = "InvokeSysGalleryActivit";
    public final static int REQUEST_CODE_CAMERA_PHOTO = 0;
    public final static int REQUEST_CODE_PICTURE = 1;
    public final static int REQUEST_CODE_VIDEO = 2;
    public final static int REQUEST_CODE_DOC = 3;
    private static final int REQUEST_CODE_CAMERA_VIDEO = 4;

    private Button startCameraButton;
    private Button startGalleryButton;
    private Button startVideoGalleryButton;
    private Button startDocButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke_sys_gallery);

        startCameraButton = findViewById(R.id.button0);
        startGalleryButton = findViewById(R.id.button1);
        startVideoGalleryButton = findViewById(R.id.button2);
        startDocButton = findViewById(R.id.button3);

        startCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统相机,跟打开相机一样，貌似不会返回结果
                //                startActivityForResult(new Intent(MediaStore
                //                .INTENT_ACTION_VIDEO_CAMERA),
                //                                       REQUEST_CODE_CAMERA);

                //打开相机拍照，返回照片结果
                //                startActivityForResult(new Intent(MediaStore
                //                .ACTION_IMAGE_CAPTURE),
                //                                       REQUEST_CODE_CAMERA_PHOTO);
                //打开相机拍摄视频，返回视频结果
                startActivityForResult(new Intent(MediaStore.ACTION_VIDEO_CAPTURE),
                                       REQUEST_CODE_CAMERA_VIDEO);
            }
        });
        startGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动系统相册
                Intent i = new Intent(Intent.ACTION_PICK,
                                      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(i, REQUEST_CODE_PICTURE);
            }
        });

        startVideoGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动系统相册,选视频
                Intent intent = new Intent(Intent.ACTION_PICK,
                                           MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("*/*");

                ArrayList<String> mimes = new ArrayList<>();
                //mimes.add("image/*");
                mimes.add("video/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, REQUEST_CODE_VIDEO);
            }
        });

        startDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                ArrayList<String> mimes = new ArrayList<>();
                mimes.add("image/*");
                mimes.add("video/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);
                //４．３以上的设备才支持Intent.EXTRA_ALLOW_MULTIPLE，是否可以一次选择多个文件
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, REQUEST_CODE_DOC);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //进相册选图片返回
        if (requestCode == REQUEST_CODE_PICTURE && resultCode == Activity.RESULT_OK && null != data) {

            //多选返回时为null
            //Uri selectedImage = data.getData();


            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                Log.d(TAG, "onActivityResult: " + item.getUri());
                Uri uri = item.getUri();

                //根据uri查询file path
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(uri, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                //显示图片路径
                Toast.makeText(this, picturePath, Toast.LENGTH_SHORT).show();

            }
        }

        //进相册选视频返回
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == Activity.RESULT_OK && null != data) {
            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                String filePath = UriUtil.getFilePathByUri(this, clipData.getItemAt(i).getUri());
                Log.d(TAG, "onActivityResult: " + filePath);
            }
        }

        //选文档返回
        if (requestCode == REQUEST_CODE_DOC && resultCode == Activity.RESULT_OK && null != data) {
            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                String filePath = UriUtil.getFilePathByUri(this, clipData.getItemAt(i).getUri());
                Log.d(TAG, "onActivityResult: " + filePath);
            }
        }

        //打开相机拍照片返回
        if (requestCode == REQUEST_CODE_CAMERA_PHOTO) {
            Bitmap bitmap = data.getParcelableExtra("data");
        }

        //打开相机拍视频返回
        if (requestCode == REQUEST_CODE_CAMERA_VIDEO) {
            // TODO: 2020-06-01
        }
    }
}
