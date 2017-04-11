/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.pay.entity.PayInfo;

/**
 * 支付信息DAO接口
 */
@MyBatisDao
public interface PayInfoDao extends CrudDao<PayInfo> {
	/**
	 * 根据期号查询没有中奖的支付信息
	 * @param itemStageId
	 * @param luckOrderId
	 * @return
	 */
	List<PayInfo> getByItemStageNotLucky(@Param(value="itemStageId")String itemStageId,
			@Param(value="luckOrderId")String luckOrderId);

}