package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 每天消费类
 *
 * @author yinjy
 */
public class DayConsome implements Serializable {

    /** 日期(yyyy-mm-dd) */
    private String date = null;
    /** 广告消耗(元) */
    private Double consume = null;


    /**
     * 获取日期(yyyy-mm-dd)
     *
     * @return 日期(yyyy-mm-dd)
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置日期(yyyy-mm-dd)
     *
     * @param date 日期(yyyy-mm-dd)
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取广告消耗(元)
     *
     * @return 广告消耗(元)
     */
    public Double getConsume() {
        return consume;
    }

    /**
     * 设置广告消耗(元)
     *
     * @param consume 广告消耗(元)
     */
    public void setConsume(Double consume) {
        this.consume = consume;
    }

}
