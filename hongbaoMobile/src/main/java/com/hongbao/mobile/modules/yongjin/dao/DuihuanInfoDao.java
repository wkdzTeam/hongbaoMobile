/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.dao;

import java.util.List;
import java.util.Map;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;

/**
 * 兑换DAO接口
 */
@MyBatisDao
public interface DuihuanInfoDao extends CrudDao<DuihuanInfo> {
	
	/**
	 * 根据用户获取兑换记录
	 * @param hongbaoInfo
	 * @return
	 */
	public List<DuihuanInfo> getDuihuanListByUser(Map map);
	
}