package com.hongbao.mobile.modules.pay.entity;

public class PayDataInfo {
	/**
	 * 支付url
	 */
	private String payUrl;
	
	/**
	 * 威富通的预支付ID
	 */
	private String tokenId;
	
	/**
	 * 获取  支付url
	 */
	public String getPayUrl() {
		return payUrl;
	}

	/**
	 * 设置  支付payUrl
     * @param payUrl
	 */
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	/**
	 * 获取  威富通的预支付ID
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * 设置  威富通的预支付ID
     * @param tokenId
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
}
