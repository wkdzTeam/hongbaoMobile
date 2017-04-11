/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.util.UserBalanceUtil;
import com.hongbao.mobile.modules.user.util.UserYongjinUtil;
import com.hongbao.mobile.modules.weixin.exception.WeixinException;
import com.hongbao.mobile.modules.weixin.util.WeixinTransferUtil;
import com.hongbao.mobile.modules.yongjin.dao.DuihuanInfoDao;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;

/**
 * 兑换Service
 */
@Service
@Transactional(readOnly = true)
public class DuihuanInfoService extends CrudService<DuihuanInfoDao, DuihuanInfo> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(DuihuanInfoService.class);
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 兑换更新Service
	 */
	@Autowired
	private DuihuanInfoUpdateService duihuanInfoUpdateService;

	public DuihuanInfo get(String id) {
		return super.get(id);
	}
	
	public List<DuihuanInfo> findList(DuihuanInfo duihuanInfo) {
		return super.findList(duihuanInfo);
	}
	
	public Page<DuihuanInfo> findPage(Page<DuihuanInfo> page, DuihuanInfo duihuanInfo) {
		return super.findPage(page, duihuanInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(DuihuanInfo duihuanInfo) {
		return super.save(duihuanInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(DuihuanInfo duihuanInfo) {
		return super.delete(duihuanInfo);
	}
	
	/**
	 * 根据用户获取兑换记录
	 * @param hongbaoInfo
	 * @return
	 */
	public List<DuihuanInfo> getDuihuanListByUser(Map map){
		return dao.getDuihuanListByUser(map);
	}
	
	/**
	 * 提现金币
	 * @Title tixian
	 * @Description 
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public void tixian() throws HongbaoException {
		//获取登录用户
		UserInfo userInfoSession = (UserInfo)getRequest().getSession().getAttribute("userInfo");
		if(userInfoSession==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		UserInfo userInfo = userInfoService.get(userInfoSession.getId());
		
		//余额
		BigDecimal balance = userInfo.getBalance();
		if(balance.compareTo(BigDecimal.ONE)<0){
			throw new HongbaoException(ResultCodeConstants.C0012,"提现最低限额1元");
		}
		//验证余额
		if(balance.compareTo(BigDecimal.ZERO)<=0){
			throw new HongbaoException(ResultCodeConstants.C0012,"余额为空");
		}

		BigDecimal amount = userInfo.getBalance(); 
		
		//修改金额
		UserBalanceUtil.changeBalance(userInfo.getId(), new BigDecimal("-"+balance),"提现",getRequest());
		
		//插入兑换记录
		//兑换记录
		DuihuanInfo duihuanInfo = new DuihuanInfo();				
		duihuanInfo.setId(IdGen.uuid()); //ID
		duihuanInfo.setUserId(userInfo.getId()); //用户id
		duihuanInfo.setUserOpenId(userInfo.getOpenId()); //用户openid
		duihuanInfo.setUserNo(userInfo.getUserNo()); //用户编号
		duihuanInfo.setTransferAmount(amount); //金额
		duihuanInfo.setTransferMessage(""); //转账消息
		duihuanInfo.setTransferType("2");  //红包佣金领取   
		duihuanInfo.setPayFlag("1"); //转账标示  默认没有转账 0:已转账  1：等待中 2:转账中 3:转账失败 
		duihuanInfo.setDelFlag("0");
		duihuanInfoUpdateService.insertNow(duihuanInfo);
		
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
				resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，操作失败，失败原因【"+resultMessage+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
				//设置转账失败
				duihuanInfo.setPayFlag("3");
				duihuanInfo.setResultMessage(resultMessage);
				duihuanInfoUpdateService.updateNow(duihuanInfo);
				logger.error(resultMessage);
			}
		} catch(WeixinException weixin) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，异常失败，失败原因【"+weixin.getMessage()+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
			//设置转账失败
			duihuanInfo.setPayFlag("3");
			duihuanInfo.setResultMessage(resultMessage);
			duihuanInfoUpdateService.updateNow(duihuanInfo);
			logger.error(resultMessage);
		} catch (Exception e) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，系统异常【"+e.getMessage()+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";//设置转账失败
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
	
	
	
	/**
	 * 提现佣金
	 * @Title tixianYongjin
	 * @Description 
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public void tixianYongjin() throws HongbaoException {
		//获取登录用户
		UserInfo userInfoSession = (UserInfo)getRequest().getSession().getAttribute("userInfo");
		if(userInfoSession==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		UserInfo userInfo = userInfoService.get(userInfoSession.getId());
		
		//佣金
		BigDecimal yongin = userInfo.getYongjin();
		//验证余额
		if(yongin.compareTo(BigDecimal.ZERO)<=0){
			throw new HongbaoException(ResultCodeConstants.C0012,"余额为空");
		}
		if(yongin.compareTo(BigDecimal.ONE)<0){
			throw new HongbaoException(ResultCodeConstants.C0012,"提现最低限额1元");
		}
		
		//修改金额
		UserYongjinUtil.changeYongjin(userInfo.getId(), new BigDecimal("-"+yongin),"佣金提现",getRequest());
		
		//插入兑换记录
		//兑换记录
		DuihuanInfo duihuanInfo = new DuihuanInfo();				
		duihuanInfo.setId(IdGen.uuid()); //ID
		duihuanInfo.setUserId(userInfo.getId()); //用户id
		duihuanInfo.setUserOpenId(userInfo.getOpenId()); //用户openid
		duihuanInfo.setUserNo(userInfo.getUserNo()); //用户编号
		duihuanInfo.setTransferAmount(yongin); //金额
		duihuanInfo.setTransferMessage(""); //转账消息
		duihuanInfo.setTransferType("1");  //红包佣金领取  1、佣金  2、用户金币兑换   
		duihuanInfo.setPayFlag("1"); //转账标示  默认没有转账 0:已转账  1：等待中 2:转账中 3:转账失败 
		duihuanInfo.setDelFlag("0");
		duihuanInfoUpdateService.insertNow(duihuanInfo);
		
		String transferMessage = "【"+userInfo.getUserNo()+"】佣金提现："+duihuanInfo.getTransferAmount();
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
		
		logger.info("向用户【"+userInfo.getUserNo()+"】发起微信提现佣金，金额【"+ transferAmount +"】");
		
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
				resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，操作失败，失败原因【"+resultMessage+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
				//设置转账失败
				duihuanInfo.setPayFlag("3");
				duihuanInfo.setResultMessage(resultMessage);
				duihuanInfoUpdateService.updateNow(duihuanInfo);
				logger.error(resultMessage);
			}
		} catch(WeixinException weixin) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，异常失败，失败原因【"+weixin.getMessage()+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";
			//设置转账失败
			duihuanInfo.setPayFlag("3");
			duihuanInfo.setResultMessage(resultMessage);
			duihuanInfoUpdateService.updateNow(duihuanInfo);
			logger.error(resultMessage);
		} catch (Exception e) {
			resultMessage = "向用户【"+userInfo.getUserNo()+"】微信提现，系统异常【"+e.getMessage()+"】，兑换ID【"+ duihuanInfo.getId() +"】，金额【"+ transferAmount +"】";//设置转账失败
			duihuanInfo.setPayFlag("3");
			duihuanInfo.setResultMessage(resultMessage);
			duihuanInfoUpdateService.updateNow(duihuanInfo);
			logger.error(resultMessage);
		}
		
	}
}