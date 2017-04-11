/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.dao;

import java.util.List;
import java.util.Map;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;

/**
 * 红包转盘信息DAO接口
 */
@MyBatisDao
public interface HongbaoDrawInfoDao extends CrudDao<HongbaoDrawInfo> {
	/**
	 * 获取未打开的红包
	 * @Title getUnPay
	 * @Description 
	 * @param hongbaoDrawInfo
	 * @return
	 */
	public List<HongbaoDrawInfo> getUnOpen(HongbaoDrawInfo hongbaoDrawInfo);
	
	/**
	 * 获取未支付的红包
	 * @Title getUnPay
	 * @Description 
	 * @param hongbaoDrawInfo
	 * @return
	 */
	public List<HongbaoDrawInfo> getUnPay(HongbaoDrawInfo hongbaoDrawInfo);
	
	/**
	 * 获取下一个红包转盘编号
	 * @Title getHongbaoDrawNoNext
	 * @Description 
	 * @return
	 */
    public String getHongbaoDrawNoNext();
    
    /**
     * 获取当前红包转盘编号
     * @Title getHongbaoDrawNoCurr
     * @Description 
     * @return
     */
    public String getHongbaoDrawNoCurr();
    
    
    /**
     * 根据用户去查询子类的红包
     * @param map (parentuserid)
     * @return
     */
    public List<HongbaoDrawInfo> getListHongbaoDrawByUser(Map map);
}