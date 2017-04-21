package com.hongbao.mobile.modules.weixin.exception;

/**
 * 微信异常
 * @ClassName WeixinException
 * @Description 
 */
public class WeixinException extends RuntimeException {
	private static final long serialVersionUID = 5607354945446232851L;
	
	public WeixinException(String msg) {
		super(msg);
	}
}