package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.weixin.exception.WeixinException;
import com.hongbao.mobile.modules.weixin.util.WeixinTransferUtil;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;
import com.hongbao.mobile.modules.yongjin.service.DuihuanInfoUpdateService;

/**
 * 转账线程
 * @ClassName WeixinTransferThread
 * @Description 
 */
public class HongbaoDrawTransferThread extends Thread {
	
	private UserInfo userInfo;
	private DuihuanInfo duihuanInfo;
	private DuihuanInfoUpdateService duihuanInfoUpdateService;
	private Logger logger;
	
	public HongbaoDrawTransferThread(UserInfo userInfo,DuihuanInfo duihuanInfo,DuihuanInfoUpdateService duihuanInfoUpdateService,Logger logger) {
		this.userInfo = userInfo;
		this.duihuanInfo = duihuanInfo;
		this.duihuanInfoUpdateService = duihuanInfoUpdateService;
		this.logger = logger;
	}
	
	@Override
	public void run() {
		String transferMessage = "【"+userInfo.getUserNo()+"】余额提现："+duihuanInfo.getTransferAmount();
		logger.info(transferMessage);
		//设置转账信息
		duihuanInfo.setTransferMessage(transferMessage);
		//设置转账中
		duihuanInfo.setPayFlag("2");
		//更新记录
		duihuanInfoUpdateService.updateNow(duihuanInfo);
		
		//计算转账金额（分）
		int transferAmount = duihuanInfo.getTransferAmount().multiply(getHundred()).intValue();
//		int transferAmount = 100;
		
		logger.info("向用户【"+userInfo.getUserNo()+"】发起微信提现，金额【"+ transferAmount +"】");
		
		//转账标识
		String transferFlag = "1"; //失败    0:以转账  1：未转账
		String resultMessage = "";
		//向用户转账
		try {
			Map<String, String> transferResult = new HashMap<>();
			//调用转账接口
			transferResult = WeixinTransferUtil.transfer(duihuanInfo.getId(),duihuanInfo.getUserOpenId(), transferAmount, transferMessage);
			if(StringUtils.equals(transferResult.get("state"), "SUCCESS")) {
				//转账标识标识
				transferFlag = "0";
				//转账编号
				String paymentNo = transferResult.get("payment_no");
				duihuanInfo.setTransferNo(paymentNo);
				//转账时间
				String paymentTime = transferResult.get("payment_time");
				duihuanInfo.setTransferTime(paymentTime);
				//修改红包信息
				duihuanInfo.setPayFlag(transferFlag);//成功标识
				duihuanInfo.setResultMessage("ok");
				duihuanInfoUpdateService.updateNow(duihuanInfo);
				//调用成功跳出等待
				resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现成功，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
				logger.info(resultMessage);
			} else {
				resultMessage = transferResult.get("err_code")+"："+transferResult.get("err_code_des");
				resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，失败原因【"+resultMessage+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
				//设置转账失败
				duihuanInfo.setPayFlag("3");
				duihuanInfo.setResultMessage(resultMessage);
				duihuanInfoUpdateService.updateNow(duihuanInfo);
				logger.error(resultMessage);
			}
		} catch(WeixinException weixin) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，失败原因："+weixin.getMessage()+"，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
			//设置转账失败
			duihuanInfo.setPayFlag("3");
			duihuanInfo.setResultMessage(resultMessage);
			duihuanInfoUpdateService.updateNow(duihuanInfo);
			logger.error(resultMessage);
		} catch (Exception e) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，系统异常："+e.getMessage()+"，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";//设置转账失败
			duihuanInfo.setPayFlag("3");
			duihuanInfo.setResultMessage(resultMessage);
			duihuanInfoUpdateService.updateNow(duihuanInfo);
			logger.error(resultMessage);
		}
		
	}
	
	/**
	 * 获取100
	 * @Title getHundred
	 * @Description 
	 * @return
	 */
	private BigDecimal getHundred() {
		BigDecimal hundred = BigDecimal.TEN.multiply(BigDecimal.TEN);
		return hundred;
	}
}
