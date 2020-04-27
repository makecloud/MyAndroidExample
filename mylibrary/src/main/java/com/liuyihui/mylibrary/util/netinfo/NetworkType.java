package com.liuyihui.mylibrary.util.netinfo;

public enum NetworkType {

        UNKNOWN(0), WIFI(100), NETWORK_4G(4), NETWORK_3G(3), NETWORK_2G(2), ETHERNET(101);
        private int mValue;

        NetworkType(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }