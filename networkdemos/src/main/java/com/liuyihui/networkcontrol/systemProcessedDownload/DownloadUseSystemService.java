package com.liuyihui.networkcontrol.systemProcessedDownload;


import android.app.DownloadManager;
import android.net.Uri;

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

        String url = "";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        downloadManager.enqueue(request);
        // TODO: 2020-05-19
    }


}
