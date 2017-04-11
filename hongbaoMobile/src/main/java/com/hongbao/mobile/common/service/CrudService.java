package com.hongbao.mobile.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.DataEntity;
import com.hongbao.mobile.common.persistence.Page;

/**
 * Service基类
 * @ClassName: CrudService   
 * @Description: TODO  
 * @param <D>
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}

	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity) {
		return dao.findAllList(entity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int save(T entity) {
		int count = 0;
		if (entity.getIsNewRecord()){
			entity.preInsert();
			count = dao.insert(entity);
		}else{
			entity.preUpdate();
			count = dao.update(entity);
		}
		return count;
	}
	
	/**
	 * 新增数据
	 * @Title insert
	 * @Description 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insert(T entity) {
		entity.preInsert();
		return dao.insert(entity);
	}
	
	/**
	 * 修改数据
	 * @Title update
	 * @Description 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(T entity) {
		entity.preUpdate();
		return dao.update(entity);
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public int delete(T entity) {
		return dao.delete(entity);
	}

}
