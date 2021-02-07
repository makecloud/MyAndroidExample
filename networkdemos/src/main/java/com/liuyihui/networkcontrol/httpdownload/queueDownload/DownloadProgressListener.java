package com.liuyihui.networkcontrol.httpdownload.queueDownload;

/**
 * Created by gaowen on 2017/7/17.
 */

public interface DownloadProgressListener {
    /**
     * 下载进度
     * @param bytesRead
     * @param contentLength
     * @param done
     */
    void onUpdate(long bytesRead, long contentLength, boolean done);
}
