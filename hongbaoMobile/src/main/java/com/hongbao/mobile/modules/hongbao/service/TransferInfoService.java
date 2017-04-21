/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.hongbao.entity.TransferInfo;
import com.hongbao.mobile.modules.hongbao.dao.TransferInfoDao;

/**
 * 转账信息Service
 */
@Service
@Transactional(readOnly = true)
public class TransferInfoService extends CrudService<TransferInfoDao, TransferInfo> {

	public TransferInfo get(String id) {
		return super.get(id);
	}
	
	public List<TransferInfo> findList(TransferInfo transferInfo) {
		return super.findList(transferInfo);
	}
	
	public Page<TransferInfo> findPage(Page<TransferInfo> page, TransferInfo transferInfo) {
		return super.findPage(page, transferInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(TransferInfo transferInfo) {
		return super.save(transferInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(TransferInfo transferInfo) {
		return super.delete(transferInfo);
	}
	
	/**
	 * 新增，独立事务
	 * @Title insertNow
	 * @Description 
	 * @param transferInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	public int insertNow(TransferInfo transferInfo) {
		transferInfo.preInsert();
		return dao.insert(transferInfo);
	}
	
	/**
	 * 修改，独立事务
	 * @Title updateNow
	 * @Description 
	 * @param transferInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	public int updateNow(TransferInfo transferInfo) {
		transferInfo.preUpdate();
		return dao.update(transferInfo);
	}
}