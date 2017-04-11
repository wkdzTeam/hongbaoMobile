/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.entity;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 转账信息Entity
 */
public class TransferInfo extends DataEntity<TransferInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private String userId;
			
	/**
	 * 用户openid
	 */
	private String userOpenId;
			
	/**
	 * 红包id
	 */
	private String hongbaoId;
	
	/**
	 * 转账金额
	 */
	private Long transferAmount;
	
	/**
	 * 转账消息
	 */
	private String transferMessage;
	
	/**
	 * 转账标识
	 */
	private String transferFlag;
	
	/**
	 * 结果消息
	 */
	private String resultMessage;
	
	/**
	 * 转账编号
	 */
	private String paymentNo;
	
	/**
	 * 转账时间
	 */
	private String paymentTime;
			
	
	public TransferInfo() {
		super();
	}

	public TransferInfo(String id){
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
	
	/**
	 * 获取  红包id
	 */
	public String getHongbaoId() {
		return hongbaoId;
	}

	/**
	 * 设置  红包id
     * @param hongbaoId
	 */
	public void setHongbaoId(String hongbaoId) {
		this.hongbaoId = hongbaoId;
	}

	/**
	 * 获取  转账金额
	 */
	public Long getTransferAmount() {
		return transferAmount;
	}

	/**
	 * 设置  转账金额
     * @param transferAmount
	 */
	public void setTransferAmount(Long transferAmount) {
		this.transferAmount = transferAmount;
	}

	/**
	 * 获取  转账消息
	 */
	public String getTransferMessage() {
		return transferMessage;
	}

	/**
	 * 设置  转账消息
     * @param transferMessage
	 */
	public void setTransferMessage(String transferMessage) {
		this.transferMessage = transferMessage;
	}
	
	/**
	 * 获取  转账标识
	 */
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * 设置  转账标识
     * @param transferFlag
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * 获取  结果消息
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * 设置  结果消息
     * @param resultMessage
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * 获取  转账编号
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * 设置  转账编号
     * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * 获取  转账时间
	 */
	public String getPaymentTime() {
		return paymentTime;
	}

	/**
	 * 设置  转账时间
     * @param paymentTime
	 */
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	
}