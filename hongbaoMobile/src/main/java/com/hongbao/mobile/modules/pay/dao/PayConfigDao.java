/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.pay.entity.PayConfig;

/**
 * 支付配置DAO接口
 */
@MyBatisDao
public interface PayConfigDao extends CrudDao<PayConfig> {
	
	/**
	 * 根据商户号获取南粤的支付配置
	 * @Title getNanyueByMchId
	 * @Description 
	 * @param payConfig
	 * @return
	 */
	public PayConfig getNanyueByMchId(PayConfig payConfig);
	
	/**
	 * 根据商户号获取威富通的支付配置
	 * @Title getWeifutongByMchId
	 * @Description 
	 * @param payConfig
	 * @return
	 */
	public PayConfig getWeifutongByMchId(PayConfig payConfig);
	

	/**
	 * 根据应用id获取浦发的支付配置
	 * @Title getPufaByAppId
	 * @Description 
	 * @param payConfig
	 * @return
	 */
	public PayConfig getPufaByAppId(PayConfig payConfig);
}