package com.liuyihui.networkcontrol.retrofitCommon;

import java.io.Serializable;

/**
 * 接口应答类
 * 
 * @author yinjy
 */
public class ApiResponse implements Serializable {

	/** 应答编码 */
	private int code = 0;
	/** 应答消息 */
	private String message = null;
	/** 应答数据 */
	private Object data = null;

	/**
	 * 构造函数
	 */
	public ApiResponse() {
	}

	/**
	 * 构造函数
	 * 
	 * @param data 应答数据
	 */
	public ApiResponse(Object data) {
		this.data = data;
	}

	/**
	 * 构造函数
	 * 
	 * @param code 应答编码
	 * @param message 应答消息
	 */
	public ApiResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 构造函数
	 * 
	 * @param code 应答编码
	 * @param message 应答消息
	 * @param data 应答数据
	 */
	public ApiResponse(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 获取应答编码
	 * 
	 * @return 应答编码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置应答编码
	 * 
	 * @param code 应答编码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获取应答消息
	 * 
	 * @return 应答消息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置应答消息
	 * 
	 * @param message 应答消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取应答数据
	 * 
	 * @return 应答数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置应答数据
	 * 
	 * @param data 应答数据
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
