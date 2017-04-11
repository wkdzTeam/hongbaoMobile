/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/hongbao.mobile">JeeSite</a> All rights reserved.
 */
package com.hongbao.mobile.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.sys.dao.LogDao;
import com.hongbao.mobile.modules.sys.entity.Log;

/**
 * 日志Service
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
	
}
