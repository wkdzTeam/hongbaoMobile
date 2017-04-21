/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.CacheUtils;
import com.hongbao.mobile.modules.sys.dao.DictDao;
import com.hongbao.mobile.modules.sys.entity.Dict;
import com.hongbao.mobile.modules.sys.utils.DictUtils;

/**
 * 字典Service
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public int save(Dict dict) {
		int count = super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
		return count;
	}

	@Transactional(readOnly = false)
	public int delete(Dict dict) {
		int count = super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
		return count;
	}

}
