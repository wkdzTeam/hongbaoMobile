package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;

import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

public class YongjinThread extends Thread {
	
	private YongjinInfoService yongjinInfoService;
	private String userId;
	private BigDecimal amount; 
	
	public YongjinThread(YongjinInfoService yongjinInfoService,String userId,BigDecimal amount) {
		this.yongjinInfoService = yongjinInfoService;
		this.userId = userId;
		this.amount = amount;
	}
	
	
	@Override
	public void run() {
		//佣金奖励
		yongjinInfoService.createYongjin(userId, amount);
	}
}
