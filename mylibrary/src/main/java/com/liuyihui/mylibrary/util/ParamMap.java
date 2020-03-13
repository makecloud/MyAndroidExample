package com.liuyihui.mylibrary.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 直接使用map来存放和传递参数, 重复写参数名key,如paramMap.put("pageSize",15); 为减少重复写key,
 * Created by liuyi on 2019/4/9.
 */

public class ParamMap extends HashMap<String, Object> {
    private Map<String, Object> storeMap;
    //系统
    private String verifyCode;
    private String phone;
    private String newPassword;
    private String sessionCode;
    //分页
    private Integer pageSize;
    private Integer startIndex;
    //状态类
    private Short workStatus;
    private Short positionStatus;
    private Short putStatus;
    private Short putMode;
    private Short billingMode;
    private Short putType;
    private Short saleType;
    private Boolean isOnline;
    private Short playerStatus;
    private Short netType;
    private Boolean isYunge;
    private Boolean isPass;
    private Short rejectReason;
    //经纬度
    private Double longitude;
    private Double latitude;
    //id类
    private Long cityId;
    private Long positionId;
    private Long planId;
    private String name;
    private Long sceneId;
    private String address;
    private Long groupId;
    private String playerCode;
    private Long areaId;
    private String sceneIdList;
    private Long provinceId;
    private Long districtId;
    private Long companyId;
    private String beginDate;
    private String endDate;
    private String positionIdList;
    private String remark;
    private Short auditStatus;
    private Long screenId;
    private Boolean isBound;
    private Long mediaGroupId;
    private Long messageId;
    private Long resolotionId;
    private Long templetId;
    private Integer playTime;//播放时长
    private Short planType;
    private String screenItems;
    private Short materialFileType;
    private Long customerId;
    private String webUrl;
    private Short commandType;
    private String commondId;
    private Long positionGroupId;
    private Integer width;
    private Integer height;
    private String startTime;
    private String closeTime;


    /**
     * 构造方法
     */
    public ParamMap() {
        this.storeMap = new HashMap<>();
    }

    private ParamMap(Builder builder) {
        setVerifyCode(builder.verifyCode);
        setPhone(builder.phone);
        setNewPassword(builder.newPassword);
        setSessionCode(builder.sessionCode);
        setPageSize(builder.pageSize);
        setStartIndex(builder.startIndex);
        setWorkStatus(builder.workStatus);
        setPositionStatus(builder.positionStatus);
        setPutStatus(builder.putStatus);
        setPutMode(builder.putMode);
        setBillingMode(builder.billingMode);
        setPutType(builder.putType);
        setSaleType(builder.saleType);
        isOnline = builder.isOnline;
        setPlayerStatus(builder.playerStatus);
        setNetType(builder.netType);
        isYunge = builder.isYunge;
        setIsPass(builder.isPass);
        setRejectReason(builder.rejectReason);
        setLongitude(builder.longitude);
        setLatitude(builder.latitude);
        setCityId(builder.cityId);
        setPositionId(builder.positionId);
        setPlanId(builder.planId);
        setName(builder.name);
        setSceneId(builder.sceneId);
        setAddress(builder.address);
        setGroupId(builder.groupId);
        setPlayerCode(builder.playerCode);
        setAreaId(builder.areaId);
        setSceneIdList(builder.sceneIdList);
        setProvinceId(builder.provinceId);
        setDistrictId(builder.districtId);
        setCompanyId(builder.companyId);
        setBeginDate(builder.beginDate);
        setEndDate(builder.endDate);
        setPositionIdList(builder.positionIdList);
        setRemark(builder.remark);
        setAuditStatus(builder.auditStatus);
        setScreenId(builder.screenId);
        setIsBound(builder.isBound);
        setMediaGroupId(builder.mediaGroupId);
        setMessageId(builder.messageId);
        setResolotionId(builder.resolotionId);
        setTempletId(builder.templetId);
        setPlayTime(builder.playTime);
        setPlanType(builder.planType);
    }

    /**
     * set
     *
     * @param paramName
     * @param paramValue
     */
    public void set(String paramName, Object paramValue) {
        this.storeMap.put(paramName, paramValue);
    }

    /**
     * get
     *
     * @param paramName
     * @return
     */
    public Object get(String paramName) {
        return this.storeMap.get(paramName);
    }


    public Map<String, Object> getStoreMap() {
        return storeMap;
    }

    public void setStoreMap(Map<String, Object> storeMap) {
        this.storeMap = storeMap;
    }

    public Short getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(Short positionStatus) {
        this.positionStatus = positionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Short getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Short workStatus) {
        this.workStatus = workStatus;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Short getPutStatus() {
        return putStatus;
    }

    public void setPutStatus(Short putStatus) {
        this.putStatus = putStatus;
    }

    public Short getPutMode() {
        return putMode;
    }

    public void setPutMode(Short putMode) {
        this.putMode = putMode;
    }

    public Short getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(Short billingMode) {
        this.billingMode = billingMode;
    }

    public Short getPutType() {
        return putType;
    }

    public void setPutType(Short putType) {
        this.putType = putType;
    }

    public Short getSaleType() {
        return saleType;
    }

    public void setSaleType(Short saleType) {
        this.saleType = saleType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Short getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(Short playerStatus) {
        this.playerStatus = playerStatus;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public Short getNetType() {
        return netType;
    }

    public void setNetType(Short netType) {
        this.netType = netType;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getSceneIdList() {
        return sceneIdList;
    }

    public void setSceneIdList(String sceneIdList) {
        this.sceneIdList = sceneIdList;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Boolean getYunge() {
        return isYunge;
    }

    public void setYunge(Boolean yunge) {
        isYunge = yunge;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public String getPositionIdList() {
        return positionIdList;
    }

    public void setPositionIdList(String positionIdList) {
        this.positionIdList = positionIdList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public Short getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Short rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public Boolean getIsBound() {
        return isBound;
    }

    public void setIsBound(Boolean isBound) {
        this.isBound = isBound;
    }

    public Long getMediaGroupId() {
        return mediaGroupId;
    }

    public void setMediaGroupId(Long mediaGroupId) {
        this.mediaGroupId = mediaGroupId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getResolotionId() {
        return resolotionId;
    }

    public void setResolotionId(Long resolotionId) {
        this.resolotionId = resolotionId;
    }

    public Long getTempletId() {
        return templetId;
    }

    public void setTempletId(Long templetId) {
        this.templetId = templetId;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Short getPlanType() {
        return planType;
    }

    public void setPlanType(Short planType) {
        this.planType = planType;
    }

    public String getScreenItems() {
        return screenItems;
    }

    public void setScreenItems(String screenItems) {
        this.screenItems = screenItems;
    }

    public Short getMaterialFileType() {
        return materialFileType;
    }

    public void setMaterialFileType(Short materialFileType) {
        this.materialFileType = materialFileType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Short getCommandType() {
        return commandType;
    }

    public void setCommandType(Short commandType) {
        this.commandType = commandType;
    }

    public String getCommondId() {
        return commondId;
    }

    public void setCommondId(String commondId) {
        this.commondId = commondId;
    }

    public Long getPositionGroupId() {
        return positionGroupId;
    }

    public void setPositionGroupId(Long positionGroupId) {
        this.positionGroupId = positionGroupId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }


    public static final class Builder {
        private String verifyCode;
        private String phone;
        private String newPassword;
        private String sessionCode;
        private Integer pageSize;
        private Integer startIndex;
        private Short workStatus;
        private Short positionStatus;
        private Short putStatus;
        private Short putMode;
        private Short billingMode;
        private Short putType;
        private Short saleType;
        private Boolean isOnline;
        private Short playerStatus;
        private Short netType;
        private Boolean isYunge;
        private Boolean isPass;
        private Short rejectReason;
        private Double longitude;
        private Double latitude;
        private Long cityId;
        private Long positionId;
        private Long planId;
        private String name;
        private Long sceneId;
        private String address;
        private Long groupId;
        private String playerCode;
        private Long areaId;
        private String sceneIdList;
        private Long provinceId;
        private Long districtId;
        private Long companyId;
        private String beginDate;
        private String endDate;
        private String positionIdList;
        private String remark;
        private Short auditStatus;
        private Long screenId;
        private Boolean isBound;
        private Long mediaGroupId;
        private Long messageId;
        private Long resolotionId;
        private Long templetId;
        private Integer playTime;
        private Short planType;

        public Builder() {
        }

        public Builder verifyCode(String val) {
            verifyCode = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder newPassword(String val) {
            newPassword = val;
            return this;
        }

        public Builder sessionCode(String val) {
            sessionCode = val;
            return this;
        }

        public Builder pageSize(Integer val) {
            pageSize = val;
            return this;
        }

        public Builder startIndex(Integer val) {
            startIndex = val;
            return this;
        }

        public Builder workStatus(Short val) {
            workStatus = val;
            return this;
        }

        public Builder positionStatus(Short val) {
            positionStatus = val;
            return this;
        }

        public Builder putStatus(Short val) {
            putStatus = val;
            return this;
        }

        public Builder putMode(Short val) {
            putMode = val;
            return this;
        }

        public Builder billingMode(Short val) {
            billingMode = val;
            return this;
        }

        public Builder putType(Short val) {
            putType = val;
            return this;
        }

        public Builder saleType(Short val) {
            saleType = val;
            return this;
        }

        public Builder isOnline(Boolean val) {
            isOnline = val;
            return this;
        }

        public Builder playerStatus(Short val) {
            playerStatus = val;
            return this;
        }

        public Builder netType(Short val) {
            netType = val;
            return this;
        }

        public Builder isYunge(Boolean val) {
            isYunge = val;
            return this;
        }

        public Builder isPass(Boolean val) {
            isPass = val;
            return this;
        }

        public Builder rejectReason(Short val) {
            rejectReason = val;
            return this;
        }

        public Builder longitude(Double val) {
            longitude = val;
            return this;
        }

        public Builder latitude(Double val) {
            latitude = val;
            return this;
        }

        public Builder cityId(Long val) {
            cityId = val;
            return this;
        }

        public Builder positionId(Long val) {
            positionId = val;
            return this;
        }

        public Builder planId(Long val) {
            planId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder sceneId(Long val) {
            sceneId = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder groupId(Long val) {
            groupId = val;
            return this;
        }

        public Builder playerCode(String val) {
            playerCode = val;
            return this;
        }

        public Builder areaId(Long val) {
            areaId = val;
            return this;
        }

        public Builder sceneIdList(String val) {
            sceneIdList = val;
            return this;
        }

        public Builder provinceId(Long val) {
            provinceId = val;
            return this;
        }

        public Builder districtId(Long val) {
            districtId = val;
            return this;
        }

        public Builder companyId(Long val) {
            companyId = val;
            return this;
        }

        public Builder beginDate(String val) {
            beginDate = val;
            return this;
        }

        public Builder endDate(String val) {
            endDate = val;
            return this;
        }

        public Builder positionIdList(String val) {
            positionIdList = val;
            return this;
        }

        public Builder remark(String val) {
            remark = val;
            return this;
        }

        public Builder auditStatus(Short val) {
            auditStatus = val;
            return this;
        }

        public Builder screenId(Long val) {
            screenId = val;
            return this;
        }

        public Builder isBound(Boolean val) {
            isBound = val;
            return this;
        }

        public Builder mediaGroupId(Long val) {
            mediaGroupId = val;
            return this;
        }

        public Builder messageId(Long val) {
            messageId = val;
            return this;
        }

        public Builder resolotionId(Long val) {
            resolotionId = val;
            return this;
        }

        public Builder templetId(Long val) {
            templetId = val;
            return this;
        }

        public Builder playTime(Integer val) {
            playTime = val;
            return this;
        }

        public Builder planType(Short val) {
            planType = val;
            return this;
        }

        public ParamMap build() {
            return new ParamMap(this);
        }
    }
}
