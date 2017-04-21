package com.hongbao.mobile.common.exception;

import com.hongbao.mobile.common.config.ResultCode;

/**
 * Hongbao异常
 * @ClassName HongbaoException
 * @Description 
 */
public class HongbaoException extends RuntimeException{//继承RuntimeException使其抛出异常能触发事务回滚
	
	private static final long serialVersionUID = -2901609651550143066L;
	
	/**
	 * 错误代码
	 */
	private ResultCode resultCode;
	
	/**
	 * 构造函数
	 * @Title DuobaoException
	 * @Description 
	 * @param resultCode
	 */
	public HongbaoException(ResultCode resultCode) {
		this.resultCode = resultCode.clone();
	}
	
	/**
	 * 构造函数
	 * @Title DuobaoException
	 * @Description 
	 * @param resultCode
	 * @param msg
	 */
	public HongbaoException(ResultCode resultCode,String msg) {
		this.resultCode = resultCode.clone();
		this.resultCode.setMsg(msg);
	}
	
	/**
	 * 获取返回json
	 * @Title getResultJson
	 * @Description 
	 * @return
	 */
	public String getResultJson() {
		return this.resultCode.toString();
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	
	
}
