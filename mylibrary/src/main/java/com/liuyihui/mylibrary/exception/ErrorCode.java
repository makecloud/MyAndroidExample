package com.liuyihui.mylibrary.exception;

/**
 * 错误编码类
 * 
 * @author yinjy
 */
public class ErrorCode {

	/** 令牌失效 */
	public static final int TOKEN_INVALID = 1;
	/** 签名错误 */
	public static final int SIGNATURE_ERROR = 2;
	/** 短信越限 */
	public static final int SMS_OVER_LIMIT = 3;
	/** 其它错误 */
	public static final int OTHER_ERROR = 9999;

}
