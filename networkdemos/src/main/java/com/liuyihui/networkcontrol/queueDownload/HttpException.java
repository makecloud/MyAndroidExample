package com.liuyihui.networkcontrol.queueDownload;

/**
 * Created by gaowen on 2017/7/6.
 */

public class HttpException extends RuntimeException {

    private int mResultCode;

    public HttpException(String detailMessage , int resultCode) {
        super(detailMessage);
        this.mResultCode = resultCode;
    }

    public int getResultCode(){
        return mResultCode;
    }
}
