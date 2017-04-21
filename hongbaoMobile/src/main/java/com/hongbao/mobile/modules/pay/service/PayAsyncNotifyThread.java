package com.hongbao.mobile.modules.pay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.pay.util.PayUtil;

/**
 * 支付异步通知
 * @ClassName PayAsyncNotifyThread
 * @Description 
 */
public class PayAsyncNotifyThread extends Thread {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private String hongbaoNo;
	private String trade_code;
	private String trade_paycode;
	private HongbaoInfoService hongbaoInfoService;
	
	public PayAsyncNotifyThread(String hongbaoNo,String trade_code,String trade_paycode,HongbaoInfoService hongbaoInfoService) {
		this.hongbaoNo = hongbaoNo;
		this.trade_code = trade_code;
		this.trade_paycode = trade_paycode;
		this.hongbaoInfoService = hongbaoInfoService;
	}
	
	@Override
	public void run() {
		try {
			//获取通知
			PayNotify payNotify = PayUtil.getSetPayNotify(hongbaoNo);
			//异步通知
			payNotify.asyncNotify(hongbaoInfoService, trade_code, trade_paycode);
		}
		catch (HongbaoException e) {
			e.printStackTrace();
			logger.info("异步通知失败："+e.getResultCode().getMsg());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("异步通知系统异常："+e.getMessage());
		}
	}
}
