/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.user.dao.UserInfoDao;
import com.hongbao.mobile.modules.user.entity.UserInfo;

/**
 * 用户信息Service
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {
	
	/**
	 * 查询用户信息
	 * @Title getUserInfo
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	public List<UserInfo> getUserInfo(UserInfo userInfo) {
		return dao.getUserInfo(userInfo);
	}
	
	/**
	 * 根据openid查询用户信息
	 * @Title getByOpenId
	 * @Description 
	 * @param openId
	 * @return
	 */
    public UserInfo getByOpenId(String openId) {
    	return dao.getByOpenId(openId);
    }
    
    /**
     * 根据用户编号查询
     * @Title getByUserNo
     * @Description 
     * @param userNo
     * @return
     */
    public UserInfo getByUserNo(String userNo) {
    	return dao.getByUserNo(userNo);
    }

	public UserInfo get(String id) {
		return super.get(id);
	}
	
	public List<UserInfo> findList(UserInfo userInfo) {
		return super.findList(userInfo);
	}
	
	public Page<UserInfo> findPage(Page<UserInfo> page, UserInfo userInfo) {
		return super.findPage(page, userInfo);
	}
	
	/**
	 * 生成用户编号
	 * @Title makeUserNo
	 * @Description 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String makeUserNo() {
		//获取下一个用户编号
		String userNo = dao.getUserNoNext();
		return userNo;
	}
	
	/**
	 * 保存用户
	 * @Title insert
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insert(UserInfo userInfo) {
		//保存
		return super.insert(userInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(UserInfo userInfo) {
		return super.save(userInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(UserInfo userInfo) {
		return super.delete(userInfo);
	}

	/**
	 * 根据序列查询用户信息
	 * @Title getByIndex
	 * @Description 
	 * @param index
	 * @return
	 */
    public UserInfo getByIndex(int index) {
    	return dao.getByIndex(index);
    }
    
    /**
     * 查询用户总数
     * @Title getCount
     * @Description 
     * @return
     */
    public Integer getCount() {
    	return dao.getCount();
    }

	/**
	 * 修改余额
	 * @Title update
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateBalance(UserInfo userInfo) {
		userInfo.preUpdate();
		return dao.updateBalance(userInfo);
	}
	
	
	/**
	 * 修改余额
	 * @Title update
	 * @Description 
	 * @param userInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateYongjin(UserInfo userInfo) {
		userInfo.preUpdate();
		return dao.updateYongjin(userInfo);
	}
	
    
    /**
     * 修改用户信息，新事务
     * @Title updateNow
     * @Description 
     * @param userInfo
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateNow(UserInfo userInfo) {
    	userInfo.preUpdate();
		return dao.update(userInfo);
	}
    
    /**
     * 修改登录
     * @Title updateLogin
     * @Description 
     * @param userInfo
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateLogin(UserInfo userInfo) {
    	userInfo.preUpdate();
		return dao.updateLogin(userInfo);
	}
    
    /**
     * 修改openid2
     * @Title updateOpenId2
     * @Description 
     * @param userInfo
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateOpenId2(UserInfo userInfo) {
    	userInfo.preUpdate();
		return dao.updateOpenId2(userInfo);
    }
	
	
	/**
	 * 得到用户的中奖记录
	 * @param id
	 * @return
	 */
	public UserInfo getBylucky(String id) {
		return dao.getByLucky(id);
	}

}