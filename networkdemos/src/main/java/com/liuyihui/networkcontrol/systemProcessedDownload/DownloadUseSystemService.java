package com.liuyihui.networkcontrol.systemProcessedDownload;


import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.TransitionRes;

import com.liuyihui.networkcontrol.MyApplication;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 使用系统自带的下载服务下载文件
 */
public class DownloadUseSystemService {
    private final String TAG = getClass().getSimpleName();

    private static class InstanceHolder {
        private static DownloadUseSystemService downloadUseSystemService =
                new DownloadUseSystemService();
    }

    private DownloadUseSystemService() {

    }

    public static DownloadUseSystemService getInstance() {
        return InstanceHolder.downloadUseSystemService;
    }

    public void downloadFile() {
        DownloadManager downloadManager = (DownloadManager) MyApplication.context.getSystemService(
                DOWNLOAD_SERVICE);

        String url = "https://ygsd-test.oss-cn-beijing.aliyuncs" + ".com/material/40" +
                "/51AECDE7D6C5C65FD5100DA995837E06.mp4";
        String url2 = "https://res.oohlink.com/play.apk?v=1594611006664";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url2));
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        long id = downloadManager.enqueue(request);


        /*DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor downing = downloadManager.query(query);
        downing.moveToFirst();*/



        // TODO: 2020-05-19
    }


}
