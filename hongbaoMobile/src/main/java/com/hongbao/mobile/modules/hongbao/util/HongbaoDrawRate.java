package com.hongbao.mobile.modules.hongbao.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.hongbao.mobile.common.utils.PropertiesLoader;
import com.hongbao.mobile.common.utils.StringUtils;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawDetail;

/**
 * 转盘红包概率类
 * @ClassName HongbaoDrawRate
 * @Description 
 */
public class HongbaoDrawRate {

	/**
	 * 属性文件加载对象
	 */
	private PropertiesLoader loader;
	
	public HongbaoDrawRate(PropertiesLoader loader) {
		this.loader = loader;
	}

	/**
	 * 保存全局属性值
	 */
	private Map<String, String> map = Maps.newHashMap();
	
	
	/**
	 * 获取配置
	 */
	public String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 红包转盘详情列表map
	 */
	private HashMap<String, List<HongbaoDrawDetail>> hongbaoDrawDetailListMap;
	
	/**
	 * 获取红包转盘详情列表map
	 * @Title getHongbaoDrawDetailListMap
	 * @Description 
	 * @return
	 */
	public synchronized HashMap<String, List<HongbaoDrawDetail>> getHongbaoDrawDetailListMap() {
		if(hongbaoDrawDetailListMap==null) {
			hongbaoDrawDetailListMap = new HashMap<>();
		}
		return hongbaoDrawDetailListMap;
	}
	
	/**
	 * 获取获取红包转盘详情列表
	 * @Title getHongbaoDrawDetailList
	 * @Description 
	 * @param amountType
	 * @param drawType
	 * @return
	 */
	public List<HongbaoDrawDetail> getHongbaoDrawDetailList(String amountType,String drawType) {
		//查询缓存数据
		HashMap<String, List<HongbaoDrawDetail>> hongbaoDrawDetailListMap = getHongbaoDrawDetailListMap();
		String key = amountType+":"+drawType;
		List<HongbaoDrawDetail> hongbaoDrawDetailList = hongbaoDrawDetailListMap.get(key);
		if(hongbaoDrawDetailList!=null && hongbaoDrawDetailList.size()>0) {
			return hongbaoDrawDetailList;
		}
		
		//获取转盘金额数量
		Integer drawCount = Integer.parseInt(getConfig("hongbao.draw.count."+(drawType)));
		//一万
		BigDecimal wan = new BigDecimal(100000);
		//获取红包转盘详情列表
		hongbaoDrawDetailList = new ArrayList<>();
		int rateNum = 1;//概率数字
		for (int i = 0; i < drawCount; i++) {
			int id = i+1;
			//红包转盘详情
			HongbaoDrawDetail hongbaoDrawDetail = new HongbaoDrawDetail();
			//id
			hongbaoDrawDetail.setId(id);
			//获取幸运金额
			BigDecimal luckyAmount = new BigDecimal(getConfig("hongbao.draw.luckyAmount."+(amountType)+"."+(drawType)+"."+(id)));
			hongbaoDrawDetail.setLuckyAmount(luckyAmount);
			//获取概率
			BigDecimal rate = new BigDecimal(getConfig("hongbao.draw.rate."+(amountType)+"."+(drawType)+"."+(id)));
			hongbaoDrawDetail.setRate(rate);
			//概率起始数字
			hongbaoDrawDetail.setBeginNum(rateNum);
			//概率结束数字
			rateNum = rateNum+rate.multiply(wan).intValue();
			hongbaoDrawDetail.setEndNum(rateNum-1);
			
			hongbaoDrawDetailList.add(hongbaoDrawDetail);
		}
		//map中设置
		hongbaoDrawDetailListMap.put(key, hongbaoDrawDetailList);
		
		return hongbaoDrawDetailList;
	}
	
}
