package com.hongbao.mobile.modules.hongbao.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.utils.PropertiesLoader;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawDetail;

/**
 * 红包工具类
 * @ClassName HongbaoUtil
 * @Description 
 */
public class HongbaoDrawRateUtil {
	
	/**
	 * 红包转盘详情列表map
	 */
	private static HashMap<String, HongbaoDrawRate> hongbaoDrawRateMap;
	
	/**
	 * 获取红包转盘详情列表map
	 * @Title getHongbaoDrawDetailListMap
	 * @Description 
	 * @return
	 */
	public synchronized static HashMap<String, HongbaoDrawRate> getHongbaoDrawRateMap() {
		if(hongbaoDrawRateMap==null) {
			hongbaoDrawRateMap = new HashMap<>();
			
			HongbaoDrawRate hongbaoDrawRate = new HongbaoDrawRate(new PropertiesLoader("/properties/hongbaoDrawRate.properties"));
			hongbaoDrawRateMap.put("hongbaoDrawRate", hongbaoDrawRate);
			
			HongbaoDrawRate hongbaoDrawRateNewUser = new HongbaoDrawRate(new PropertiesLoader("/properties/hongbaoDrawRateNewUser.properties"));
			hongbaoDrawRateMap.put("hongbaoDrawRateNewUser", hongbaoDrawRateNewUser);
		}
		return hongbaoDrawRateMap;
	}
	
	/**
	 * 获取红包转盘详情列表
	 * @Title getHongbaoDrawDetailList
	 * @Description 
	 * @param amountType
	 * @param drawType
	 * @return
	 */
	public static List<HongbaoDrawDetail> getHongbaoDrawDetailList(String amountType,String drawType) {
		return getHongbaoDrawDetailList("hongbaoDrawRate", amountType, drawType);
	}

	/**
	 * 获取新用户红包转盘详情列表
	 * @Title getHongbaoDrawDetailNewUserList
	 * @Description 
	 * @param amountType
	 * @param drawType
	 * @return
	 */
	public static List<HongbaoDrawDetail> getHongbaoDrawDetailNewUserList(String amountType,String drawType) {
		return getHongbaoDrawDetailList("hongbaoDrawRateNewUser", amountType, drawType);
	}
	
	/**
	 * 获取转盘红包概率
	 * @Title getHongbaoDrawRate
	 * @Description 
	 * @param rateType
	 * @return
	 */
	public static HongbaoDrawRate getHongbaoDrawRate(String rateType) {
		HashMap<String, HongbaoDrawRate> hongbaoDrawRateMap = getHongbaoDrawRateMap();
		HongbaoDrawRate hongbaoDrawRate = hongbaoDrawRateMap.get(rateType);
		
		return hongbaoDrawRate;
	}
	
	/**
	 * 获取红包转盘详情列表
	 * @Title getHongbaoDrawDetailList
	 * @Description 
	 * @param amountType
	 * @param drawType
	 * @return
	 */
	public static List<HongbaoDrawDetail> getHongbaoDrawDetailList(String rateType,String amountType,String drawType) {
		HongbaoDrawRate hongbaoDrawRate = getHongbaoDrawRate(rateType);
		List<HongbaoDrawDetail> hongbaoDrawDetailList = hongbaoDrawRate.getHongbaoDrawDetailList(amountType, drawType);
		return hongbaoDrawDetailList;
	}
	
	/**
	 * 显示概率详情
	 * @Title showRateDetail
	 * @Description 
	 */
	public static void showRateDetail(String rateType) {
		for (int i = 0; i < 8; i++) {
			Integer amount = Integer.parseInt(Hongbao.getConfig("hongbao.draw.amount."+(i+1)));
			System.out.println("金额："+amount);
			for (int j = 0; j < 3; j++) {
				List<HongbaoDrawDetail> hongbaoDrawDetailList = getHongbaoDrawDetailList(rateType,(i+1)+"", (j+1)+"");
				System.out.println(JSONArray.fromObject(hongbaoDrawDetailList));
			}
			System.out.println();
		}
	}
	
	/**
	 * 显示概率
	 * @Title showRate
	 * @Description 
	 * @param hongbaoDrawRate
	 */
	public static void showRate(HongbaoDrawRate hongbaoDrawRate) {
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 8; i++) {
				Integer amount = Integer.parseInt(Hongbao.getConfig("hongbao.draw.amount."+(i+1)));
				System.out.println("金额："+amount);
				Integer drawCount = Integer.parseInt(hongbaoDrawRate.getConfig("hongbao.draw.count."+(j+1)));
				String name = "";
				if(j+1==1) {
					name="小圈";
				}
				if(j+1==2) {
					name="中圈";
				}
				if(j+1==3) {
					name="大圈";
				}
				for (int k = 0; k < drawCount; k++) {
					BigDecimal luckyAmount = new BigDecimal(hongbaoDrawRate.getConfig("hongbao.draw.luckyAmount."+(i+1)+"."+(j+1)+"."+(k+1)));
					BigDecimal rate = new BigDecimal(hongbaoDrawRate.getConfig("hongbao.draw.rate."+(i+1)+"."+(j+1)+"."+(k+1)));
					
					System.out.println("【"+name+"】金额：【"+luckyAmount+"】，概率：【"+rate+"%】");
				}
				System.out.println();
			}
		}
	}
	

	public static void main(String[] args) {
//		showRateDetail("hongbaoDrawRate");
		showRateDetail("hongbaoDrawRateNewUser");
	}
	
	public static void main1(String[] args) {
//		HongbaoDrawRate hongbaoDrawRate = getHongbaoDrawRate("hongbaoDrawRate");
//		showRate(hongbaoDrawRate);
		HongbaoDrawRate hongbaoDrawRateNewUser = getHongbaoDrawRate("hongbaoDrawRateNewUser");
		showRate(hongbaoDrawRateNewUser);
	}
}
