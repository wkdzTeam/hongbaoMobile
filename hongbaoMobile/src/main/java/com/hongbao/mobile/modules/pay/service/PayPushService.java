/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.pay.entity.PayPush;
import com.hongbao.mobile.modules.pay.dao.PayPushDao;

/**
 * 支付推送Service
 */
@Service
@Transactional(readOnly = true)
public class PayPushService extends CrudService<PayPushDao, PayPush> {

	public PayPush get(String id) {
		return super.get(id);
	}
	
	public List<PayPush> findList(PayPush payPush) {
		return super.findList(payPush);
	}
	
	public Page<PayPush> findPage(Page<PayPush> page, PayPush payPush) {
		return super.findPage(page, payPush);
	}
	
	@Transactional(readOnly = false)
	public int save(PayPush payPush) {
		return super.save(payPush);
	}
	
	@Transactional(readOnly = false)
	public int delete(PayPush payPush) {
		return super.delete(payPush);
	}
	
	/**
	 * 修改支付标识
	 * @Title updatePayFlag
	 * @Description 
	 * @param payPush
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updatePayFlag(PayPush payPush) {
		return dao.updatePayFlag(payPush);
	}
	
	
}