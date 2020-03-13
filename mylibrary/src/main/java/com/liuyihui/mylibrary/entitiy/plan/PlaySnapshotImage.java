package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 播控截屏类
 * 
 * @author yinjy
 *
 */
public class PlaySnapshotImage implements Serializable {

	/** 广告计划标识 */
	private Long planId = null;
	/** 广告计划名称 */
	private String planName = null;
	/** 媒体位标识 */
	private Long positionId = null;
	/** 媒体位名称 */
	private String positionName = null;
	/** 图片文件地址 */
	private String imageUrl = null;
	/** 截屏时间(yyyy-mm-dd hh:MM:ss) */
	private String datetime = null;

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
	 * 获取媒体位标识
	 * 
	 * @return 媒体位标识
	 */
	public Long getPositionId() {
		return positionId;
	}

	/**
	 * 设置媒体位标识
	 * 
	 * @param positionId 媒体位标识
	 */
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	/**
	 * 获取媒体位名称
	 * 
	 * @return 媒体位名称
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * 设置媒体位名称
	 * 
	 * @param positionName 媒体位名称
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * 获取图片文件地址
	 * 
	 * @return 图片文件地址
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置图片文件地址
	 * 
	 * @param imageUrl 图片文件地址
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 获取截屏时间(yyyy-mm-dd hh:MM:ss)
	 * 
	 * @return 截屏时间(yyyy-mm-dd hh:MM:ss)
	 */
	public String getDatetime() {
		return datetime;
	}

	/**
	 * 设置截屏时间(yyyy-mm-dd hh:MM:ss)
	 * 
	 * @param datetime 截屏时间(yyyy-mm-dd hh:MM:ss)
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
