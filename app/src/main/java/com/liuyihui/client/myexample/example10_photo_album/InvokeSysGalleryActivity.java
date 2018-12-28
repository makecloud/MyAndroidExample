package com.liuyihui.client.myexample.example10_photo_album;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;

/**
 * 调用系统相册实例
 */
public class InvokeSysGalleryActivity extends AppCompatActivity {
    public final static String TAG = "InvokeSysGalleryActivity";
    public final static int REQUEST_CODE_PICTURE = 1;
    private Button startGalleryButton;
    private Button startVideoGalleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke_sys_gallery);

        startGalleryButton = (Button) findViewById(R.id.button1);
        startVideoGalleryButton = (Button) findViewById(R.id.button2);

        startGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动系统相册
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_PICTURE);
            }
        });

        startVideoGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动系统相册,选视频
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                ArrayList<String> mimes = new ArrayList<>();
                mimes.add("image/*");
                mimes.add("video/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimes);
                //４．３以上的设备才支持Intent.EXTRA_ALLOW_MULTIPLE，是否可以一次选择多个文件
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(intent, REQUEST_CODE_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICTURE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            //获取图片并显示
            Toast.makeText(this, picturePath, Toast.LENGTH_SHORT).show();
            //上传照片

        }
    }
}
