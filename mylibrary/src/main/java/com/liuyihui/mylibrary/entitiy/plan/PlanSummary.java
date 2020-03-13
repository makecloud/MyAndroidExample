package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 获取应用版本数据类
 * 
 * @author yinjy
 *
 */
public class PlanSummary implements Serializable {

	/** 投放中广告数量 */
	private Integer puttingNum = null;
	/** 已投放广告数量 */
	private Integer putNum = null;
	/** 待投放广告数量 */
	private Integer unputNum = null;
	/** 审核中广告数量 */
	private Integer auditingNum = null;
	/** 待上刊广告数量 */
	private Integer publishNum = null;

	/**
	 * 获取投放中广告数量
	 * 
	 * @return 投放中广告数量
	 */
	public Integer getPuttingNum() {
		return puttingNum;
	}

	/**
	 * 设置投放中广告数量
	 * 
	 * @param puttingNum 投放中广告数量
	 */
	public void setPuttingNum(Integer puttingNum) {
		this.puttingNum = puttingNum;
	}

	/**
	 * 获取已投放广告数量
	 * 
	 * @return 已投放广告数量
	 */
	public Integer getPutNum() {
		return putNum;
	}

	/**
	 * 设置已投放广告数量
	 * 
	 * @param putNum 已投放广告数量
	 */
	public void setPutNum(Integer putNum) {
		this.putNum = putNum;
	}

	/**
	 * 获取待投放广告数量
	 * 
	 * @return 待投放广告数量
	 */
	public Integer getUnputNum() {
		return unputNum;
	}

	/**
	 * 设置待投放广告数量
	 * 
	 * @param unputNum 待投放广告数量
	 */
	public void setUnputNum(Integer unputNum) {
		this.unputNum = unputNum;
	}

	/**
	 * 获取审核中广告数量
	 * 
	 * @return 审核中广告数量
	 */
	public Integer getAuditingNum() {
		return auditingNum;
	}

	/**
	 * 设置审核中广告数量
	 * 
	 * @param auditingNum 审核中广告数量
	 */
	public void setAuditingNum(Integer auditingNum) {
		this.auditingNum = auditingNum;
	}
	
	/**
	 * 设置 待上刊广告数量
	 * 
	 * @return  待上刊广告数量
	 */
	public Integer getPublishNum() {
		return publishNum;
	}

	/**
	 * 获取 待上刊广告数量
	 * 
	 * @param publishNum  待上刊广告数量
	 */
	public void setPublishNum(Integer publishNum) {
		this.publishNum = publishNum;
	}
}
