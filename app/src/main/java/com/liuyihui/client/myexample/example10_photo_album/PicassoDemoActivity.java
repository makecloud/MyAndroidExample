package com.liuyihui.client.myexample.example10_photo_album;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.liuyihui.client.myexample.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PicassoDemoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_demo);

        imageView = findViewById(R.id.imageview);
        String url = "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1570857736163.jpg";

        File file = new File(url);
        Log.d(TAG, "onCreate: " + file.exists());

        Picasso.with(this).load("file:" + url).into(imageView);
    }
}
