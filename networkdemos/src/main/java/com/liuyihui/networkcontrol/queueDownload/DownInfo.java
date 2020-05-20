package com.liuyihui.networkcontrol.queueDownload;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by gaowen on 2017/7/12.
 */

public class DownInfo {
    @JSONField
    private String matUrl;
    @JSONField
    private long matSize;
    @JSONField
    private String matMd5;
    @JSONField
    private long readSize;
    @JSONField
    private int downloadState;
    @JSONField
    private String savePathName;
    @JSONField
    private FileType type;
    @JSONField
    private String conetntType;
    @JSONField
    private long downloadSize;
    private DownLoadCompletedListener downLoadCompletedListener;

    public String getMatUrl() {
        return matUrl;
    }

    public void setMatUrl(String matUrl) {
        this.matUrl = matUrl;
    }

    public long getMatSize() {
        return matSize;
    }

    public void setMatSize(long matSize) {
        this.matSize = matSize;
    }

    public String getMatMd5() {
        return matMd5;
    }

    public void setMatMd5(String matMd5) {
        this.matMd5 = matMd5;
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

    public String getConetntType() {
        return conetntType;
    }

    public void setConetntType(String conetntType) {
        this.conetntType = conetntType;
    }

    public void setDownLoadCompletedListener(DownLoadCompletedListener downLoadCompletedListener) {
        this.downLoadCompletedListener = downLoadCompletedListener;
    }

    public void downLoadCompleted() {
        if (downLoadCompletedListener != null) {
            downLoadCompletedListener.onCompleted();
        }
    }

    public void downLoadProgress() {
        if (downLoadCompletedListener != null) {
            downLoadCompletedListener.onProgress(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownInfo downInfo = (DownInfo) o;

        return matUrl.equals(downInfo.matUrl);
    }

    @Override
    public int hashCode() {
        return matUrl.hashCode();
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public DownLoadCompletedListener getDownLoadCompletedListener() {
        return downLoadCompletedListener;
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

    public interface DownLoadCompletedListener {
        void onCompleted();
        void onProgress(DownInfo downInfo);
    }
}
