/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.entity;

import org.hibernate.validator.constraints.Length;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 佣金信息Entity
 */
public class YongjinInfo extends DataEntity<YongjinInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 用户编号
	 */
	private String userNo;
			
	/**
	 * 佣金金额
	 */
	private String amount;
			
	/**
	 * 来源用户id
	 */
	private String userIdOrigin;
	
	/**
	 * 来源用户编号
	 */
	private String userNoOrigin;
	
	/**
	 * 红包编号
	 */
	private String hongbaoNo;
			
	
	public YongjinInfo() {
		super();
	}

	public YongjinInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	/**
	 * 获取  用户ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置  用户ID
     * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 获取  佣金金额
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置  佣金金额
     * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=64, message="来源用户id长度必须介于 0 和 64 之间")
	/**
	 * 获取  来源用户id
	 */
	public String getUserIdOrigin() {
		return userIdOrigin;
	}

	/**
	 * 设置  来源用户id
     * @param userIdOrigin
	 */
	public void setUserIdOrigin(String userIdOrigin) {
		this.userIdOrigin = userIdOrigin;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserNoOrigin() {
		return userNoOrigin;
	}

	public void setUserNoOrigin(String userNoOrigin) {
		this.userNoOrigin = userNoOrigin;
	}

	public String getHongbaoNo() {
		return hongbaoNo;
	}

	public void setHongbaoNo(String hongbaoNo) {
		this.hongbaoNo = hongbaoNo;
	}
	
}