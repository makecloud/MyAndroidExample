package com.liuyihui.client.myexample.example10_photo_album;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liuyihui.client.myexample.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;

/**
 * 知乎matisse 相册 demo
 */
public class MatisseDemoActivity extends AppCompatActivity {
    private final int REQUEST_CODE_CHOOSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matisse_demo);

    }

    public void startMatisse(View view) {
        Matisse.from(this)
               .choose(MimeType.ofAll())
               .countable(true)
               .maxSelectable(9)
               //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
               .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
               .thumbnailScale(0.85f)
               .imageEngine(new PicassoEngine())
               .forResult(REQUEST_CODE_CHOOSE);
    }

}
