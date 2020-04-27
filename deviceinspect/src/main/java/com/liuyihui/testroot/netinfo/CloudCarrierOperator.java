package com.liuyihui.testroot.netinfo;

public enum CloudCarrierOperator {

        UNKNOWN(""), CMCC("46000"), CUCC("46001"), CTCC("46003");

        private String mValue;

        CloudCarrierOperator(String value) {
            mValue = value;
        }

        public String getValue() {
            return mValue;
        }
    }