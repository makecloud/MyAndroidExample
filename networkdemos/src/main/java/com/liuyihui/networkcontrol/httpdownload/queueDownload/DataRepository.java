package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.liuyihui.mylibrary.util.SharedPreferencesUtils;
import com.liuyihui.networkcontrol.MyApplication;

/**
 * 断点续传下载使用的数据库
 */
public class DataRepository {
    private static final String TAG = "DataRepository";

    public DownInfo getDownInfoFromDB(String matMD5) {
        String a = SharedPreferencesUtils.getInstance(MyApplication.context).getString(matMD5);
        return JSON.parseObject(a, DownInfo.class);
    }

    public void reportAlertInfo(int i, String matUrl) {
        Log.e(TAG, "reportAlertInfo: " + i + matUrl);
    }

    public void saveDownInfoToDB(DownInfo downInfo) {
        String a = JSON.toJSONString(downInfo);
        SharedPreferencesUtils.getInstance(MyApplication.context).put(downInfo.getMatMd5(), a);
    }

    public void deleteDownInfoToDB(DownInfo downInfo) {
        SharedPreferencesUtils.getInstance(MyApplication.context).remove(downInfo.getMatMd5());
    }

    public static void main(String[] args) {
        DownInfo downInfo = new DownInfo();
        downInfo.setConetntType("dfa");
        downInfo.setMatMd5("4j1h5234hk");
        downInfo.setMatUrl("http:fjdks.com");
        downInfo.setType(DownInfo.FileType.SCREEN_MATERIAL);

        String a = JSON.toJSONString(downInfo);

        System.out.println(a);
    }
}
