package com.hongbao.mobile.modules.pay.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.mapper.JsonMapper;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.service.PayNotify;

public class PayUtil {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(PayUtil.class);
	
	/**
	 * 支付通知map
	 */
	private static HashMap<String, PayNotify> payNotifyMap = new HashMap<>();
	
	/**
	 * 获取或设置支付通知信息
	 * @Title getSetPayNotify
	 * @Description 
	 * @param hongbaoNo
	 * @return
	 */
	public synchronized static PayNotify getSetPayNotify(String hongbaoNo) {
		if(payNotifyMap.containsKey(hongbaoNo)) {
			return payNotifyMap.get(hongbaoNo);
		} else {
			PayNotify payNotify = new PayNotify(hongbaoNo);
			setPayNotify(hongbaoNo, payNotify);
			return payNotify;
		}
	}
	
	/**
	 * 设置支付通知信息
	 * @Title setPayNotify
	 * @Description 
	 * @param hongbaoNo
	 * @param payDrawNotify
	 */
	public synchronized static void setPayNotify(String hongbaoNo,PayNotify payDrawNotify) {
		payNotifyMap.put(hongbaoNo, payDrawNotify);
	}
	
	/**
	 * 删除支付通知信息
	 * @Title removePayNotify
	 * @Description 
	 * @param hongbaoNo
	 * @param payDrawNotify
	 */
	public synchronized static void removePayNotify(String hongbaoNo) {
		payNotifyMap.remove(hongbaoNo);
	}
	
	/**
	 * 获取当前支付配置
	 * @Title getPayConfig
	 * @Description 
	 * @return
	 */
	public static PayConfig getPayConfig() {
		PayConfig payConfig = null;
		try {
			String payConfigStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":payConfig");
			payConfig = (PayConfig)JsonMapper.fromJsonString(payConfigStr, PayConfig.class);
		} catch (Exception e) {
			logger.error("获取支付配置失败："+e.getMessage());
		}
		return payConfig;
	}
	
	/**
	 * 获取的用户当前支付的openId
	 * @Title getPayOpenId
	 * @Description 
	 * @return
	 */
	public static String getPayOpenId(String userId) {
		String openId = null;
		try {
			//获取当前配置
			PayConfig payConfig = getPayConfig();
			//获取当前配置支付的openId
			openId = JedisUtils.get(JedisUtils.KEY_PREFIX+":payOpenId:"+userId+":"+payConfig.getId());
		} catch (Exception e) {
			logger.error("获取支付openid失败："+e.getMessage());
		}
		return openId;
	}
	
}
