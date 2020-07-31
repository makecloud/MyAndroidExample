package com.liuyihui.networkcontrol.http.retrofitCommon;

/**
 * 接口异常类
 *
 * @author yinjy
 */
public class ApiException extends IllegalStateException {

    /** 错误编码 */
    private int code = 0;

    /**
     * 构造函数
     *
     * @param code 错误编码
     */
    public ApiException(int code) {
        super();
        this.code = code;
    }

    public ApiException(String msg) {
        super(msg);
    }

    /**
     * 构造函数
     *
     * @param code 错误编码
     * @param message 异常消息
     */
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code 错误编码
     * @param cause 异常原因
     */
    public ApiException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code 错误编码
     * @param message 异常消息
     * @param cause 异常原因
     */
    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


}
