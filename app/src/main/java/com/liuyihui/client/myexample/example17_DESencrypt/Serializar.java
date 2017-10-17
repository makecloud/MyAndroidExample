package com.liuyihui.client.myexample.example17_DESencrypt;

import android.os.Environment;
import android.util.Log;

import com.liuyihui.client.myexample.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by liuyh on 2017/4/5.
 */

public class Serializar {
    private static final String TAG = "Serializar";

    public static String loadFileToStr(String filename) {
        String fileContentStr = "";
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(getAppSDPath() + filename));
            fileContentStr = YungeIoUtil.getStrFromInputStreamReader(isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContentStr;
    }

    /**
     * 序列化json字符串到SD卡下/YungeAdvertiser/下指定文件
     * @param jsonstr json串
     * @param filename 要存到的文件名(不含路径)
     */
    public static void serializeJsonStrToFilename(String jsonstr, String filename) {
        OutputStream out;
        Writer writer = null;
        try {
            out = new FileOutputStream(getAppSDPath() + filename);
            writer = new OutputStreamWriter(out);
            writer.write(jsonstr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取SD卡上的本应用文件夹，如不存在则创建
     * @return /storage/emulated/0/YungeAdvertiser/
     * @throws IOException 获取SD卡下应用目录为空
     */
    public static String getAppSDPath() throws IOException {
        String SDPath = null;
//        context.getApplicationContext().getExternalCacheDir().getAbsolutePath();
        //判断外部存储可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取SD根目录
            String SDRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            //String SDRootPath = Environment.getDataDirectory().getAbsolutePath();
            //String SDRootPath = context.getApplicationContext().getExternalCacheDir().getAbsolutePath();
            //获取SD根目录下/YungeAdvertiser文件夹，如不存在则创建
//            File appDir = new File(SDRootPath + "/YungeClient") ;//原始
            File appDir = new File(SDRootPath + "/" + MyApplication.getContextObject().getPackageName());//两个app分路径
            if (!appDir.exists() || !appDir.isDirectory()) {
                appDir.mkdir();
            }
            SDPath = appDir.getAbsolutePath() + "/";
            Log.i(TAG, "yunge get sdpath：" + SDPath);
            //尝试写入外部存储
            try {
                File f = new File(SDPath + "test.txt");
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(2);
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "external storage invaliable !");
            }
        }
        if (SDPath == null) {
            throw new IOException("获取SD卡下应用目录为空");
        }
        return SDPath;
    }
}
