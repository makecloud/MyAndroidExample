package com.liuyihui.mylibrary.entitiy.message;

import java.io.Serializable;

/**
 * 消息类
 * 
 * @author yinjy
 * 
 */
public class Message implements Serializable {

	/** 消息标识 */
	private Long id = null;
	/** 消息类型(1:公告;2:通知) */
	private String type = null;
	/** 消息状态(1:未读;2:已读;3:删除) */
	private String status = null;
	/** 标题 */
	private String title = null;
	/** 消息内容 */
	private String content = null;
	/** 创建时间(YYYY-MM-DD HH:MM:SS) */
	private String createdTime = null;

	/**
	 * 获取消息标识
	 * 
	 * @return 消息标识
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置消息标识
	 * 
	 * @param id 消息标识
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取消息类型(1:公告;2:通知)
	 * 
	 * @return 消息类型(1:公告;2:通知)
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置消息类型(1:公告;2:通知)
	 * 
	 * @param type 消息类型(1:公告;2:通知)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取消息状态(1:未读;2:已读;3:删除)
	 * 
	 * @return 消息状态(1:未读;2:已读;3:删除)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置消息状态(1:未读;2:已读;3:删除)
	 * 
	 * @param status 消息状态(1:未读;2:已读;3:删除)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取消息内容
	 * 
	 * @return 消息内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param content 消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取创建时间(YYYY-MM-DD HH:MM:SS)
	 * 
	 * @return 创建时间(YYYY-MM-DD HH:MM:SS)
	 */
	public String getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置创建时间(YYYY-MM-DD HH:MM:SS)
	 * 
	 * @param createdTime 创建时间(YYYY-MM-DD HH:MM:SS)
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

}
