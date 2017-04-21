package com.hongbao.mobile.modules.yongjin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.service.BaseService;
import com.hongbao.mobile.modules.yongjin.dao.DuihuanInfoDao;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;

/**
 * 兑换更新Service
 * @ClassName DuihuanInfoUpdateService
 * @Description 
 */
@Service
@Transactional(readOnly = true)
public class DuihuanInfoUpdateService extends BaseService {
	
	/**
	 * 兑换DAO接口
	 */
	@Autowired
	private DuihuanInfoDao duihuanInfoDao;
	
	/**
	 * 新增，独立事务
	 * @Title insertNow
	 * @Description 
	 * @param duihuanInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	public int insertNow(DuihuanInfo duihuanInfo) {
		duihuanInfo.preInsert();
		return duihuanInfoDao.insert(duihuanInfo);
	}
	
	/**
	 * 修改，独立事务
	 * @Title updateNow
	 * @Description 
	 * @param duihuanInfo
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	public int updateNow(DuihuanInfo duihuanInfo) {
		duihuanInfo.preUpdate();
		return duihuanInfoDao.update(duihuanInfo);
	}
	
}
