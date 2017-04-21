/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.user.entity.UserOauth;
import com.hongbao.mobile.modules.user.dao.UserOauthDao;

/**
 * 用户第三方登录记录Service
 */
@Service
@Transactional(readOnly = true)
public class UserOauthService extends CrudService<UserOauthDao, UserOauth> {

	public UserOauth get(String id) {
		return super.get(id);
	}
	
	public List<UserOauth> findList(UserOauth userOauth) {
		return super.findList(userOauth);
	}
	
	public Page<UserOauth> findPage(Page<UserOauth> page, UserOauth userOauth) {
		return super.findPage(page, userOauth);
	}
	
	@Transactional(readOnly = false)
	public int save(UserOauth userOauth) {
		return super.save(userOauth);
	}
	
	@Transactional(readOnly = false)
	public int delete(UserOauth userOauth) {
		return super.delete(userOauth);
	}
	
	/**
	 * 根据用户id获取用户微信登录记录
	 * @Title getUserOauthByUserIdWeixin
	 * @Description 
	 * @param userId
	 * @return
	 */
	public UserOauth getUserOauthByUserIdWeixin(String userId) {
		UserOauth param = new UserOauth();
		param.setUserId(userId);
		param.setSource("1");
		return this.getUserOauth(param);
	}
	
	/**
	 * 获取用户第三方登录记录
	 * @Title getUserOauth
	 * @Description 
	 * @param param
	 * @return
	 */
	public UserOauth getUserOauth(UserOauth param) {
		List<UserOauth> userOauthList = dao.findList(param);
		if(userOauthList!=null && userOauthList.size()>0) {
			return userOauthList.get(0);
		}
		return null;
	}
	
}