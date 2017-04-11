package com.hongbao.mobile.common.config;

import net.sf.json.JSONObject;

import com.hongbao.mobile.common.mapper.JsonMapper;

/**
 * 返回代码实体类
 * @ClassName: ResultCode   
 * @Description: TODO  
 * @date:2015年12月14日 上午12:56:26
 */
public class ResultCode implements Cloneable{
	
	private String code;
	
	private String msg;
	
	/**
	 * 构造函数
	 * @param code
	 * @param msg
	 */
	public ResultCode(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	
	/**
	 * 构造函数
	 * @param code
	 */
	public ResultCode(String code) {
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		JSONObject msgJobj = toJsonObject();
		return msgJobj.toString();
	}

	public String toString(String msg) {
		JSONObject msgJobj = toJsonObject(msg);
		return msgJobj.toString();
	}
	
	/**
	 * 对象转成JSONObject
	 * @Title: toJsonObject   
	 * @Description: TODO  
	 * @return JSONObject  
	 */
	public JSONObject toJsonObject() {
		return JsonMapper.toJSONObject(this);
	}
	
	/**
	 * 对象转成JSONObject
	 * @Title: toJsonObject   
	 * @Description: TODO  
	 * @param msg
	 * @return JSONObject  
	 */
	public JSONObject toJsonObject(String msg) {
		JSONObject msgJobj = toJsonObject();
		msgJobj.put("msg", msg);
		return msgJobj;
	}
	
	/**
	 * 对象克隆
	 * @Title clone
	 * @Description 
	 * @date 2016年6月6日 上午1:08:57
	 * @see java.lang.Object#clone()
	 * @return
	 */
	public ResultCode clone() {
		try {
			return (ResultCode)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
