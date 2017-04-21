package com.hongbao.mobile.modules.pay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.pay.util.PayUtil;

/**
 * 支付同步通知
 * @ClassName PaySyncNotifyThread
 * @Description 
 */
public class PaySyncNotifyThread extends Thread {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private String hongbaoNo;
	private HongbaoInfoService hongbaoInfoService;
	
	public PaySyncNotifyThread(String hongbaoNo,HongbaoInfoService hongbaoInfoService) {
		this.hongbaoNo = hongbaoNo;
		this.hongbaoInfoService = hongbaoInfoService;
	}
	
	@Override
	public void run() {
		try {
			//获取通知
			PayNotify payNotify = PayUtil.getSetPayNotify(hongbaoNo);
			//同步通知
			payNotify.syncNotify(hongbaoInfoService);
		}
		catch (HongbaoException e) {
			e.printStackTrace();
			logger.info("同步通知失败："+e.getResultCode().getMsg());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("同步通知系统异常："+e.getMessage());
		}
	}
}
