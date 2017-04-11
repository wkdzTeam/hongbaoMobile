/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.hongbao.entity.TransferInfo;

/**
 * 转账信息DAO接口
 */
@MyBatisDao
public interface TransferInfoDao extends CrudDao<TransferInfo> {
	
}