package com.liuyihui.wifip2pdemo;

import java.io.Serializable;

public class SendInfo implements Serializable {

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name + ":" + value;
    }
}
