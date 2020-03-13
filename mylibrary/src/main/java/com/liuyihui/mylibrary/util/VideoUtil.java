package com.liuyihui.mylibrary.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.util.Hashtable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 视频处理工具类
 * <p>
 * Created by liuyi on 2018/12/14.
 */

public class VideoUtil {
    private static final String TAG = "VideoUtil";

    /**
     * 获取本地视频文件第一帧图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */

    public static Bitmap getLocalVideoThumb(String path) {
        LogUtil.i(TAG, path);
        try {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            return media.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            LogUtil.e(TAG, "获取视频缩略图出错", e);
            return null;
        }
    }

    /**
     * 获取网络，本地。。视频第一帧图片
     * <p>
     * 异步加载
     *
     * @param filePath 网路视频url,本地文件...都行
     * @param kind     MediaStore.Images.Thumbnails.MINI_KIND 固定
     * @return
     */
    public static Observable<Bitmap> createVideoThumbnail(final String filePath, final int kind) {
        LogUtil.i(TAG, filePath);
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    if (filePath.startsWith("http://") || filePath.startsWith("https://") || filePath
                            .startsWith("widevine://")) {
                        retriever.setDataSource(filePath, new Hashtable<String, String>());
                    } else {
                        retriever.setDataSource(filePath);
                    }
                    bitmap = retriever.getFrameAtTime(0,
                                                      MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
                } catch (IllegalArgumentException ex) {
                    // Assume this is a corrupt video file
                    ex.printStackTrace();
                } catch (RuntimeException ex) {
                    // Assume this is a corrupt video file.
                    ex.printStackTrace();
                } finally {
                    try {
                        retriever.release();
                    } catch (RuntimeException ex) {
                        // Ignore failures while cleaning up.
                        ex.printStackTrace();
                    }
                }

                if (bitmap == null) {
                    subscriber.onError(new Throwable("get bitmap null !"));
                    //return null;
                }

                if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
                    // Scale down the bitmap if it's too large.
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int max = Math.max(width, height);
                    if (max > 512) {
                        float scale = 512f / max;
                        int w = Math.round(scale * width);
                        int h = Math.round(scale * height);
                        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
                    }//压缩图片 结束处
                } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                                                             96,
                                                             96,
                                                             ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                }
                subscriber.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
}
