/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.sys.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.sys.entity.Log;

/**
 * 日志DAO接口
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
