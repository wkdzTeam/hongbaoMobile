/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.util.UserBalanceUtil;
import com.hongbao.mobile.modules.user.util.UserYongjinUtil;
import com.hongbao.mobile.modules.yongjin.dao.YongjinInfoDao;
import com.hongbao.mobile.modules.yongjin.entity.YongjinInfo;

/**
 * 佣金信息Service
 */
@Service
@Transactional(readOnly = true)
public class YongjinInfoService extends CrudService<YongjinInfoDao, YongjinInfo> {

	public YongjinInfo get(String id) {
		return super.get(id);
	}
	
	public List<YongjinInfo> findList(YongjinInfo yongjinInfo) {
		return super.findList(yongjinInfo);
	}
	
	public Page<YongjinInfo> findPage(Page<YongjinInfo> page, YongjinInfo yongjinInfo) {
		return super.findPage(page, yongjinInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(YongjinInfo yongjinInfo) {
		return super.save(yongjinInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(YongjinInfo yongjinInfo) {
		return super.delete(yongjinInfo);
	}
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 给用户反佣金
	 * @param userId  用户id
	 * @param amount  金额（元）
	 */
	public void createYongjin(String userId, BigDecimal amount){
		try {
			//获取当前用户
			UserInfo current_user = userInfoService.get(userId);
			//获取父类id
			String parentUserId_1 = current_user.getParentUserid();
			//父类用户ID-1
			if( !parentUserId_1.equals("0") ){
				UserInfo user_1 = userInfoService.get(parentUserId_1);
				if(user_1 != null ){
					float balance_1 = (user_1.getBalance().floatValue()) + 0.25f;
					System.out.println("user_1.getBalance().floatValue():"+(user_1.getBalance().floatValue())+",balance_1:"+balance_1);
					//user_1.setBalance(new BigDecimal(balance_1));
					//userInfoService.update(user_1);
					UserBalanceUtil.changeBalance(user_1.getId(), new BigDecimal("+0.25"),"佣金1",getRequest());
					
					//父类用户ID-2
					String parentUserId_2 = user_1.getParentUserid();
					if( !parentUserId_2.equals("0") ){
						UserInfo user_2 = userInfoService.get(parentUserId_2);
						if(user_2 != null ){
							float balance_2 = (user_2.getBalance().floatValue()) + 0.10f;
							System.out.println("user_2.getBalance().floatValue():"+(user_2.getBalance().floatValue())+",balance_2:"+balance_2);
							//user_2.setBalance(new BigDecimal(balance_2));
							//userInfoService.update(user_2);
							UserBalanceUtil.changeBalance(user_2.getId(), new BigDecimal("+0.10"),"佣金2",getRequest());
							
							//父类用户ID-3
							String parentUserId_3 = user_2.getParentUserid();
							if( !parentUserId_3.equals("0") ){
								UserInfo user_3 = userInfoService.get(parentUserId_3);
								if(user_3 != null ){
									float balance_3 = (user_3.getBalance().floatValue()) + 0.05f;
									System.out.println("user_3.getBalance().floatValue():"+(user_3.getBalance().floatValue())+",balance_3:"+balance_3);
									//user_3.setBalance(new BigDecimal(balance_3));
									//userInfoService.update(user_3);
									UserBalanceUtil.changeBalance(user_3.getId(), new BigDecimal("+0.05"),"佣金3",getRequest());
								}
							}
						}
					}
				}
			}else{
				System.out.println("not parentUserId_one userId:"+userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("yongjinService createYongjin error:" + e);
		}
	}
	
	
	/**
	 * 转盘给用户反佣金
	 * @param userId  用户id
	 * @param payType   支付类型  1、公众号支付   2、其他   3、余额支付
	 * @param hongbaoNo  红包编号
	 * @param amount  金额（元）
	 */
	@Transactional
	public void DrawYongjin(String userId, String payType,String hongbaoNo, BigDecimal amount){
		try {
			//TODO 机率给用户用户
			//余额支付  扣量20% 充值支付。。不扣佣金
			if( payType != null && payType.equals("3")){
				String key_scale = JedisUtils.KEY_PREFIX+":yongjin.scale";
				int scale  = 20; //给用户的比例
				if( JedisUtils.exists( key_scale ) ){
					scale = Integer.parseInt( JedisUtils.get(key_scale) );
				}else{
					JedisUtils.set(key_scale, scale+"");
				}
				Random random = new Random();
				int number = random.nextInt(100) + 1; //1-100
				if(number < scale){  //随机数
					logger.info("userId:"+userId+",hongbaoNo:"+hongbaoNo+",amount:"+amount+",deduction........");
					return;
				}
			}
			
			//获取当前用户
			UserInfo current_user = userInfoService.get(userId);
			//获取父类id
			String parentUserId_1 = current_user.getParentUserid();
			//父类用户ID-1
			if( !parentUserId_1.equals("0") && parentUserId_1.length() > 2 ){
				UserInfo user_1 = userInfoService.get(parentUserId_1);
				if(user_1 != null ){
					System.out.println("user_temp.getBalance().floatValue():"+(user_1.getBalance().floatValue()) +",balance_2:"+ amount.floatValue() * 0.05f);
					//给用户反充值金额的5%
					//UserBalanceUtil.changeBalance(user_1.getId(), new BigDecimal("+"+(amount.floatValue() * 0.05f)),"转盘佣金1",getRequest());
					UserYongjinUtil.changeYongjin(user_1.getId(), new BigDecimal("+"+(amount.floatValue() * 0.05f)),"转盘佣金1",getRequest());
					insertYongjin(user_1, current_user, hongbaoNo, amount, 0.05f); //第一级5个点
					
					String parentUserId_2 = user_1.getParentUserid();
					if( !parentUserId_2.equals("0") ){
						UserInfo user_2 = userInfoService.get(parentUserId_2);
						if(user_2 != null ){
							System.out.println("user_2.getBalance().floatValue():"+(user_2.getBalance().floatValue())+",balance_2:"+ amount.floatValue() * 0.01f);
							//给用户反充值金额的1%
							//UserBalanceUtil.changeBalance(user_2.getId(), new BigDecimal("+"+(amount.floatValue() * 0.01f)),"转盘佣金2",getRequest());
							UserYongjinUtil.changeYongjin(user_2.getId(), new BigDecimal("+"+(amount.floatValue() * 0.01f)),"转盘佣金2",getRequest());
							insertYongjin(user_2, current_user, hongbaoNo, amount, 0.01f); //第一级3个点
							
							//父类用户ID-3   占时不给第三级用户
							/*String parentUserId_3 = user_2.getParentUserid();
							if( !parentUserId_3.equals("0") ){
								UserInfo user_3 = userInfoService.get(parentUserId_3);
								if(user_3 != null ){
									System.out.println("user_3.getBalance().floatValue():"+(user_3.getBalance().floatValue())+",balance_3:"+ amount.floatValue() * 0.01f);
									//给用户反充值金额的1%
									userInfoService.changeBalance(user_3.getId(), new BigDecimal("+"+(amount.floatValue() * 0.01f)),"转盘佣金3");
									insertYongjin(user_3, current_user, hongbaoNo, amount, 0.01f); //第一级1个点
								}
							}*/
						}
					}
				}
			}else{
				System.out.println("not parentUserId userId:"+userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("yongjinService DrawYongjin error:" + e);
		}
	}
	
	/**
	 * 格式化字符串
	 */
	private static java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
	 
	
	/**
	 * 添加佣金记录表
	 * @param userInfo 用户信息
	 * @param hongbaoNo 红包编号
	 * @param amount 金额
	 * @param scale  比例  0.05f
	 */
	private void insertYongjin(UserInfo userInfo, UserInfo userInfoOrigin, String hongbaoNo, BigDecimal amount, float scale){
		YongjinInfo yongjinInfo = new YongjinInfo();
		yongjinInfo.setId(IdGen.uuid());
		yongjinInfo.setUserId(userInfo.getId());
		yongjinInfo.setUserNo(userInfo.getUserNo());
		yongjinInfo.setHongbaoNo(hongbaoNo); //红包编号
		yongjinInfo.setAmount((amount.floatValue() * scale) +"");
		
		yongjinInfo.setUserIdOrigin(userInfoOrigin.getId());
		yongjinInfo.setUserNoOrigin(userInfoOrigin.getUserNo());
		yongjinInfo.setDelFlag("0");
		insert(yongjinInfo);
		try {
			//TODO 添加佣金总和到redis里面
			String yongjin_key = JedisUtils.KEY_PREFIX + ":yongjin:" + userInfo.getUserNo();
			if(JedisUtils.exists(yongjin_key)){
				String yongjin = df.format( Float.parseFloat( JedisUtils.get(yongjin_key) )  + (amount.floatValue() * scale) );
				JedisUtils.set(yongjin_key, yongjin);
			}else{
				String yongjin = df.format( amount.floatValue() * scale);
				JedisUtils.set(yongjin_key, yongjin);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		float balance_one = (new BigDecimal("23.23").floatValue()) + 0.05f;
		
		System.out.println(balance_one);
	}
	
	
	/**
	 * 根据用户获取佣金记录
	 * @param hongbaoInfo
	 * @return
	 */
	public List<YongjinInfo> getYongjinListByUser(Map map){
		return dao.getYongjinListByUser(map);
	}
	
	/**
	 * 根据用户获取总佣金
	 * @param map
	 * @return
	 */
	public HashMap<String, Object> getYongjinSumByUser(Map map){
		return dao.getYongjinSumByUser(map);
	}
}
