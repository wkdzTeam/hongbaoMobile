/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.entity;

import org.hibernate.validator.constraints.Length;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 支付推送Entity
 */
public class PayPush extends DataEntity<PayPush> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商户号
	 */
	private String mchId;
			
	/**
	 * 支付商家
	 */
	private String payMerchant;
			
	/**
	 * 推送报文
	 */
	private String pushData;
			
	/**
	 * 支付标识
	 */
	private String payFlag;
			
	/**
	 * 服务器ip
	 */
	private String serverIp;
			
	
	public PayPush() {
		super();
	}

	public PayPush(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商户号长度必须介于 0 和 64 之间")
	/**
	 * 获取  商户号
	 */
	public String getMchId() {
		return mchId;
	}

	/**
	 * 设置  商户号
     * @param mchId
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	
	@Length(min=0, max=5, message="支付商家长度必须介于 0 和 5 之间")
	/**
	 * 获取  支付商家
	 */
	public String getPayMerchant() {
		return payMerchant;
	}

	/**
	 * 设置  支付商家
     * @param payMerchant
	 */
	public void setPayMerchant(String payMerchant) {
		this.payMerchant = payMerchant;
	}
	
	@Length(min=0, max=5000, message="推送报文长度必须介于 0 和 5000 之间")
	/**
	 * 获取  推送报文
	 */
	public String getPushData() {
		return pushData;
	}

	/**
	 * 设置  推送报文
     * @param pushData
	 */
	public void setPushData(String pushData) {
		this.pushData = pushData;
	}
	
	@Length(min=0, max=5, message="支付标识长度必须介于 0 和 5 之间")
	/**
	 * 获取  支付标识
	 */
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * 设置  支付标识
     * @param payFlag
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	
	@Length(min=0, max=50, message="服务器ip长度必须介于 0 和 50 之间")
	/**
	 * 获取  服务器ip
	 */
	public String getServerIp() {
		return serverIp;
	}

	/**
	 * 设置  服务器ip
     * @param serverIp
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
}