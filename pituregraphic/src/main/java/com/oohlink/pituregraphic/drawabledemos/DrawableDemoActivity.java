package com.oohlink.pituregraphic.drawabledemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oohlink.pituregraphic.R;

/**
 * 各种drawable demo
 */
public class DrawableDemoActivity extends AppCompatActivity {
    private ConstraintLayout rootLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_demo);
        rootLayout = findViewById(R.id.rootLayout);
        imageView = findViewById(R.id.imageView);

        //rootLayout.setBackgroundResource(R.drawable.logo_mini);

        //layerDrawableDemo1();
        //layerDrawableDemo2();
        //layerDrawableDemo3();

    }


    /**
     * layerDrawable 使用示例 代码中创建 多层叠的可绘制对象 layerDrawable
     */
    public void layerDrawableDemo1() {
        Drawable whiteDrawable = new ColorDrawable(Color.WHITE);
        BitmapDrawable logoDrawable =
                (BitmapDrawable) getResources().getDrawable(R.drawable.galaxy_276_300);


        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{whiteDrawable,
                logoDrawable});
        layerDrawable.setLayerGravity(1, Gravity.CENTER);
        layerDrawable.setLayerInset(1, 0, 0, 0, 0);


        imageView.setBackground(layerDrawable);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setImageDrawable(layerDrawable);


    }

    /**
     * layerDrawable 使用示例2 从xml获取对象，然后改变，使用
     */
    public void layerDrawableDemo2() {

        LayerDrawable layerDrawable =
                (LayerDrawable) getResources().getDrawable(R.drawable.logo_mini);


        imageView.setBackground(layerDrawable);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setImageDrawable(layerDrawable);
    }

    /**
     * 多层叠的可绘制对象 demo 3
     */
    public void layerDrawableDemo3() {
        Bitmap bitmap = BitmapFactory.decodeFile(
                "/sdcard/oohlink/player/.reverse/4b472dc846cd5c6705c964a31aba5b87");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

        Drawable whiteDrawable = new ColorDrawable(Color.GREEN);
        final LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{whiteDrawable,
                bitmapDrawable});

        layerDrawable.setLayerGravity(1, Gravity.CENTER);
        //layerDrawable.setLayerSize(1, bitmap.getWidth(), bitmap.getHeight());


        imageView.setBackground(layerDrawable);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setImageDrawable(layerDrawable);
    }
}
