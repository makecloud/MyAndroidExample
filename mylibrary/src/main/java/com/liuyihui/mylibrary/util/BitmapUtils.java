package com.liuyihui.mylibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gaowen on 2017/8/18.
 */

public class BitmapUtils {

    public static Bitmap drawText(Bitmap src, String msg, int color, int textSize) {

        Canvas canvas = new Canvas(src);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setFakeBoldText(true);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseX = 0;
        float baseY = src.getHeight() - fontMetrics.bottom;

        canvas.drawText(msg, baseX, baseY, paint);
        canvas.save();
        canvas.restore();

        return src;
    }

    public static Bitmap decodeBigImage(Context context, int res) {
        // 解析分辨率大的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), res, options);
        Log.d("Welcome", "with : " + options.outWidth + " height: " + options.outHeight);
        options.inSampleSize = computeSampleSize(options, -1, 1024 * 1800);
        options.inJustDecodeBounds = false;

        try {
            return BitmapFactory.decodeResource(context.getResources(), res, options);
        } catch (OutOfMemoryError err) {
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength,
                                        int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength,
                                                int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                                                                      Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * bitmap保存到文件
     */
    public static String writeBitmapToFile(Bitmap pBitmap,
                                           String saveName,
                                           String savePath,
                                           int quality) {
        return writeBitmapToFile(pBitmap, saveName, savePath, quality, Bitmap.CompressFormat.JPEG);

    }

    public static String writeBitmapToFile(Bitmap pBitmap,
                                           String saveName,
                                           String savePath,
                                           int quality,
                                           Bitmap.CompressFormat compressFormat) {

        File file = new File(savePath, saveName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            pBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
