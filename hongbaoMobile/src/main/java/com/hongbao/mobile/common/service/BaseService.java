package com.hongbao.mobile.common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.utils.IpUtils;

/**
 * Service基类
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	

	/**
	 * request对象
	 */
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 获取真实的ip地址
	 * @Title getRequestIp
	 * @Description 
	 * @return
	 */
	public String getRequestIp() {
		return IpUtils.getRequestIp(request);
	}
	
	/**
	 * 获取request对象
	 * @Title getRequest
	 * @Description 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * 获取session对象
	 * @Title getSession
	 * @Description 
	 * @return
	 */
	public HttpSession getSession() {
		if(getRequest()==null) {
			return null;
		}
		return getRequest().getSession();
	}
	
	/**
	 * 获取渠道信息
	 * @Title getChannel
	 * @Description 
	 * @return
	 */
	public String getChannel() {
		if(getSession()==null) {
			return "0";
		}
		return getSession().getAttribute("channel")!=null?getSession().getAttribute("channel").toString():"0";
	}
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

}
