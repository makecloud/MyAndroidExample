package com.liuyihui.client.myexample.example2_pullToRefreshListView_demo;

/**
 * 列表项数据实体
 * Created by liuyh on 2016/11/1.
 */

public class Item {
    private String planName;
    private String planPeriod;
    private String planCost;

    public Item(String planName, String planPeriod, String planCost) {
        this.planName = planName;
        this.planPeriod = planPeriod;
        this.planCost = planCost;
    }


    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(String planPeriod) {
        this.planPeriod = planPeriod;
    }

    public String getPlanCost() {
        return planCost;
    }

    public void setPlanCost(String planCost) {
        this.planCost = planCost;
    }
}
