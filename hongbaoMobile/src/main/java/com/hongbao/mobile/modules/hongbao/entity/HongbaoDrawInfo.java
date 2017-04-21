/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.entity;

import java.math.BigDecimal;

import com.hongbao.mobile.common.persistence.DataEntity;
import com.hongbao.mobile.modules.pay.entity.PayInfo;

/**
 * 红包转盘信息Entity
 */
public class HongbaoDrawInfo extends DataEntity<HongbaoDrawInfo> {
	
	private static final long serialVersionUID = 1L;
	
	//支付信息
	private PayInfo payInfo;

	public PayInfo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}
	
	/**
	 * 红包转盘编号
	 */
	private String hongbaoDrawNo;
			
	/**
	 * 用户id
	 */
	private String userId;
			
	/**
	 * 转盘类型
	 */
	private String drawType;
			
	/**
	 * 金额类型
	 */
	private String amountType;
			
	/**
	 * 投注金额
	 */
	private BigDecimal amount;
			
	/**
	 * 幸运号码
	 */
	private Integer luckyNum;
			
	/**
	 * 开奖金额
	 */
	private BigDecimal luckyAmount;
			
	/**
	 * 打开标识
	 */
	private String openFlag;
			
	/**
	 * 打开时间
	 */
	private Long openDate;
			
	/**
	 * 支付id
	 */
	private String payId;
			
	/**
	 * 支付类型（1：公众号支付，2：wap支付，3：余额支付）
	 */
	private String payType;
	
	/**
	 * 支付商家(1：盾行，2：威富通）
	 */
	private String payMerchant;
			
	/**
	 * 支付url
	 */
	private String payUrl;
			
	/**
	 * 威富通的预支付ID
	 */
	private String payTokenId;
			
	/**
	 * 支付标识
	 */
	private String payFlag;
			
	/**
	 * 支付时间
	 */
	private Long payDate;
			
	/**
	 * 渠道ID
	 */
	private String channel;
	
	/**
	 * 用户父类ID
	 */
	private String parentUserid;
			
	/**
	 * 导入标识（0：否，1：是）
	 */
	private String importFlag;
			
	
	public HongbaoDrawInfo() {
		super();
	}

	public HongbaoDrawInfo(String id){
		super(id);
	}

	/**
	 * 获取  红包转盘编号
	 */
	public String getHongbaoDrawNo() {
		return hongbaoDrawNo;
	}

	/**
	 * 设置  红包转盘编号
     * @param hongbaoDrawNo
	 */
	public void setHongbaoDrawNo(String hongbaoDrawNo) {
		this.hongbaoDrawNo = hongbaoDrawNo;
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
	 * 获取  转盘类型
	 */
	public String getDrawType() {
		return drawType;
	}

	/**
	 * 设置  转盘类型
     * @param drawType
	 */
	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}
	
	/**
	 * 获取  金额类型
	 */
	public String getAmountType() {
		return amountType;
	}

	/**
	 * 设置  金额类型
     * @param amountType
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	
	/**
	 * 获取  投注金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置  投注金额
     * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 获取  幸运号码
	 */
	public Integer getLuckyNum() {
		return luckyNum;
	}

	/**
	 * 设置  幸运号码
     * @param luckyNum
	 */
	public void setLuckyNum(Integer luckyNum) {
		this.luckyNum = luckyNum;
	}
	
	/**
	 * 获取  开奖金额
	 */
	public BigDecimal getLuckyAmount() {
		return luckyAmount;
	}

	/**
	 * 设置  开奖金额
     * @param luckyAmount
	 */
	public void setLuckyAmount(BigDecimal luckyAmount) {
		this.luckyAmount = luckyAmount;
	}
	
	/**
	 * 获取  打开标识
	 */
	public String getOpenFlag() {
		return openFlag;
	}

	/**
	 * 设置  打开标识
     * @param openFlag
	 */
	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}
	
	/**
	 * 获取  打开时间
	 */
	public Long getOpenDate() {
		return openDate;
	}

	/**
	 * 设置  打开时间
     * @param openDate
	 */
	public void setOpenDate(Long openDate) {
		this.openDate = openDate;
	}
	
	/**
	 * 获取  支付id
	 */
	public String getPayId() {
		return payId;
	}

	/**
	 * 设置  支付id
     * @param payId
	 */
	public void setPayId(String payId) {
		this.payId = payId;
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
	 * 获取  支付url
	 */
	public String getPayUrl() {
		//设置支付信息
//		String payJson = PayDunxingUtil.createHongbaoDrawPayJson(Hongbao.NAME+"-draw-order-"+this.getHongbaoDrawNo(), "大赚盘"+this.getAmount().intValue()+"元", this.getAmount()).toString();
//		this.payUrl = PayDunxingUtil.getPayApiUrl()+"?Pay="+payJson;
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
	 * 获取  威富通的预支付ID
	 */
	public String getPayTokenId() {
		return payTokenId;
	}

	/**
	 * 设置  威富通的预支付ID
     * @param payTokenId
	 */
	public void setPayTokenId(String payTokenId) {
		this.payTokenId = payTokenId;
	}
	
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
	
	/**
	 * 获取  支付时间
	 */
	public Long getPayDate() {
		return payDate;
	}

	/**
	 * 设置  支付时间
     * @param payDate
	 */
	public void setPayDate(Long payDate) {
		this.payDate = payDate;
	}
	
	/**
	 * 获取  渠道ID
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * 设置  渠道ID
     * @param channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getParentUserid() {
		return parentUserid;
	}

	public void setParentUserid(String parentUserid) {
		this.parentUserid = parentUserid;
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
	
}