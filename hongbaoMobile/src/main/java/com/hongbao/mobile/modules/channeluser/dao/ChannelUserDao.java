/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.channeluser.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.channeluser.entity.ChannelUser;

/**
 * 渠道信息DAO接口
 */
@MyBatisDao
public interface ChannelUserDao extends CrudDao<ChannelUser> {
	
}