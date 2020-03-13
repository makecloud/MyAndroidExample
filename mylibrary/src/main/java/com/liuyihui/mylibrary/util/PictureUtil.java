package com.liuyihui.mylibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片操作工具类
 * Created by liuyh on 2017/2/14.
 */

public class PictureUtil {
    private static final String TAG = "cloud.PictureUtil";

    /**
     * for test
     *
     * @param args
     */
    public static void main(String[] args) {
        String originPicturePath = "D:\\TU\\IMG_0775.jpg";
        String targetPath = "D:\\TU\\temp\\picture\\IMG_0775_compressed.jpg";
        compressImage(originPicturePath, targetPath, 70);
    }


    /**
     * 压缩过程会移除照片的rotation
     *
     * @param filePath
     * @param targetPath
     * @param quality
     * @return
     */
    public static String compressImageWithRemoveRotation(String filePath, String targetPath,
                                                         int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bitmap = rotateBitmap(bitmap, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();//创建上层目录
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return outputFile.getPath();
    }

    /**
     * 压缩图片
     *
     * @param filePath   原始图片路径
     * @param targetPath 目标图片路径
     * @param quality    压缩质量
     * @return
     */
    public static String compressImage(String filePath, String targetPath, int quality) {
        Bitmap targetBitmap = getSmallBitmapFromFile(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            targetBitmap = rotateBitmap(targetBitmap, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();//创建上层目录
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            targetBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return outputFile.getPath();
    }

    /**
     * 压缩图片+打水印
     *
     * @param filePath   原始图片路径
     * @param targetPath 目标图片路径
     * @param quality    压缩质量
     * @return
     */
    public static String compressImageAndWaterMask(String filePath, String targetPath, int quality,
                                                   Context context, String text) {
        Bitmap bm = getSmallBitmapFromFile(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        //打水印
        bm = ImageWaterMaskUtil.drawTextToRightBottom(context, bm, text, 12, Color.WHITE, 8, 8);

        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();//创建上层目录
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmapFromFile(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                            ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap,
                                         0,
                                         0,
                                         bitmap.getWidth(),
                                         bitmap.getHeight(),
                                         m,
                                         true);
            return bitmap;
        }
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
