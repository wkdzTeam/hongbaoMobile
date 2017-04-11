/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.entity;

import java.math.BigDecimal;

import com.hongbao.mobile.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 支付信息Entity
 */
public class PayInfo extends DataEntity<PayInfo> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private String userId;
			
	/**
	 * 红包id
	 */
	private String hongbaoId;
			
	/**
	 * 盾行支付平台流水号
	 */
	private String tradeCode;
	
	/**
	 * 第三方支付平台流水号
	 */
	private String tradePaycode;
			
	/**
	 * 支付标题
	 */
	private String payTitle;
	
	/**
	 * 支付类型（1：公众号支付，2：wap支付，3：余额支付）
	 */
	private String payType;
	
	/**
	 * 支付商家(1：盾行，2：威富通）
	 */
	private String payMerchant;
	
	/**
	 * 支付业务类型
	 */
	private String payBusinessType;
	
	/**
	 * 商户号
	 */
	private String mchId;
			
	/**
	 * 用户ip
	 */
	private String userIp;
			
	/**
	 * 用户城市
	 */
	private String userCity;
			
	/**
	 * 描述
	 */
	private String describe;
			
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 导入标识（0：否，1：是）
	 */
	@JsonIgnore
	private String importFlag;
			
	/**
	 * 渠道ID
	 */
	private String channel;
	
	/**
	 * 用户父类ID
	 */
	private String parentUserid;
	
	/**
	 * 是否扣量:(0：否，1：是)
	 */
	@JsonIgnore
	private String buckleFlag;
	
	public PayInfo() {
		super();
	}

	public PayInfo(String id){
		super(id);
	}

	/**
	 * 获取  用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置  用户id
     * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 获取  红包id
	 */
	public String getHongbaoId() {
		return hongbaoId;
	}

	/**
	 * 设置  红包id
     * @param hongbaoId
	 */
	public void setHongbaoId(String hongbaoId) {
		this.hongbaoId = hongbaoId;
	}
	
	/**
	 * 获取  盾行支付平台流水号
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 设置  盾行支付平台流水号
     * @param tradeCode
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * 获取  第三方支付平台流水号
	 */
	public String getTradePaycode() {
		return tradePaycode;
	}

	/**
	 * 设置  第三方支付平台流水号
     * @param tradePaycode
	 */
	public void setTradePaycode(String tradePaycode) {
		this.tradePaycode = tradePaycode;
	}

	/**
	 * 获取  支付标题
	 */
	public String getPayTitle() {
		return payTitle;
	}

	/**
	 * 设置  支付标题
     * @param payTitle
	 */
	public void setPayTitle(String payTitle) {
		this.payTitle = payTitle;
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
	 * 获取  支付商家(1：盾行，2：威富通）
	 */
	public String getPayMerchant() {
		return payMerchant;
	}

	/**
	 * 设置  支付商家(1：盾行，2：威富通）
     * @param payMerchant
	 */
	public void setPayMerchant(String payMerchant) {
		this.payMerchant = payMerchant;
	}

	/**
	 * 获取  支付业务类型
	 */
	public String getPayBusinessType() {
		return payBusinessType;
	}

	/**
	 * 设置  商户号
     * @param payType
	 */
	public void setPayBusinessType(String payBusinessType) {
		this.payBusinessType = payBusinessType;
	}

	/**
	 * 获取  商户号
	 */
	public String getMchId() {
		return mchId;
	}

	/**
	 * 设置  支付业务类型
     * @param payType
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	/**
	 * 获取  用户ip
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * 设置  用户ip
     * @param userIp
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	/**
	 * 获取  用户城市
	 */
	public String getUserCity() {
		return userCity;
	}

	/**
	 * 设置  用户城市
     * @param userCity
	 */
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	
	/**
	 * 获取  描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * 设置  描述
     * @param describe
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	/**
	 * 获取  金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置  金额
     * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取  导入标识（0：否，1：是）
	 */
	public String getImportFlag() {
		return importFlag;
	}

	/**
	 * 设置  导入标识（0：否，1：是）
     * @param importFlag
	 */
	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}
	
	/**
	 * 获取 渠道号
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * 设置  渠道号
     * @param orderId
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	/**
	 * 获取  是否扣量
	 */
	public String getBuckleFlag() {
		return buckleFlag;
	}

	/**
	 * 设置  是否扣量
     * @param orderId
	 */
	public void setBuckleFlag(String buckleFlag) {
		this.buckleFlag = buckleFlag;
	}

	public String getParentUserid() {
		return parentUserid;
	}

	public void setParentUserid(String parentUserid) {
		this.parentUserid = parentUserid;
	}
	
	
}