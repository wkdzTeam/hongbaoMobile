/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.modules.hongbao.dao.HongbaoDrawInfoDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.user.util.UserBalanceUtil;

/**
 * 红包转盘余额支付Service
 * @ClassName HongbaoDrawInfoBalancePayService
 * @Description 
 */
@Service
@Transactional(readOnly = true)
public class HongbaoDrawInfoBalancePayService extends CrudService<HongbaoDrawInfoDao, HongbaoDrawInfo> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(HongbaoDrawInfoService.class);
	
	/**
	 * 红包转盘完成支付Service
	 */
	@Autowired
	private HongbaoDrawInfoPayFinishService hongbaoDrawInfoPayFinishService;
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 用户第三方登录记录Service
	 */
	@Autowired
	private UserOauthService userOauthService;
	
	/**
	 * 余额支付
	 * @Title balancePay
	 * @Description 
	 * @param hongbaoDrawId
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo balancePay(String hongbaoDrawId) throws HongbaoException {
		//查询订单信息
		HongbaoDrawInfo hongbaoDrawInfo = this.get(hongbaoDrawId);
		return balancePay(hongbaoDrawInfo);
	}
	
	/**
	 * 余额支付
	 * @Title balancePay
	 * @Description 
	 * @param hongbaoDrawInfo
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo balancePay(HongbaoDrawInfo hongbaoDrawInfo) throws HongbaoException {
		//获取session中的用户信息
		UserInfo userInfoSession = (UserInfo)getRequest().getSession().getAttribute("userInfo");
		if(userInfoSession==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		if(hongbaoDrawInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
		}
		//查询用户信息
		UserInfo userInfo = userInfoService.get(userInfoSession.getId());
		if(userInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		//判断是待支付的情况
		if(!hongbaoDrawInfo.getPayFlag().equals("0")) {
			throw new HongbaoException(ResultCodeConstants.C0012,"已支付");
		}
		//获取红包金额
		BigDecimal amount = hongbaoDrawInfo.getAmount();
		//判断用户余额是否足够
		if(userInfo.getBalance().compareTo(amount)==-1) {
			//1107:余额不足
			throw new HongbaoException(ResultCodeConstants.C1107,"余额不足");
		}
		
		//生成支付单号
		String tradeCode = IdGen.uuid();
		String tradePaycode = IdGen.uuid();
		
		//完成支付操作
		hongbaoDrawInfo = hongbaoDrawInfoPayFinishService.finishPayOrder(hongbaoDrawInfo,"", tradeCode,tradePaycode,"3");
		
		//扣除余额
		UserBalanceUtil.changeBalance(userInfo.getId(), amount.multiply(new BigDecimal(-1)),"余额支付",getRequest());
		
		return hongbaoDrawInfo;
	}
	
	/**
	 * 根据用户去查询子类的红包
	 * @param map
	 * @return
	 * @throws HongbaoException
	 */
	public List<HongbaoDrawInfo> getListHongbaoDrawByUser(Map map) throws HongbaoException {
		return dao.getListHongbaoDrawByUser(map);
	}
	
}