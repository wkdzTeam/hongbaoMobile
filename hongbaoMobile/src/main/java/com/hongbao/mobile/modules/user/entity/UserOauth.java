/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.entity;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 用户第三方登录记录Entity
 */
public class UserOauth extends DataEntity<UserOauth> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private String userId;
			
	/**
	 * 第三方登录id-开发者
	 */
	private String openId;
	
	/**
	 * 第三方登录id-应用
	 */
	private String unionId;
			
	/**
	 * 登录令牌
	 */
	private String accessToken;
		
	/**
	 * 刷新令牌
	 */
	private String refreshToken;
	
	/**
	 * 令牌超时时间(s)
	 */
	private Integer expiresIn;
			
	/**
	 * 注册来源（1：微信，2：QQ，3：微博）
	 */
	private String source;
			
	/**
	 * 登录次数
	 */
	private Integer loginNum;
			
	
	public UserOauth() {
		super();
	}

	public UserOauth(String id){
		super(id);
	}

	/**
	 * 获取  用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置  用户id
     * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 获取  第三方登录id-开发者
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置  第三方登录id-开发者
     * @param openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 获取 第三方登录id-应用
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * 设置  第三方登录id-应用
     * @param unionId
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * 获取  登录令牌
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 设置  登录令牌
     * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	/**
	 * 获取 刷新令牌
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 设置  刷新令牌
     * @param refreshToken
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	/**
	 * 获取 令牌超时时间(s)
	 */
	public Integer getExpiresIn() {
		return expiresIn;
	}

	/**
	 * 设置  令牌超时时间(s)
     * @param expiresIn
	 */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * 获取  注册来源（1：微信，2：QQ，3：微博）
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 设置  注册来源（1：微信，2：QQ，3：微博）
     * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * 获取  登录次数
	 */
	public Integer getLoginNum() {
		return loginNum;
	}

	/**
	 * 设置  登录次数
     * @param loginNum
	 */
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	
}