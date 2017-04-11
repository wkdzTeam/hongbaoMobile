/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.yongjin.entity.YongjinInfo;

/**
 * 佣金信息DAO接口
 */
@MyBatisDao
public interface YongjinInfoDao extends CrudDao<YongjinInfo> {
	
	
	/**
	 * 根据用户获取佣金记录
	 * @param map
	 * @return
	 */
	public List<YongjinInfo> getYongjinListByUser(Map map);
	
	
	/**
	 * 根据用户获取总佣金
	 * @param map
	 * @return
	 */
	public HashMap<String, Object> getYongjinSumByUser(Map map);
	
}