/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.tousu.entity;

import org.hibernate.validator.constraints.Length;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 用户投诉Entity
 */
public class TousuInfo extends DataEntity<TousuInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	private String userNo;
			
	/**
	 * 微信号/QQ/邮箱
	 */
	private String number;
			
	/**
	 * 投诉内容
	 */
	private String content;
			
	/**
	 * 类型
	 */
	private String type;
			
	
	public TousuInfo() {
		super();
	}

	public TousuInfo(String id){
		super(id);
	}

	@Length(min=0, max=20, message="用户编号长度必须介于 0 和 20 之间")
	/**
	 * 获取  用户编号
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 设置  用户编号
     * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	@Length(min=0, max=50, message="微信号/QQ/邮箱长度必须介于 0 和 50 之间")
	/**
	 * 获取  微信号/QQ/邮箱
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置  微信号/QQ/邮箱
     * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=200, message="投诉内容长度必须介于 0 和 200 之间")
	/**
	 * 获取  投诉内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置  投诉内容
     * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	/**
	 * 获取  类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置  类型
     * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}