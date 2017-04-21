package com.hongbao.mobile.modules.hongbao.entity;

import java.math.BigDecimal;

/**
 * 红包转盘详情
 * @ClassName HongbaoDrawDetail
 * @Description 
 */
public class HongbaoDrawDetail {
	//id
	private Integer id;
	//幸运金额
	private BigDecimal luckyAmount;
	//概率
	private BigDecimal rate;
	//概率起始数字
	private int beginNum;
	//概率结束数字
	private int endNum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getLuckyAmount() {
		return luckyAmount;
	}
	public void setLuckyAmount(BigDecimal luckyAmount) {
		this.luckyAmount = luckyAmount;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public int getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(int beginNum) {
		this.beginNum = beginNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
}

