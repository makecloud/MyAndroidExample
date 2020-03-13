package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 视图通用map类
 * 
 * @author yinjy
 *
 */
public class ViewMap implements Serializable {
	/** 标识 */
	private Long id = null;
	/** 名称 */
	private String name = null;

	/**
	 * 获取标识
	 * 
	 * @return 标识
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置标识
	 * 
	 * @param id 标识
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

}
