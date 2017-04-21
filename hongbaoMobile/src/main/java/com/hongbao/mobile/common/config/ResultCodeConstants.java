package com.hongbao.mobile.common.config;


/**
 * 返回代码
 * @ClassName: ResultCodeConstants   
 * @Description: TODO  
 */
public class ResultCodeConstants {
	

	/**
	 * 0:请求成功
	 */
	public static final ResultCode C0 = new ResultCode("0");
	
	/*
	 * 定义：4位整数
	 * 前两位代表模块编号，后两位代表异常编号
	 */
	
	/*****************************************系统异常10xx begin*****************************************/
	/**
	 * 1001:请求参数错误
	 */
	public static final ResultCode C1001 = new ResultCode("1001","请求参数错误");
	
	/**
	 * 1002:签名验证失败
	 */
	public static final ResultCode C1002 = new ResultCode("1002","签名验证失败");
	
	/**
	 * 1003:请求接口不存在
	 */
	public static final ResultCode C1003 = new ResultCode("1003","请求接口不存在");
	
	/**
	 * 1004:请求接口未启用
	 */
	public static final ResultCode C1004 = new ResultCode("1004","请求接口未启用");
	
	/**
	 * 1005:系统异常
	 */
	public static final ResultCode C1005 = new ResultCode("1005","系统异常");
	
	/**
	 * 1006:请求客户端不存在
	 */
	public static final ResultCode C1006 = new ResultCode("1006","请求客户端不存在");
	
	/**
	 * 1007:请求客户端未启用
	 */
	public static final ResultCode C1007 = new ResultCode("1007","请求客户端未启用");
	
	/**
	 * 1008:请求已过期
	 */
	public static final ResultCode C1008 = new ResultCode("1008","请求过期");
	
	/**
	 * 1009:用户登录异常
	 */
	public static final ResultCode C1009 = new ResultCode("1009","用户登录异常");
	/*****************************************系统异常10xx end*****************************************/
	

	/*****************************************业务相关10xx begin*****************************************/
	/**
	 * 0011:必填项为空
	 */
	public static final ResultCode C0011 = new ResultCode("0011","必填项为空");
	
	/**
	 * 0012:操作失败
	 */
	public static final ResultCode C0012 = new ResultCode("0012","操作失败");
	
	/**
	 * 0013:查询数据为空
	 */
	public static final ResultCode C0013 = new ResultCode("0013","查询数据为空");
	
	/**
	 * 0014:数据超上限
	 */
	public static final ResultCode C0014 = new ResultCode("0014","数据超上限");
	/*****************************************业务异常10xx end*****************************************/
	
	
	/*****************************************用户相关11xx begin*****************************************/
	/**
	 * 1101:该用户已注册
	 */
	public static final ResultCode C1101 = new ResultCode("1101","该用户已注册");
	
	/**
	 * 1102:用户不存在
	 */
	public static final ResultCode C1102 = new ResultCode("1102","用户不存在");
	
	/**
	 * 1103:密码错误
	 */
	public static final ResultCode C1103 = new ResultCode("1103","密码错误");
	
	/**
	 * 1104:登录超时
	 */
	public static final ResultCode C1104 = new ResultCode("1104","登录超时");
	
	/**
	 * 1105:短信验证码错误
	 */
	public static final ResultCode C1105 = new ResultCode("1105","短信验证码错误");
	
	/**
	 * 1106:短信验证码超时
	 */
	public static final ResultCode C1106 = new ResultCode("1106","短信验证码超时");
	
	/**
	 * 1107:余额不足
	 */
	public static final ResultCode C1107 = new ResultCode("1107","余额不足");
	
	/**
	 * 1108:openId为空
	 */
	public static final ResultCode C1108 = new ResultCode("1108","openId为空");
	
	/*****************************************用户相关11xx end*****************************************/
}
