/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.pay.entity.PayPush;

/**
 * 支付推送DAO接口
 */
@MyBatisDao
public interface PayPushDao extends CrudDao<PayPush> {
	
	/**
	 * 修改支付标识
	 * @Title updatePayFlag
	 * @Description 
	 * @param payPush
	 * @return
	 */
	public Integer updatePayFlag(PayPush payPush);
	
}