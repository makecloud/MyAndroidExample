package com.liuyihui.networkcontrol.queueDownload;

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
    void update(long bytesRead, long contentLength, boolean done);
}
