/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.channeluser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.channeluser.entity.ChannelUser;
import com.hongbao.mobile.modules.channeluser.dao.ChannelUserDao;

/**
 * 渠道信息Service
 */
@Service
@Transactional(readOnly = true)
public class ChannelUserService extends CrudService<ChannelUserDao, ChannelUser> {

	public ChannelUser get(String id) {
		return super.get(id);
	}
	
	public List<ChannelUser> findList(ChannelUser channelUser) {
		return super.findList(channelUser);
	}
	
	public Page<ChannelUser> findPage(Page<ChannelUser> page, ChannelUser channelUser) {
		return super.findPage(page, channelUser);
	}
	
	@Transactional(readOnly = false)
	public int save(ChannelUser channelUser) {
		return super.save(channelUser);
	}
	
	@Transactional(readOnly = false)
	public int delete(ChannelUser channelUser) {
		return super.delete(channelUser);
	}
	
}