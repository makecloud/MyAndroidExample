package com.liuyihui.mylibrary.entitiy.plan;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 广告计划摘要类
 *
 * @author yinjy
 */
public class PlanAbstract implements Serializable {

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
    /** 素材url */
    private String materialUrl = null;
    /** 素材图 */
    private Bitmap materialThumb = null;

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
     * 获取素材url
     *
     * @return 素材url
     */
    public String getMaterialUrl() {
        return materialUrl;
    }

    /**
     * 设置素材url
     *
     * @param materialUrl 素材url
     */
    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    /**
     * 获取素材缩略图
     *
     * @return bitmap位图
     */
    public Bitmap getMaterialThumb() {
        return materialThumb;
    }

    /**
     * 设置素材缩略图
     *
     * @param materialThumb 缩略图
     */
    public void setMaterialThumb(Bitmap materialThumb) {
        this.materialThumb = materialThumb;
    }

}
