package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 每天消费类Ext
 * <p>
 * BarMonth!=null代表月份条
 *
 * @author liuyh
 */
public class DayConsomeExt extends DayConsome implements Serializable {


    /** 此属性与其他属性互斥地存于一个对象，包含此属性的对象标识一个月份条 */
    private String BarMonth = null;
    private boolean topDivider = false;

    public DayConsomeExt() {
        super();
    }

    public DayConsomeExt(String barMonth) {
        this.BarMonth = barMonth;
    }


    public String getBarMonth() {
        return BarMonth;
    }

    public void setBarMonth(String barMonth) {
        BarMonth = barMonth;
    }

    public boolean hasTopDivider() {
        return topDivider;
    }

    public void setTopDivider(boolean topDivider) {
        this.topDivider = topDivider;
    }

}
