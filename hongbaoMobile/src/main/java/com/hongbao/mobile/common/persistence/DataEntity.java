/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.common.persistence;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hongbao.mobile.common.utils.IdGen;

/**
 * 数据Entity类
 */
public abstract class DataEntity<T> extends BaseEntity<T> implements Cloneable {

	private static final long serialVersionUID = 1L;
	
	protected Long createDate;	// 创建日期
	protected Long updateDate;	// 更新日期
	
	@JsonIgnore
	protected String delFlag; 	// 删除标记（0：正常；1：删除）
	
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		//判断是否存在id
		if (StringUtils.isBlank(this.id)){
			setId(IdGen.uuid());
		}
		Long now = System.currentTimeMillis();
		if(this.updateDate==null) {
			this.updateDate = now;
		}
		if(this.createDate==null) {
			this.createDate = now;
		}
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		Long now = System.currentTimeMillis();
		this.updateDate = now;
	}
	
	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 对象克隆
	 * @Title clone
	 * @Description 
	 * @see java.lang.Object#clone()
	 * @return
	 */
	@Override  
    public Object clone() {  
    	T obj = null;  
        try {  
        	obj = (T)super.clone();  
        } catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }
        return obj;  
    } 
}
