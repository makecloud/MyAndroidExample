package com.liuyihui.networkcontrol.http.entity;

import java.util.List;

/**
 * Created by gaowen on 2017/7/13.
 */

public class PlayerInfo {

    private long positionId;
    private int taskSec;
    private int heartSec;
    private int reportSec;
    private String timeZone;
    private int volume;
    private boolean dspStBar;
    private String pkgVer;
    private String rsvImgVer;
    private boolean dspWthBar;
    private String wthImgVer;
    private String vpnIp;
    private int areaId;
    private String startTime;
    private String closeTime;
    private String serverAddress;
    private boolean reportAdMaster;
    private String adMasterReportUrl;
    private boolean isGetDsp;
    private String channelId;
    private String token;
    private int rotation;
    private boolean isAutoStart = true;


    private boolean isValid = true;
    private boolean autoOnOff;
    private boolean showNavBar;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isAutoOnOff() {
        return autoOnOff;
    }

    public void setAutoOnOff(boolean autoOnOff) {
        this.autoOnOff = autoOnOff;
    }

    public boolean isShowNavBar() {
        return showNavBar;
    }

    public void setShowNavBar(boolean showNavBar) {
        this.showNavBar = showNavBar;
    }

    public boolean isAutoStart() {
        return isAutoStart;
    }

    public void setAutoStart(boolean autoStart) {
        isAutoStart = autoStart;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getTaskSec() {
        return taskSec;
    }

    public void setTaskSec(int taskSec) {
        this.taskSec = taskSec;
    }

    public int getHeartSec() {
        return heartSec;
    }

    public void setHeartSec(int heartSec) {
        this.heartSec = heartSec;
    }

    public int getReportSec() {
        return reportSec;
    }

    public void setReportSec(int reportSec) {
        this.reportSec = reportSec;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getPkgVer() {
        return pkgVer;
    }

    public void setPkgVer(String pkgVer) {
        this.pkgVer = pkgVer;
    }

    public String getWthImgVer() {
        return wthImgVer;
    }

    public void setWthImgVer(String wthImgVer) {
        this.wthImgVer = wthImgVer;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getAdMasterReportUrl() {
        return adMasterReportUrl;
    }

    public void setAdMasterReportUrl(String adMasterReportUrl) {
        this.adMasterReportUrl = adMasterReportUrl;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public long getPositionId() {
        return this.positionId;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setDspStBar(boolean dspStBar) {
        this.dspStBar = dspStBar;
    }

    public boolean getDspStBar() {
        return this.dspStBar;
    }

    public void setRsvImgVer(String rsvImgVer) {
        this.rsvImgVer = rsvImgVer;
    }

    public String getRsvImgVer() {
        return this.rsvImgVer;
    }

    public void setDspWthBar(boolean dspWthBar) {
        this.dspWthBar = dspWthBar;
    }

    public boolean getDspWthBar() {
        return this.dspWthBar;
    }

    public void setVpnIp(String vpnIp) {
        this.vpnIp = vpnIp;
    }

    public String getVpnIp() {
        return this.vpnIp;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getAreaId() {
        return this.areaId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseTime() {
        return this.closeTime;
    }

    public void setReportAdMaster(boolean reportAdMaster) {
        this.reportAdMaster = reportAdMaster;
    }

    public boolean getReportAdMaster() {
        return this.reportAdMaster;
    }

    public void setIsGetDsp(boolean isGetDsp) {
        this.isGetDsp = isGetDsp;
    }

    public boolean getIsGetDsp() {
        return this.isGetDsp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
