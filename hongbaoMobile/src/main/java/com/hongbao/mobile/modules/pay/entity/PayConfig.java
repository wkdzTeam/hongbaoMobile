/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.entity;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 支付配置Entity
 */
public class PayConfig extends DataEntity<PayConfig> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 配置名称
	 */
	private String configName;
	
	/**
	 * 支付商家（1：盾行，2：威富通）
	 */
	private String payMerchant;
			
	/**
	 * 支付类型（1：公众号支付，2：wap支付，3：余额支付）
	 */
	private String payType;
			
	/**
	 * 应用ID
	 */
	private String appId;
			
	/**
	 * 应用密钥
	 */
	private String appSecret;
			
	/**
	 * 威富通商户号
	 */
	private String mchId;
			
	/**
	 * 交易密钥
	 */
	private String key;
			
	/**
	 * 商品编号
	 */
	private String itemNo;
			
	/**
	 * 银行编号
	 */
	private String bankId;
			
	/**
	 * 请求url
	 */
	private String reqUrl;
			
	/**
	 * 支付url
	 */
	private String payUrl;
			
	/**
	 * 签名验证url
	 */
	private String signUrl;
			
	/**
	 * 微信回调域名
	 */
	private String weixinCallbackDomain;
			
	/**
	 * 异步通知url
	 */
	private String asyncNotifyUrl;
			
	/**
	 * 同步通知url
	 */
	private String syncNotifyUrl;
			
	/**
	 * 转盘异步通知url
	 */
	private String drawAsyncNotifyUrl;
			
	/**
	 * 转盘同步通知url
	 */
	private String drawSyncNotifyUrl;
	
	/**
	 * 回调域名
	 */
	private String callbackDomain;
	
	/**
	 * 订单前缀
	 */
	private String orderPrefix;
	
	/**
	 * 使用标志
	 */
	private String useFlag;
			
	
	public PayConfig() {
		super();
	}

	public PayConfig(String id){
		super(id);
	}

	/**
	 * 获取  支付商家（1：盾行，2：威富通）
	 */
	public String getPayMerchant() {
		return payMerchant;
	}

	/**
	 * 设置  支付商家（1：盾行，2：威富通）
     * @param payMerchant
	 */
	public void setPayMerchant(String payMerchant) {
		this.payMerchant = payMerchant;
	}
	
	/**
	 * 获取  支付类型（1：公众号支付，2：wap支付，3：余额支付）
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * 设置  支付类型（1：公众号支付，2：wap支付，3：余额支付）
     * @param payType
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	/**
	 * 获取  应用ID
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * 设置  应用ID
     * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	/**
	 * 获取  应用密钥
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * 设置  应用密钥
     * @param appSecret
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	/**
	 * 获取  威富通商户号
	 */
	public String getMchId() {
		return mchId;
	}

	/**
	 * 设置  威富通商户号
     * @param mchId
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	
	/**
	 * 获取  交易密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置  交易密钥
     * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 获取  商品编号
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * 设置  商品编号
     * @param itemNo
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	/**
	 * 获取  银行编号
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * 设置  银行编号
     * @param bankId
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	/**
	 * 获取  请求url
	 */
	public String getReqUrl() {
		return reqUrl;
	}

	/**
	 * 设置  请求url
     * @param reqUrl
	 */
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	
	/**
	 * 获取  支付url
	 */
	public String getPayUrl() {
		return payUrl;
	}

	/**
	 * 设置  支付url
     * @param payUrl
	 */
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	
	/**
	 * 获取  签名验证url
	 */
	public String getSignUrl() {
		return signUrl;
	}

	/**
	 * 设置  签名验证url
     * @param signUrl
	 */
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	
	/**
	 * 获取  微信回调域名
	 */
	public String getWeixinCallbackDomain() {
		return weixinCallbackDomain;
	}

	/**
	 * 设置  微信回调域名
     * @param weixinCallbackDomain
	 */
	public void setWeixinCallbackDomain(String weixinCallbackDomain) {
		this.weixinCallbackDomain = weixinCallbackDomain;
	}
	
	/**
	 * 获取  异步通知url
	 */
	public String getAsyncNotifyUrl() {
		return asyncNotifyUrl;
	}

	/**
	 * 设置  异步通知url
     * @param asyncNotifyUrl
	 */
	public void setAsyncNotifyUrl(String asyncNotifyUrl) {
		this.asyncNotifyUrl = asyncNotifyUrl;
	}
	
	/**
	 * 获取  同步通知url
	 */
	public String getSyncNotifyUrl() {
		return syncNotifyUrl;
	}

	/**
	 * 设置  同步通知url
     * @param syncNotifyUrl
	 */
	public void setSyncNotifyUrl(String syncNotifyUrl) {
		this.syncNotifyUrl = syncNotifyUrl;
	}
	
	/**
	 * 获取  转盘异步通知url
	 */
	public String getDrawAsyncNotifyUrl() {
		return drawAsyncNotifyUrl;
	}
	
	public String getPayDrawAsyncNotifyUrl() {
		//获取域名
		String domain = getCallbackDomain();
		String domainUrl = "http://"+domain+drawAsyncNotifyUrl;
		return domainUrl;
	}

	/**
	 * 设置  转盘异步通知url
     * @param drawAsyncNotifyUrl
	 */
	public void setDrawAsyncNotifyUrl(String drawAsyncNotifyUrl) {
		this.drawAsyncNotifyUrl = drawAsyncNotifyUrl;
	}
	
	/**
	 * 获取  转盘同步通知url
	 */
	public String getDrawSyncNotifyUrl() {
		return drawSyncNotifyUrl;
	}

	public String getPayDrawSyncNotifyUrl() {
		//获取域名
		String domain = Hongbao.getDomain();
		String domainUrl = "http://"+domain+drawSyncNotifyUrl;
		return domainUrl;
	}

	/**
	 * 设置  转盘同步通知url
     * @param drawSyncNotifyUrl
	 */
	public void setDrawSyncNotifyUrl(String drawSyncNotifyUrl) {
		this.drawSyncNotifyUrl = drawSyncNotifyUrl;
	}

	/**
	 * 获取  使用标志
	 */
	public String getUseFlag() {
		return useFlag;
	}

	/**
	 * 设置  使用标志
     * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getCallbackDomain() {
		return callbackDomain;
	}

	public void setCallbackDomain(String callbackDomain) {
		this.callbackDomain = callbackDomain;
	}

	public String getOrderPrefix() {
		return orderPrefix;
	}

	public void setOrderPrefix(String orderPrefix) {
		this.orderPrefix = orderPrefix;
	}
	
}