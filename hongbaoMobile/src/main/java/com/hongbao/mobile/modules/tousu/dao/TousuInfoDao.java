/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.tousu.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.tousu.entity.TousuInfo;

/**
 * 用户投诉DAO接口
 */
@MyBatisDao
public interface TousuInfoDao extends CrudDao<TousuInfo> {
	
}