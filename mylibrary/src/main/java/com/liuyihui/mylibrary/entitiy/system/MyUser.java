package com.liuyihui.mylibrary.entitiy.system;

import java.io.Serializable;

/**
 * 我的用户类
 * 
 * @author yinjy
 *
 */
public class MyUser implements Serializable {

	/** 用户标识 */
	private Long id = null;
	/** 公司标识 */
	private Long companyId = null;
	/** 公司名称 */
	private String companyName = null;
	/** 角色名称 */
	private String roleName = null;
	/** 用户名称 */
	private String username = null;
	/** 用户状态(0:禁用;1:启用) */
	private Short status = null;
	/** 用户姓名 */
	private String name = null;
	/** 用户性别(0:保密;1:先生;2:女士) */
	private Short gender = null;
	/** 用户电话 */
	private String phone = null;
	/** 用户职务 */
	private String title = null;
	/** 用户邮箱 */
	private String email = null;
	/** 用户头像 */
	private String avatar = null;
	/** 账户余额(元) */
	private Double balance = null;
	/** 登录令牌 */
	private String loginToken = null;

	/**
	 * 获取用户标识
	 * 
	 * @return 用户标识
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置用户标识
	 * 
	 * @param id 用户标识
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取公司标识
	 * 
	 * @return 公司标识
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * 设置公司标识
	 * 
	 * @param companyId 公司标识
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 获取公司名称
	 * 
	 * @return 公司名称
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 设置公司名称
	 * 
	 * @param companyName 公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 获取角色名称
	 * 
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置角色名称
	 * 
	 * @param roleName 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取用户名称
	 * 
	 * @return 用户名称
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名称
	 * 
	 * @param username 用户名称
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取用户状态(0:禁用;1:启用)
	 * 
	 * @return 用户状态(0:禁用;1:启用)
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * 设置用户状态(0:禁用;1:启用)
	 * 
	 * @param status 用户状态(0:禁用;1:启用)
	 */
	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * 获取用户姓名
	 * 
	 * @return 用户姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置用户姓名
	 * 
	 * @param name 用户姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取用户性别(0:保密;1:先生;2:女士)
	 * 
	 * @return 用户性别(0:保密;1:先生;2:女士)
	 */
	public Short getGender() {
		return gender;
	}

	/**
	 * 设置用户性别(0:保密;1:先生;2:女士)
	 * 
	 * @param gender 用户性别(0:保密;1:先生;2:女士)
	 */
	public void setGender(Short gender) {
		this.gender = gender;
	}

	/**
	 * 获取用户电话
	 * 
	 * @return 用户电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置用户电话
	 * 
	 * @param phone 用户电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取用户职务
	 * 
	 * @return 用户职务
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置用户职务
	 * 
	 * @param title 用户职务
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取用户邮箱
	 * 
	 * @return 用户邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置用户邮箱
	 * 
	 * @param email 用户邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取用户头像
	 * 
	 * @return 用户头像
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * 设置用户头像
	 * 
	 * @param avatar 用户头像
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 获取账户余额(元)
	 * 
	 * @return 账户余额(元)
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * 设置账户余额(元)
	 * 
	 * @param balance 账户余额(元)
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * 获取登录令牌
	 * 
	 * @return 登录令牌
	 */
	public String getLoginToken() {
		return loginToken;
	}

	/**
	 * 设置登录令牌
	 * 
	 * @param loginToken 登录令牌
	 */
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

}
