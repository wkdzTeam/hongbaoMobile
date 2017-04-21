/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.dao;

import java.util.List;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.user.entity.UserInfo;

/**
 * 用户信息DAO接口
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {
		
	/**
	 * 修改openid2
	 * @Title updateOpenId2
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	public Integer updateOpenId2(UserInfo userInfo);

	/**
	 * 修改登录
	 * @Title updateLogin
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	public Integer updateLogin(UserInfo userInfo);
	
	/**
	 * 修改余额
	 * @Title updateBalance
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	public Integer updateBalance(UserInfo userInfo);
	
	/**
	 * 根据openid查询用户信息
	 * @Title getByOpenId
	 * @Description 
	 * @param openId
	 * @return
	 */
    public UserInfo getByOpenId(String openId);
    
    /**
     * 根据用户编号查询
     * @Title getByUserNo
     * @Description 
     * @param userNo
     * @return
     */
    public UserInfo getByUserNo(String userNo);
	
	/**
	 * 根据序列查询用户信息
	 * @Title getByIndex
	 * @Description 
	 * @param index
	 * @return
	 */
    public UserInfo getByIndex(int index);
    
    /**
     * 查询用户总数
     * @Title getCount
     * @Description 
     * @return
     */
    public Integer getCount();
	
	/**
	 * 查询用户信息
	 * @Title getUserInfo
	 * @Description 
	 * @param userInfo
	 * @return
	 */
    public List<UserInfo> getUserInfo(UserInfo userInfo);
    
    /**
     * 获取下一个用户编号
     * @Title getUserNoNext
     * @Description 
     * @return
     */
    public String getUserNoNext();
    
    /**
     * 获取当前用户编号
     * @Title getUserNoCurr
     * @Description 
     * @return
     */
    public String getUserNoCurr();
    
    /**
     * 得到用户的中奖记录
     * @param id
     * @return
     */
	public UserInfo getByLucky(String id);
	
	/**
	 * 修改佣金
	 * @Title updateYongjin
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	public Integer updateYongjin(UserInfo userInfo);
}