/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 用户信息Entity
 */
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	private String userNo;
			
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
			
	/**
	 * 参加夺宝次数
	 */
	private Integer joinItemCount;
			
	/**
	 * 最后登录时间
	 */
	private Long lastLoginTime;
			
	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;
			
	/**
	 * 渠道号
	 */
	@JsonIgnore
	private String channel;
	
	/**
	 * 导入标识（0：否，1：是）
	 */
	@JsonIgnore
	private String importFlag;
	
	/**
	 * 用户父类ID
	 */
	private String parentUserid;
	
	/**
	 * 微信openId
	 */
	private String openId;
	
	/**
	 * 微信openId2
	 */
	private String openId2;
	
	/**
	 * 设备号
	 */
	private String equipment;
	

	/**
	 * 佣金
	 */
	@JsonIgnore
	private BigDecimal yongjin;
	
	//佣金操作金额
	private BigDecimal yongjinAmount;
	
	public BigDecimal getYongjinAmount() {
		return yongjinAmount;
	}

	public void setYongjinAmount(BigDecimal yongjinAmount) {
		this.yongjinAmount = yongjinAmount;
	}
	
	public UserInfo() {
		super();
	}

	public UserInfo(String id){
		super(id);
	}

	/**
	 * 获取  用户编号
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 设置  用户编号
     * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	/**
	 * 获取  余额
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置  余额
     * @param balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 获取  支付金额
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	/**
	 * 设置  支付金额
     * @param payAmount
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * 获取  参加夺宝次数
	 */
	public Integer getJoinItemCount() {
		if(joinItemCount==null) {
			joinItemCount=0;
		}
		return joinItemCount;
	}

	/**
	 * 设置  参加夺宝次数
     * @param joinItemCount
	 */
	public void setJoinItemCount(Integer joinItemCount) {
		this.joinItemCount = joinItemCount;
	}
	
	/**
	 * 获取  最后登录时间
	 */
	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * 设置  最后登录时间
     * @param lastLoginTime
	 */
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	/**
	 * 获取  最后登录ip
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * 设置  最后登录ip
     * @param lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * 获取  导入标识（0：否，1：是）
	 */
	public String getImportFlag() {
		return importFlag;
	}

	/**
	 * 设置  导入标识（0：否，1：是）
     * @param importFlag
	 */
	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}
	
	/**
	 * 获取  渠道号
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * 设置 渠道号
     * @param channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getParentUserid() {
		return parentUserid;
	}

	public void setParentUserid(String parentUserid) {
		this.parentUserid = parentUserid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public BigDecimal getYongjin() {
		return yongjin;
	}

	public void setYongjin(BigDecimal yongjin) {
		this.yongjin = yongjin;
	}

	public String getOpenId2() {
		return openId2;
	}

	public void setOpenId2(String openId2) {
		this.openId2 = openId2;
	}
}