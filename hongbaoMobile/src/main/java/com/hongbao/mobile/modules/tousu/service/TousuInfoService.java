/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.tousu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.tousu.entity.TousuInfo;
import com.hongbao.mobile.modules.tousu.dao.TousuInfoDao;

/**
 * 用户投诉Service
 */
@Service
@Transactional(readOnly = true)
public class TousuInfoService extends CrudService<TousuInfoDao, TousuInfo> {

	public TousuInfo get(String id) {
		return super.get(id);
	}
	
	public List<TousuInfo> findList(TousuInfo tousuInfo) {
		return super.findList(tousuInfo);
	}
	
	public Page<TousuInfo> findPage(Page<TousuInfo> page, TousuInfo tousuInfo) {
		return super.findPage(page, tousuInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(TousuInfo tousuInfo) {
		return super.save(tousuInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(TousuInfo tousuInfo) {
		return super.delete(tousuInfo);
	}
	
}