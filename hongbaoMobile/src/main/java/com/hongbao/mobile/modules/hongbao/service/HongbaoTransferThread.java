package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;
import com.hongbao.mobile.modules.hongbao.entity.TransferInfo;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.weixin.exception.WeixinException;
import com.hongbao.mobile.modules.weixin.util.WeixinTransferUtil;

/**
 * 转账线程
 * @ClassName WeixinTransferThread
 * @Description 
 */
public class HongbaoTransferThread extends Thread {
	
	private HongbaoInfo hongbaoInfo;
	private UserInfo userInfo;
	private String openId;
	private TransferInfoService transferInfoService;
	private Logger logger;
	
	public HongbaoTransferThread(HongbaoInfo hongbaoInfo,UserInfo userInfo,String openId,TransferInfoService transferInfoService,Logger logger) {
		this.hongbaoInfo = hongbaoInfo;
		this.userInfo = userInfo;
		this.openId = openId;
		this.transferInfoService = transferInfoService;
		this.logger = logger;
	}
	
	@Override
	public void run() {
		//获取开奖金额
		BigDecimal luckyAmount = hongbaoInfo.getLuckyAmount();
		//计算转账金额（分）
		int transferAmount = luckyAmount.multiply(getHundred()).intValue();
//		int transferAmount = 100;
		String transferMessage = "拆红包获得【"+ luckyAmount +"】元";
		
		//创建转账信息
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setUserId(userInfo.getId());//用户id
		transferInfo.setUserOpenId(openId);//用户openid
		transferInfo.setHongbaoId(hongbaoInfo.getId());//红包id
		transferInfo.setTransferAmount(new Long(transferAmount));//转账金额
		transferInfo.setTransferMessage(transferMessage);//转账消息
		
		transferInfoService.insertNow(transferInfo);
		
		logger.info("向用户【"+userInfo.getUserNo()+"】发起微信转账，红包编号【"+ hongbaoInfo.getHongbaoNo() +"】，金额【"+ transferAmount +"】");
		
		//转账标识
		String transferFlag = "0";
		//结果消息
		String resultMessage = "";
		//向用户转账
		try {
			Map<String, String> transferResult = new HashMap<>();
			//循环10次，每次等待60秒调用
			transferResult = WeixinTransferUtil.transfer(transferInfo.getId(),openId, transferAmount, transferMessage);
			if(StringUtils.equals(transferResult.get("state"), "SUCCESS")) {
				//转账标识标识
				transferFlag = "1";
				//转账编号
				String paymentNo = transferResult.get("payment_no");
				transferInfo.setPaymentNo(paymentNo);
				//转账时间
				String paymentTime = transferResult.get("payment_time");
				transferInfo.setPaymentTime(paymentTime);
				//修改红包信息
				transferInfo.setTransferFlag(transferFlag);//成功标识
				resultMessage = "ok";
				transferInfo.setResultMessage(resultMessage);//结果消息
				transferInfoService.updateNow(transferInfo);
				//调用成功跳出等待
			} else {
				//修改红包信息
				resultMessage = transferResult.get("err_code")+"："+transferResult.get("err_code_des");
				resultMessage = "向用户【"+userInfo.getUserNo()+"】微信转账失败，失败原因【"+resultMessage+"】，红包编号【"+ hongbaoInfo.getHongbaoNo() +"】，金额【"+ transferAmount +"】";
				//修改红包信息
				transferInfo.setTransferFlag(transferFlag);//成功标识
				transferInfo.setResultMessage(resultMessage);//结果消息
				transferInfoService.updateNow(transferInfo);
				logger.error(resultMessage);
			}
		}
		catch(WeixinException weixin) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】转账失败，失败原因："+weixin.getMessage()+"，红包编号【"+ hongbaoInfo.getHongbaoNo() +"】，金额【"+ transferAmount +"】";
			//修改红包信息
			transferInfo.setTransferFlag(transferFlag);//成功标识
			transferInfo.setResultMessage(resultMessage);//结果消息
			transferInfoService.updateNow(transferInfo);
			logger.error(resultMessage);
		}
		catch (Exception e) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】转账失败，系统异常："+e.getMessage()+"，红包编号【"+ hongbaoInfo.getHongbaoNo() +"】，金额【"+ transferAmount +"】";
			//修改红包信息
			transferInfo.setTransferFlag(transferFlag);//成功标识
			transferInfo.setResultMessage(resultMessage);//结果消息
			transferInfoService.updateNow(transferInfo);
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
