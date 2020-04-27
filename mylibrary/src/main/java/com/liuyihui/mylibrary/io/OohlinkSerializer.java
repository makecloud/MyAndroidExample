package com.liuyihui.mylibrary.io;

import android.content.Context;
import android.os.Environment;

import com.liuyihui.mylibrary.exception.OohlinkException;
import com.liuyihui.mylibrary.util.DesUtil;
import com.liuyihui.mylibrary.util.LogUtil;

import java.io.File;
import java.io.IOException;

/**
 * 序列化工具类
 * Created by liuyh on 2016/10/26.
 */

public class OohlinkSerializer {
    private static String TAG = "OohlinkSerializer";


    public OohlinkSerializer() {
    }

    /**
     * 加密字符串
     * （在这里包装个加密方法，将来换加密方式直接改这个方法内代码
     * 如果加密代码写到以下业务方法内，换加密方式得去各个业务方法里找哪里用了加密
     * ）
     *
     * @param srcStr 明文字符串
     * @return 加密后字符串（加密失败返回""）
     */
    private static String encrypt(String srcStr) throws OohlinkException {
        try {
            return DesUtil.encrypt(srcStr);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("加密出错");
        }
    }

    /**
     * 解密字符串
     *
     * @param resStr 密文字符串
     * @return
     */
    private static String decryptStr(String resStr) throws OohlinkException {
        try {
            return DesUtil.decrypt(resStr);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("解密出错");
        }

    }

    /**
     * 获取SD卡上的本应用文件夹，如不存在则创建
     *
     * @return /storage/emulated/0/com.yunge.client.media/
     * @throws IOException 获取SD卡下应用目录为空
     */
    public static String getAppSDPath(Context context) throws IOException {
        String appPathOnSdCard = null;
        //判断外部存储可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取SD根目录
            String SDRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            //获取SD根目录下/com.yunge.client.media文件夹，如不存在则创建
            File appDir = new File(SDRootPath + "/" + context.getPackageName());
            if (!appDir.exists() || !appDir.isDirectory()) {
                appDir.mkdir();
            }
            appPathOnSdCard = appDir.getAbsolutePath() + "/";
            LogUtil.d(TAG, "get app path on sdcard：" + appPathOnSdCard);
        }
        if (appPathOnSdCard == null) {
            throw new IOException("获取SD卡下应用目录为空");
        }
        return appPathOnSdCard;
    }

}
