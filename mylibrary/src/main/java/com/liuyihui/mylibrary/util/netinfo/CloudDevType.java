package com.liuyihui.mylibrary.util.netinfo;

public enum CloudDevType {

    UNKNOWN(0), WIFI(100), MOBILE(1), ETHERNET(101), VPN(200);

    private int mValue;

    CloudDevType(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}