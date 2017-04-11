/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 兑换Entity
 */
public class DuihuanInfo extends DataEntity<DuihuanInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 兑换用户id
	 */
	private String userId;
			
	/**
	 * 用户编号
	 */
	private String userNo;
			
	/**
	 * 兑换标记[0：兑换；1：未兑换]
	 */
	private String payFlag;
			
	/**
	 * 用户openid
	 */
	private String userOpenId;
			
	/**
	 * 转账说明
	 */
	private String transferMessage;
			
	/**
	 * 转账金额
	 */
	private BigDecimal transferAmount;
			
	/**
	 * 转账编号
	 */
	private String transferNo;
			
	/**
	 * 转账时间
	 */
	private String transferTime;
	
	/**
	 * 回值内容
	 */
	private String resultMessage;
	
	/**
	 * 标示[1:红包佣金2:转盘奖励]
	 */
	private String transferType;
			
	
	public DuihuanInfo() {
		super();
	}

	public DuihuanInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="兑换用户id长度必须介于 0 和 64 之间")
	/**
	 * 获取  兑换用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置  兑换用户id
     * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=20, message="用户编号长度必须介于 0 和 20 之间")
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
	
	@Length(min=0, max=1, message="兑换标记[0：兑换；1：未兑换]长度必须介于 0 和 1 之间")
	/**
	 * 获取  兑换标记[0：兑换；1：未兑换]
	 */
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * 设置  兑换标记[0：兑换；1：未兑换]
     * @param payFlag
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	
	@Length(min=0, max=100, message="用户openid长度必须介于 0 和 100 之间")
	/**
	 * 获取  用户openid
	 */
	public String getUserOpenId() {
		return userOpenId;
	}

	/**
	 * 设置  用户openid
     * @param userOpenId
	 */
	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}
	
	@Length(min=0, max=200, message="转账说明长度必须介于 0 和 200 之间")
	/**
	 * 获取  转账说明
	 */
	public String getTransferMessage() {
		return transferMessage;
	}

	/**
	 * 设置  转账说明
     * @param transferMessage
	 */
	public void setTransferMessage(String transferMessage) {
		this.transferMessage = transferMessage;
	}
	
	/**
	 * 获取  转账金额
	 */
	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	/**
	 * 设置  转账金额
     * @param transferAmount
	 */
	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	@Length(min=0, max=50, message="转账编号长度必须介于 0 和 50 之间")
	/**
	 * 获取  转账编号
	 */
	public String getTransferNo() {
		return transferNo;
	}

	/**
	 * 设置  转账编号
     * @param transferNo
	 */
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	
	@Length(min=0, max=50, message="转账时间长度必须介于 0 和 50 之间")
	/**
	 * 获取  转账时间
	 */
	public String getTransferTime() {
		return transferTime;
	}

	/**
	 * 设置  转账时间
     * @param transferTime
	 */
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}