package com.hongbao.mobile.common.utils;

import net.sf.json.JSONObject;

/**
 * 验证工具类
 * @ClassName: ValidateUtils   
 * @Description: TODO  
 */
public class ValidateUtils {
	
	/**
	 * 验证为空
	 * @Title: validateEmpty   
	 * @Description: TODO  
	 * @param objs
	 * @return boolean  
	 */
	public static boolean validateEmpty(Object... objs) {
		boolean result = true;
		for (Object obj : objs) {
			if(obj!=null) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 验证不为空
	 * @Title: validateNotEmpty   
	 * @Description: TODO  
	 * @param objs
	 * @return boolean  
	 */
	public static boolean validateNotEmpty(Object... objs) {
		boolean result = true;
		for (Object obj : objs) {
			if(obj==null) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 验证为空或空字符串
	 * @Title: validateBlank   
	 * @Description: TODO  
	 * @param objs
	 * @return boolean  
	 */
	public static boolean validateBlank(Object... objs) {
		boolean result = true;
		for (Object obj : objs) {
			if(obj!=null && !"".equals(obj.toString())) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 验证不为空或空字符串
	 * @Title: validateNotBlank   
	 * @Description: TODO  
	 * @param objs
	 * @return boolean  
	 */
	public static boolean validateNotBlank(Object... objs) {
		boolean result = true;
		for (Object obj : objs) {
			if(obj==null || "".equals(obj.toString())) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 验证JSONObject中是否存在某字段
	 * @Title checkJsonField
	 * @Description 
	 * @param jobj
	 * @param field
	 * @return
	 */
	public static boolean checkJsonField(JSONObject jobj,String field) {
		if(jobj.containsKey(field) && !"".equals(jobj.getString(field))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(!validateNotBlank("d","d","f"));
	}
}
