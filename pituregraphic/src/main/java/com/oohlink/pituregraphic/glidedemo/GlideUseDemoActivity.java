package com.oohlink.pituregraphic.glidedemo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.contentcapture.ContentCaptureSessionId;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.BitmapTransitionFactory;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.oohlink.pituregraphic.databinding.ActivityGlideUseDemoBinding;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;

/**
 * glide 框架使用demo 测试demo
 */
public class GlideUseDemoActivity extends AppCompatActivity {
    private final String TAG = "GlideUseDemoActivity";
    private ActivityGlideUseDemoBinding binding;
    private String url = "http://test.yungeshidai.com/material/40/4d439abe1b395ff6be5d5bef81e469c8.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("glide use demo");
        binding = ActivityGlideUseDemoBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }

    public void onLoadButtonClick(View view) {
        //简单加载一个url 图片
        //loadUrlToImageView();

        //带动画
        //loadUrlToImageViewWithTransition();

        //禁用所有缓存，每次都重新加载
        loadUrlToImageViewDisableCache();

        //加载线程test
        //loadingThreadTest();

        //单加载bitmap
        //loadAsBitmap();

        //加载缓存demo
        //loadCacheDemo();
    }

    public void onClearButtonClick(View view) {
        clearImageFromImageView();
    }

    public void loadUrlToImageView() {
        GlideApp.with(this).load(url).into(binding.imageView1);
    }

    public void loadUrlToImageViewWithTransition() {
        GlideApp.with(this).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(binding.imageView1);
    }

    public void loadUrlToImageViewDisableCache() {
        GlideApp.with(this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView1);
    }

    public void loadingThreadTest() {
        //子线程调用报错：into只能在主线程调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.with(GlideUseDemoActivity.this).load(url).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady: ");
                    }
                });
            }
        }).start();
    }

    public void loadAsBitmap() {
        //在子线程同步加载bitmap
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<Bitmap> bitmapFutureTarget = GlideApp.with(GlideUseDemoActivity.this)
                                                                  .asBitmap()
                                                                  .load(url)
                                                                  .submit();
                try {
                    //调用get的时候才执行加载
                    Bitmap bitmap = bitmapFutureTarget.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void loadCacheDemo() {
        RequestBuilder<Drawable> requestBuilder = GlideApp.with(this).asDrawable();
        GlideApp.with(GlideUseDemoActivity.this)
                .asDrawable()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady: ");
                    }
                });
    }

    public void clearImageFromImageView() {
        //会将imageView上的显示的图片移除
        GlideApp.with(this).clear(binding.imageView1);
    }

}
