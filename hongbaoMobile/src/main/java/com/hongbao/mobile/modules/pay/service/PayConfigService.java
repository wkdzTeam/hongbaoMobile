/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.dao.PayConfigDao;

/**
 * 支付配置Service
 */
@Service
@Transactional(readOnly = true)
public class PayConfigService extends CrudService<PayConfigDao, PayConfig> {

	public PayConfig get(String id) {
		return super.get(id);
	}
	
	public List<PayConfig> findList(PayConfig payConfig) {
		return super.findList(payConfig);
	}
	
	public Page<PayConfig> findPage(Page<PayConfig> page, PayConfig payConfig) {
		return super.findPage(page, payConfig);
	}
	
	@Transactional(readOnly = false)
	public int save(PayConfig payConfig) {
		return super.save(payConfig);
	}
	
	@Transactional(readOnly = false)
	public int delete(PayConfig payConfig) {
		return super.delete(payConfig);
	}
	
	/**
	 * 根据商户号获取威富通的支付配置
	 * @Title getWeifutongByMchId
	 * @Description 
	 * @param mchId
	 * @return
	 */
	public PayConfig getWeifutongByMchId(String mchId) {
		PayConfig payConfig = new PayConfig();
		payConfig.setMchId(mchId);
		return dao.getWeifutongByMchId(payConfig);
	}
	
	/**
	 * 根据应用id获取浦发的支付配置
	 * @Title getPufaByAppId
	 * @Description 
	 * @param appId
	 * @return
	 */
	public PayConfig getPufaByAppId(String appId) {
		PayConfig payConfig = new PayConfig();
		payConfig.setAppId(appId);
		return dao.getPufaByAppId(payConfig);
	}
	
	/**
	 * 获取南粤支付的支付配置
	 * @Title getNanyueByMchId
	 * @Description 
	 * @param mchId
	 * @return
	 */
	public PayConfig getNanyueByMchId(String mchId) {
		PayConfig payConfig = new PayConfig();
		payConfig.setMchId(mchId);
		return dao.getNanyueByMchId(payConfig);
	}
	
}