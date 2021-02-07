package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by gaowen on 2017/7/12.
 */

public class DownInfo {


    @JSONField
    private String fileUrl;
    /**
     * 文件自身大小。
     * <p>
     * 注意：被赋值为首次得到http响应体的contentLength
     */
    @JSONField
    private long fileSize;
    @JSONField
    private String fileMd5;
    /**
     * 已经累计下载的字节数量。
     * 解析：包含每次断点续传的字节的累计
     */
    @JSONField
    private long readSize;
    @JSONField
    private int downloadState;
    @JSONField
    private String savePathName;
    @JSONField
    private FileType type;
    @JSONField
    private String contentType;
    /**
     * 记录本次下载的字节。
     * 解析：如果没有产生再次断点续传下载，则为从文件的第0个字节累计，如果产生再次断点续传，则为从续传位置累计的字节
     */
    @JSONField
    private long downloadSize;

    private DownLoadingListener downLoadingListener;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public long getReadSize() {
        return readSize;
    }

    public void setReadSize(long readSize) {
        this.readSize = readSize;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public String getSavePathName() {
        return savePathName;
    }

    public void setSavePathName(String savePathName) {
        this.savePathName = savePathName;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDownLoadingListener(DownLoadingListener downLoadingListener) {
        this.downLoadingListener = downLoadingListener;
    }

    public void downLoadCompleted() {
        if (downLoadingListener != null) {
            downLoadingListener.onCompleted();
        }
    }

    public void onDownLoadProgress() {
        if (downLoadingListener != null) {
            downLoadingListener.onProgress(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DownInfo downInfo = (DownInfo) o;

        return fileUrl.equals(downInfo.fileUrl);
    }

    @Override
    public int hashCode() {
        return fileUrl.hashCode();
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public DownLoadingListener getDownLoadingListener() {
        return downLoadingListener;
    }

    public enum FileType {

        UNKNOWN,

        APK,

        VPN,

        SCREEN_MATERIAL,

        EMERGENT_MATERIAL,

        WEATHER_IMAGE,

        REVERSE_MATERIAL
    }

    public interface DownLoadingListener {
        void onCompleted();

        void onProgress(DownInfo downInfo);
    }
}
