package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 广告计划实时投放记录类
 * 
 * @author yinjy
 * 
 */
public class RealtimePutRecord implements Serializable {

	/** 实时投放标识 */
	private Long id = null;
	/** 广告计划标识 */
	private Long planId = null;
	/** 广告计划名称 */
	private String planName = null;
	/** 开始日期 */
	private String beginDate = null;
	/** 结束日期 */
	private String endDate  = null;
	/** 小时 */
	private String hours = null;
	/** 是否有效 */
	private Boolean isEnable = null;
	/** 每天消费上限(元) */
	private Integer maxConsume = null;
	/** 每天单个媒体位最大播放次数 */
	private Integer maxTimes = null;
	/** 创建时间 */
	private String createdTime = null;
	/** 修改时间 */
	private String modifiedTime = null;

	/**
	 * 获取实时投放标识
	 * 
	 * @return 实时投放标识
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置实时投放标识
	 * 
	 * @param id 实时投放标识
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取广告计划标识
	 * 
	 * @return 广告计划标识
	 */
	public Long getPlanId() {
		return planId;
	}

	/**
	 * 设置广告计划标识
	 * 
	 * @param planId 广告计划标识
	 */
	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	/**
	 * 获取广告计划名称
	 * 
	 * @return 广告计划名称
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * 设置广告计划名称
	 * 
	 * @param planName 广告计划名称
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * 获取开始日期
	 * 
	 * @return 开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置开始日期
	 * 
	 * @param beginDate 开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取结束日期
	 * 
	 * @return 结束日期
	 */
	public String getEndDate () {
		return endDate ;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate  结束日期
	 */
	public void setEndDate (String endDate ) {
		this.endDate  = endDate ;
	}

	/**
	 * 获取小时
	 * 
	 * @return 小时
	 */
	public String getHours() {
		return hours;
	}

	/**
	 * 设置小时
	 * 
	 * @param hours 小时
	 */
	public void setHours(String hours) {
		this.hours = hours;
	}

	/**
	 * 获取是否有效
	 * 
	 * @return 是否有效
	 */
	public Boolean getIsEnable() {
		return isEnable;
	}

	/**
	 * 设置是否有效
	 * 
	 * @param isEnable 是否有效
	 */
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * 获取每天消费上限(元)
	 * 
	 * @return 每天消费上限(元)
	 */
	public Integer getMaxConsume() {
		return maxConsume;
	}

	/**
	 * 设置每天消费上限(元)
	 * 
	 * @param maxConsume 每天消费上限(元)
	 */
	public void setMaxConsume(Integer maxConsume) {
		this.maxConsume = maxConsume;
	}

	/**
	 * 获取每天单个媒体位最大播放次数
	 * 
	 * @return 每天单个媒体位最大播放次数
	 */
	public Integer getMaxTimes() {
		return maxTimes;
	}

	/**
	 * 设置每天单个媒体位最大播放次数
	 * 
	 * @param maxTimes 每天单个媒体位最大播放次数
	 */
	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return 创建时间
	 */
	public String getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createdTime 创建时间
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取修改时间
	 * 
	 * @return 修改时间
	 */
	public String getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * 设置修改时间
	 * 
	 * @param modifiedTime 修改时间
	 */
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
