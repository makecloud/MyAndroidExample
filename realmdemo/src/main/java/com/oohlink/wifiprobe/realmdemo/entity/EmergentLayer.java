package com.oohlink.wifiprobe.realmdemo.entity;

/**
 * 紧急插播对象
 */

public class EmergentLayer {

    private long id;
    private long materialId;
    private int materialType;
    private String materialUrl;
    private String materialMd5;
    private int materialSize;
    private String beginDate;
    private String endDate;
    private String beginTime;
    private String endTime;
    private boolean isEnable;
    private String contentType;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMaterialMd5() {
        return materialMd5;
    }

    public void setMaterialMd5(String materialMd5) {
        this.materialMd5 = materialMd5;
    }

    public int getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(int materialSize) {
        this.materialSize = materialSize;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
