/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.user.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.user.entity.UserOauth;

/**
 * 用户第三方登录记录DAO接口
 */
@MyBatisDao
public interface UserOauthDao extends CrudDao<UserOauth> {
	
}