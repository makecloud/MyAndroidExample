package com.liuyihui.mylibrary.entitiy.plan;

import java.io.Serializable;

/**
 * 广告计划媒体位效果类
 * 
 * @author yinjy
 *
 */
public class PositionEffect implements Serializable {
	/** 监播图片 */
	private String imageUrl = null;
	/** 监播时间 */
	private String effectDate= null;
	
	/**
	 * 获取监播图片
	 * 
	 * @return 监播图片
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * 设置监播图片
	 * 
	 * @param imageUrl 监播图片
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * 
	 * 获取监播时间
	 * 
	 * @return 监播时间
	 */
	public String getEffectDate() {
		return effectDate;
	}
	/**
	 * 设置监播时间
	 * 
	 * @param effectDate 监播时间
	 */
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	
}
