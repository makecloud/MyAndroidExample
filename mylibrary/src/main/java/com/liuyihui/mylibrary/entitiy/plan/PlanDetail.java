package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 广告计划详情类
 * 
 * @author yinjy
 * 
 */
public class PlanDetail implements Serializable {

	/** 广告计划标识 */
	private Long id = null;
	/** 广告计划名称 */
	private String planName = null;
	/** 投放开始日期 */
	private String beginDate = null;
	/** 投放结束日期 */
	private String endDate = null;
	/** 计费形式(1:CPT; 2:CPM; 3:CPF) */
	private String saleType = null;
	/** 总预算 */
	private Double budget = null;
	/** 广告花费 */
	private Double consume = null;
	/** 广告计划状态 */
	private String status = null;
	/** 是否为平台广告 */
	private Boolean isYunge = null;
	/** 投放方式(1:非策略投放; 2:策略投放; 3:实时插播) */
	private Short putType = null;
	/** 数据分析级别(0:不分析; 1:浅度分析; 2:深度分析) */
	private Short dataLevel = null;
	/** 广告场景列表 */
	private String sceneList = null;
	/** 广告地区列表 */
	private String districtList = null;
	/** 媒体位数量 */
	private Integer positionCount = null;

	/**
	 * 获取广告计划标识
	 *
	 * @return 广告计划标识
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置广告计划标识
	 *
	 * @param id 广告计划标识
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * 获取投放开始日期
	 *
	 * @return 投放开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置投放开始日期
	 *
	 * @param beginDate 投放开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取投放结束日期
	 *
	 * @return 投放结束日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置投放结束日期
	 *
	 * @param endDate 投放结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取计费形式(1:CPT; 2:CPM; 3:CPF)
	 *
	 * @return 计费形式(1:CPT; 2:CPM; 3:CPF)
	 */
	public String getSaleType() {
		return saleType;
	}

	/**
	 * 设置计费形式(1:CPT; 2:CPM; 3:CPF)
	 *
	 * @param saleType 计费形式(1:CPT; 2:CPM; 3:CPF)
	 */
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	/**
	 * 获取总预算
	 *
	 * @return 总预算
	 */
	public Double getBudget() {
		return budget;
	}

	/**
	 * 设置总预算
	 *
	 * @param budget 总预算
	 */
	public void setBudget(Double budget) {
		this.budget = budget;
	}

	/**
	 * 获取广告花费
	 *
	 * @return 广告花费
	 */
	public Double getConsume() {
		return consume;
	}

	/**
	 * 设置广告花费
	 *
	 * @param consume 广告花费
	 */
	public void setConsume(Double consume) {
		this.consume = consume;
	}

	/**
	 * 获取广告计划状态
	 *
	 * @return 广告计划状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置广告计划状态
	 *
	 * @param status 广告计划状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取是否为平台广告
	 *
	 * @return 是否为平台广告
	 */
	public Boolean getIsYunge() {
		return isYunge;
	}

	/**
	 * 设置是否为平台广告
	 *
	 * @param isYunge 是否为平台广告
	 */
	public void setIsYunge(Boolean isYunge) {
		this.isYunge = isYunge;
	}

	/**
	 * 获取投放方式(1:非策略投放; 2:策略投放; 3:实时插播)
	 *
	 * @return 投放方式(1:非策略投放; 2:策略投放; 3:实时插播)
	 */
	public Short getPutType() {
		return putType;
	}

	/**
	 * 设置投放方式(1:非策略投放; 2:策略投放; 3:实时插播)
	 *
	 * @param putType 投放方式(1:非策略投放; 2:策略投放; 3:实时插播)
	 */
	public void setPutType(Short putType) {
		this.putType = putType;
	}

	/**
	 * 获取数据分析级别(0:不分析; 1:浅度分析; 2:深度分析)
	 *
	 * @return 数据分析级别(0:不分析; 1:浅度分析; 2:深度分析)
	 */
	public Short getDataLevel() {
		return dataLevel;
	}

	/**
	 * 设置数据分析级别(0:不分析; 1:浅度分析; 2:深度分析)
	 *
	 * @param dataLevel 数据分析级别(0:不分析; 1:浅度分析; 2:深度分析)
	 */
	public void setDataLevel(Short dataLevel) {
		this.dataLevel = dataLevel;
	}

	/**
	 * 获取广告场景列表
	 *
	 * @return 广告场景列表
	 */
	public String getSceneList() {
		return sceneList;
	}

	/**
	 * 设置广告场景列表
	 *
	 * @param sceneList 广告场景列表
	 */
	public void setSceneList(String sceneList) {
		this.sceneList = sceneList;
	}

	/**
	 * 获取广告地区列表
	 *
	 * @return 广告地区列表
	 */
	public String getDistrictList() {
		return districtList;
	}

	/**
	 * 设置广告地区列表
	 *
	 * @param districtList 广告地区列表
	 */
	public void setDistrictList(String districtList) {
		this.districtList = districtList;
	}

	/**
	 * 获取媒体位数量
	 *
	 * @return 媒体位数量
	 */
	public Integer getPositionCount() {
		return positionCount;
	}

	/**
	 * 设置媒体位数量
	 *
	 * @param positionCount 媒体位数量
	 */
	public void setPositionCount(Integer positionCount) {
		this.positionCount = positionCount;
	}

}
