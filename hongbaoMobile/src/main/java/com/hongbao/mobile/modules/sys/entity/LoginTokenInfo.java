package com.hongbao.mobile.modules.sys.entity;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.utils.IdGen;


/**
 * 登录令牌
 * @ClassName LoginTokenInfo
 * @Description 
 */
public class LoginTokenInfo {
	
	public static String loginTokenId;
	
	/**
	 * 获取登录令牌在redis中的key值
	 */
	public static String loginTokenRedisKey = Hongbao.getConfig("login.token.redis.key");
	
	//令牌id
	private String tokenId;
	//登录时间
	private Long loginTime;
	//用户id
	private String userId;
	//电话
	private String phone;
	//cid(个推客户端id)
	private String cid;
	
	/**
	 * 构造函数
	 * @Title LoginTokenInfo
	 * @Description 
	 */
	public LoginTokenInfo() {
		this.tokenId = IdGen.uuid();
		this.loginTime = System.currentTimeMillis();
	}
	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
}
