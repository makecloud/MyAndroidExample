package com.liuyihui.viewpager.example;

/**
 * Created by liuyi on 2018/9/5.
 */

public class MaterialData {
    private int type;
    private String url;

    public MaterialData(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
