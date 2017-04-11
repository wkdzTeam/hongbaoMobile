/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;

/**
 * 红包信息DAO接口
 */
@MyBatisDao
public interface HongbaoInfoDao extends CrudDao<HongbaoInfo> {
		
	
	/**
	 * 根据用户获取中奖记录
	 * @param hongbaoInfo
	 * @return
	 */
	public List<HongbaoInfo> getHongBaoListByUser(Map map);
	
	/**
	 * 获取未打开的红包
	 * @Title getUnPay
	 * @Description 
	 * @param hongbaoInfo
	 * @return
	 */
	public List<HongbaoInfo> getUnOpen(HongbaoInfo hongbaoInfo);
	
	/**
	 * 获取未支付的红包
	 * @Title getUnPay
	 * @Description 
	 * @param hongbaoInfo
	 * @return
	 */
	public List<HongbaoInfo> getUnPay(HongbaoInfo hongbaoInfo);
	
	/**
	 * 获取下一个红包编号
	 * @Title getHongbaoNoNext
	 * @Description 
	 * @return
	 */
    public String getHongbaoNoNext();
    
    /**
     * 获取当前红包编号
     * @Title getHongbaoNoCurr
     * @Description 
     * @return
     */
    public String getHongbaoNoCurr();
}