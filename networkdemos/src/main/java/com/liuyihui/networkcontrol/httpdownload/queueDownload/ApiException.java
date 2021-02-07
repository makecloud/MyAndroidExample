package com.liuyihui.networkcontrol.httpdownload.queueDownload;

/**
 * 接口异常。api返回code不为0
 * <p>
 *
 * @author makecloudl
 */

public class ApiException extends RuntimeException {

    private int mApiCode;
    public static final int CODE_UNBIND = 101;

    public ApiException(String detailMessage, int apiCode) {
        super(detailMessage);
        this.mApiCode = apiCode;
    }

    public int getApiCode() {
        return mApiCode;
    }
}
