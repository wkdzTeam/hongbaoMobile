package com.hongbao.mobile.modules.sys.entity;

import com.hongbao.mobile.common.config.Hongbao;

/**
 * 验证码实体类
 * @ClassName TelCodeInfo
 * @Description 
 */
public class TelCodeInfo {
	
	/**
	 * 获取验证码在redis中的key值
	 */
	public static String telCodeRedisKey = Hongbao.getConfig("telCode.redis.key");
	
	/**
	 * 注册
	 */
	public static final String TYPE_REGISTER = "register";
	
	/**
	 * 找回密码
	 */
	public static final String TYPE_FIND_PASSWORD = "findPassword";
	
	
	//手机号
	private String phone;
	//发送时间
	private Long time;
	//验证码
	private String telCode;
	//类型
	private String type;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getTelCode() {
		return telCode;
	}
	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
