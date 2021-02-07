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

    public DataRepository() {
    }

    private static class InstanceHolder {
        private static DataRepository instance = new DataRepository();
    }

    public static DataRepository getInstance() {
        return InstanceHolder.instance;
    }

    public DownInfo getDownInfoFromDB(String matMD5) {
        String a = SharedPreferencesUtils.getInstance(MyApplication.context).getString(matMD5);
        return JSON.parseObject(a, DownInfo.class);
    }

    public void reportAlertInfo(int i, String matUrl) {
        Log.e(TAG, String.format("reportAlertInfo: %s,%s", i, matUrl));
    }

    public void saveDownInfoToDB(DownInfo downInfo) {
        String a = JSON.toJSONString(downInfo);
        SharedPreferencesUtils.getInstance(MyApplication.context).put(downInfo.getFileMd5(), a);
    }

    public void deleteDownInfoToDB(DownInfo downInfo) {
        SharedPreferencesUtils.getInstance(MyApplication.context).remove(downInfo.getFileMd5());
    }

    public static void main(String[] args) {
        DownInfo downInfo = new DownInfo();
        downInfo.setContentType("dfa");
        downInfo.setFileMd5("4j1h5234hk");
        downInfo.setFileUrl("http:fjdks.com");
        downInfo.setType(DownInfo.FileType.SCREEN_MATERIAL);

        String a = JSON.toJSONString(downInfo);

        System.out.println(a);
    }
}
