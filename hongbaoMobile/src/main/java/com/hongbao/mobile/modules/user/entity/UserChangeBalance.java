package com.hongbao.mobile.modules.user.entity;

import java.math.BigDecimal;

/**
 * 用户修改余额entity
 * @ClassName UserChangeBalance
 * @Description 
 */
public class UserChangeBalance {
	//用户id
	private String userId;
	//金额
	private BigDecimal amount;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
