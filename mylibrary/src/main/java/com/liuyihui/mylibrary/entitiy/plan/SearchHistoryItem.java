package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 搜索历史记录项实体类
 * Created by liuyh on 2016/11/14.
 */

public class SearchHistoryItem implements Serializable {
    /** 名称 */
    private String name;

    /**
     * 默认构造方法
     */
    public SearchHistoryItem() {
    }

    public SearchHistoryItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
