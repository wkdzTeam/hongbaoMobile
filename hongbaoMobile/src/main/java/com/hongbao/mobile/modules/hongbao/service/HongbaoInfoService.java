/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.UUIDGenerator;
import com.hongbao.mobile.modules.channeluser.entity.ChannelUser;
import com.hongbao.mobile.modules.channeluser.service.ChannelUserService;
import com.hongbao.mobile.modules.hongbao.dao.HongbaoInfoDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;
import com.hongbao.mobile.modules.pay.entity.PayDataInfo;
import com.hongbao.mobile.modules.pay.entity.PayInfo;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

/**
 * 红包信息Service
 */
@Service
@Transactional(readOnly = true)
public class HongbaoInfoService extends CrudService<HongbaoInfoDao, HongbaoInfo> {
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 用户第三方登录记录Service
	 */
	@Autowired
	private UserOauthService userOauthService;
	
	/**
	 * 支付信息Service
	 */
	@Autowired
	private PayInfoService payInfoService;
	
	/**
	 * 转账信息Service
	 */
	@Autowired
	private TransferInfoService transferInfoService;
	
	/**
	 * 渠道
	 */
	@Autowired
	private ChannelUserService channelUserService;
	
	/**
	 * 佣金信息Service
	 */
	@Autowired
	private YongjinInfoService yongjinInfoService;
	
	/**
	 * 扣量变量
	 */
	public static HashMap<String, Integer> map_deducted = new HashMap<String, Integer>();
	
	/**
	 * 线程池
	 */
    private ExecutorService threadPool = Executors.newFixedThreadPool(30);

	public HongbaoInfo get(String id) {
		return super.get(id);
	}
	
	public List<HongbaoInfo> findList(HongbaoInfo hongbaoInfo) {
		return super.findList(hongbaoInfo);
	}
	
	public Page<HongbaoInfo> findPage(Page<HongbaoInfo> page, HongbaoInfo hongbaoInfo) {
		return super.findPage(page, hongbaoInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(HongbaoInfo hongbaoInfo) {
		return super.save(hongbaoInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(HongbaoInfo hongbaoInfo) {
		return super.delete(hongbaoInfo);
	}
	
	/**
	 * 根据用户获取中奖记录
	 * @param hongbaoInfo
	 * @return
	 */
	public List<HongbaoInfo> getHongBaoListByUser(Map map){
		return dao.getHongBaoListByUser(map);
	}
	
	
	/**
	 * 获取未支付的信息 
	 * @Title getUnPay
	 * @Description 
	 * @return
	 */
	public HongbaoInfo getUnPay(String amountType,String userId) {
		HongbaoInfo param = new HongbaoInfo();
		param.setUserId(userId);//用户id
		param.setAmountType(amountType);//金额类型
		//获取超时时间
		Long timeout = new Long(Hongbao.getConfig("order.timeout"));
		param.setCreateDate(System.currentTimeMillis()-(timeout*1000));
		List<HongbaoInfo> hongbaoInfoList = dao.getUnPay(param);
		if(hongbaoInfoList!=null && hongbaoInfoList.size()>0) {
			return hongbaoInfoList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取未打开的红包
	 * @Title getUnOpen
	 * @Description 
	 * @param userId
	 * @return
	 */
	public HongbaoInfo getUnOpen(String userId) {
		HongbaoInfo param = new HongbaoInfo();
		param.setUserId(userId);//用户id
		List<HongbaoInfo> hongbaoInfoList = dao.getUnOpen(param);
		if(hongbaoInfoList!=null && hongbaoInfoList.size()>0) {
			return hongbaoInfoList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据红包编号查询
	 * @Title getByHongbaoNo
	 * @Description 
	 * @param hongbaoNo
	 * @return
	 */
	public HongbaoInfo getByHongbaoNo(String hongbaoNo) {
		HongbaoInfo param = new HongbaoInfo();
		param.setHongbaoNo(hongbaoNo);
		List<HongbaoInfo> hongbaoInfoList = dao.findList(param);
		if(hongbaoInfoList!=null && hongbaoInfoList.size()>0) {
			return hongbaoInfoList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取下一个红包编号
	 * @Title makeHongbaoNo
	 * @Description 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String makeHongbaoNo() {
		//获取下一个红包编号
		String hongbaoNo = dao.getHongbaoNoNext();
		return hongbaoNo;
	}
	
	/**
	 * 生成支付信息
	 * @Title makePayInfo
	 * @Description 
	 * @param amountType
	 * @param amount
	 * @param payType
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoInfo makePayInfo(String amountType,Integer amount,String payType) throws HongbaoException {
		if(StringUtils.isBlank(amountType)) {
			throw new HongbaoException(ResultCodeConstants.C0012,"错误的金额");
		}
		if(amount==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"错误的金额");
		}
		//判断金额和类型是否匹配
		Integer configAmount = Integer.parseInt(Hongbao.getConfig("hongbao.amount."+amountType));
		if(configAmount.intValue()!=amount.intValue()) {
			throw new HongbaoException(ResultCodeConstants.C0012,"金额不匹配");
		}
		//获取session中的用户信息
		UserInfo userInfoSession = (UserInfo)getRequest().getSession().getAttribute("userInfo");
		if(userInfoSession==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		//查询用户信息
		UserInfo userInfo = userInfoService.get(userInfoSession.getId());
		if(userInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		
		//获取渠道
		String channel = getChannel();
		//用户父类ID
		String parentUserid = userInfo.getParentUserid();
		
		//查询是否已创建未过期的支付信息
		HongbaoInfo unPayHongbaoInfo = this.getUnPay(amountType,userInfo.getId());
		if(unPayHongbaoInfo!=null) {
			//获取旧渠道
			String channelOld = unPayHongbaoInfo.getChannel();
			//重新设置渠道
			if(!channel.equals(channelOld)) {
				unPayHongbaoInfo.setChannel(channel);
				unPayHongbaoInfo.setParentUserid(parentUserid);
				this.update(unPayHongbaoInfo);
			}
			return unPayHongbaoInfo;
		}
		
		Long now = System.currentTimeMillis();
		
		//创建红包订单信息
		HongbaoInfo hongbaoInfo = new HongbaoInfo();
		hongbaoInfo.setId(IdGen.uuid());//id
//		hongbaoInfo.setHongbaoNo(makeHongbaoNo());//订单编号
		hongbaoInfo.setHongbaoNo(UUIDGenerator.generate());//订单编号
		hongbaoInfo.setUserId(userInfo.getId());//用户id
		hongbaoInfo.setAmountType(amountType);//金额类型
		hongbaoInfo.setAmount(new BigDecimal(amount));//金额
		hongbaoInfo.setLuckyAmount(BigDecimal.ZERO);//开奖金额
		hongbaoInfo.setOpenFlag("0");//打开标识
		hongbaoInfo.setPayType(payType);//支付类型（1：公众号支付，2：wap支付，3：余额支付）
		hongbaoInfo.setPayMerchant("2");//威富通支付
		hongbaoInfo.setPayFlag("0");//支付标识
		hongbaoInfo.setChannel(channel);//渠道
		hongbaoInfo.setParentUserid(parentUserid);
		
		hongbaoInfo.setCreateDate(now);//创建时间
		hongbaoInfo.setUpdateDate(now);//修改时间
		
		JSONObject callbackParamJobj = new JSONObject();
		callbackParamJobj.put("hongbaoNo", hongbaoInfo.getHongbaoNo());
		String callbackParam = callbackParamJobj.toString();
		
		//创建威富通支付信息
		PayDataInfo payWeifutongInfo = null;
		//设置支付url
		hongbaoInfo.setPayUrl(payWeifutongInfo.getPayUrl());
		//威富通的预支付ID
		hongbaoInfo.setPayTokenId(payWeifutongInfo.getTokenId());

		//保存订单信息
		super.insert(hongbaoInfo);
		logger.info("红包【"+hongbaoInfo.getHongbaoNo()+"】创建完毕，用户编号【"+userInfo.getUserNo()+"】，渠道【"+ hongbaoInfo.getChannel() +"】");
		
		return hongbaoInfo;
	}
	
	/**
	 * 完成支付订单信息
	 * @Title finishPayOrder
	 * @Description 
	 * @param hongbaoNo
	 * @param tradeCode
	 * @param tradePaycode
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoInfo finishPayOrder(String hongbaoNo,String tradeCode,String tradePaycode) throws HongbaoException {
		HongbaoInfo hongbaoInfo = this.getByHongbaoNo(hongbaoNo);
		return finishPayOrder(hongbaoInfo, tradeCode,tradePaycode);
	}
	
	/**
	 * 完成支付订单信息
	 * @Title finishPayOrder
	 * @Description 
	 * @param hongbaoInfo
	 * @param tradeCode
	 * @param tradePaycode
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoInfo finishPayOrder(HongbaoInfo hongbaoInfo,String tradeCode,String tradePaycode) throws HongbaoException {
		if(hongbaoInfo==null || StringUtils.isBlank(hongbaoInfo.getId())) {
			throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
		}
		//订单状态为待支付
		if(!"1".equals(hongbaoInfo.getPayFlag())) {
			Long now = System.currentTimeMillis();
			
			//创建支付信息
			PayInfo payInfo = new PayInfo();
			payInfo.setUserId(hongbaoInfo.getUserId());//用户id
			payInfo.setHongbaoId(hongbaoInfo.getId());//红包id
			payInfo.setTradeCode(tradeCode);//盾行支付平台流水号
			payInfo.setTradePaycode(tradePaycode);//第三方支付平台流水号
			payInfo.setPayTitle("拼手气"+hongbaoInfo.getAmount().intValue()+"元");//支付标题
			payInfo.setPayType(hongbaoInfo.getPayType());//支付类型（1：公众号支付，2：wap支付，3：余额支付）
			payInfo.setPayBusinessType("1");//普通红包
			payInfo.setDescribe("拼手气"+hongbaoInfo.getAmount().intValue()+"元");//描述
			payInfo.setAmount(hongbaoInfo.getAmount());//金额
			payInfo.setChannel(StringUtils.isNotBlank(hongbaoInfo.getChannel()) ?hongbaoInfo.getChannel() : "0");//从红包中获取渠道号
			payInfo.setParentUserid(StringUtils.isNotBlank(hongbaoInfo.getParentUserid()) ? hongbaoInfo.getParentUserid() : "0"); //获取用户id
			payInfo.setPayMerchant(hongbaoInfo.getPayMerchant());//支付商家
			
			UserInfo userInfo = userInfoService.get(hongbaoInfo.getUserId());//用户id
			//是否扣量
			//TODO  未实现比例扣量
			String buckleFlag = "0"; //默认不扣量
			try {
				//渠道扣量设置
				if(map_deducted.containsKey(payInfo.getChannel())){
					//如果还没有扣到量
					if(buckleFlag.equals("0") && map_deducted.get(payInfo.getChannel()) > 10 && Integer.parseInt(payInfo.getChannel()) > 0){
						ChannelUser channelUser = channelUserService.get(payInfo.getChannel());
						if(channelUser != null){
							String deductedScale = channelUser.getDeductedScale();
							int number = ThreadLocalRandom.current().nextInt(1, 101);
							if(number >= Integer.parseInt(deductedScale)){
								buckleFlag = "1";
							}
						}
					}else{
						map_deducted.put( payInfo.getChannel(), map_deducted.get(payInfo.getChannel()) + 1 );
					}
				}else{
					map_deducted.put(payInfo.getChannel(), 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			payInfo.setBuckleFlag(buckleFlag);
			
			//保存支付信息
			payInfoService.insert(payInfo);
			
			//修改订单信息
			hongbaoInfo.setPayFlag("1");//状态【已支付】
			hongbaoInfo.setPayId(payInfo.getId());//支付id
			hongbaoInfo.setPayDate(now);//支付时间
			//计算开奖金额
			this.calcLuckyAmount(hongbaoInfo,userInfo);
			//保存红包信息
			this.update(hongbaoInfo);
			//设置支付信息
			hongbaoInfo.setPayInfo(payInfo);
			
			//修改用户信息
			userInfo.setJoinItemCount(userInfo.getJoinItemCount()+1);
			userInfoService.updateNow(userInfo);
			
	        //创建转账线程
			Thread hongbaoDrawTransferThread = new YongjinThread(yongjinInfoService, userInfo.getId(), hongbaoInfo.getAmount());
			//执行线程
			threadPool.execute(hongbaoDrawTransferThread);
		}
		
		return hongbaoInfo;
	}
	
	/**
	 * 打开红包
	 * @Title openHongbao
	 * @Description 
	 * @param hongbaoId
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoInfo openHongbao(String hongbaoId) throws HongbaoException {
		HongbaoInfo hongbaoInfo = null;
		
		//获取session中的用户信息
		UserInfo userInfoSession = (UserInfo)getRequest().getSession().getAttribute("userInfo");
		if(userInfoSession==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		
		//查询红包信息
		hongbaoInfo = this.get(hongbaoId);
		if(hongbaoInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包不存在");
		}
		//判断是否已支付
		if(!hongbaoInfo.getPayFlag().equals("1")) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包未支付");
		}
		//判断是否已打开
		if(!hongbaoInfo.getOpenFlag().equals("0")) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包已被打开");
		}
		//查询用户信息
		UserInfo userInfo = userInfoService.get(userInfoSession.getId());
		if(userInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
		}
		//判断是否为当前用户的红包
		if(!hongbaoInfo.getUserId().equals(userInfo.getId())) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的红包");
		}
		Long now = System.currentTimeMillis();
		
		//修改红包信息
		hongbaoInfo.setOpenFlag("1");//已打开
		hongbaoInfo.setOpenDate(now);//打开时间
		
		//修改红包信息
		this.update(hongbaoInfo);
		
        //创建转账线程
		Thread hongbaoTransferThread = new HongbaoTransferThread(hongbaoInfo,userInfo, userInfo.getOpenId() , transferInfoService, logger);
		//执行线程
		threadPool.execute(hongbaoTransferThread);
		
		return hongbaoInfo;
	}
	
	/**
	 * 计算开奖金额
	 * @Title calcLuckyAmount
	 * @Description 
	 * @param hongbaoInfo
	 * @throws HongbaoException
	 */
	public void calcLuckyAmount(HongbaoInfo hongbaoInfo,UserInfo userInfo) throws HongbaoException {
		//获取金额类型
		String amountType = hongbaoInfo.getAmountType();
		//获取金额最大值
		Integer configLuckyAmount = Integer.parseInt(Hongbao.getConfig("hongbao.luckyAmount."+amountType));

		//创建随机数
		Random random = new Random();
		//幸运金额
		BigDecimal luckyAmount = new BigDecimal(0);
		
		//用户参加拼手气次数
		Integer joinItemCount = userInfo.getJoinItemCount()!=null?userInfo.getJoinItemCount():0;
		//生成1-1000的数字
		int luckyNum = random.nextInt(1000)+1;
		//10元
		if(amountType.equals("1")) {
			//1-3.99
			if(luckyNum>=1 && luckyNum<=700) {
				luckyAmount = makeRandomAmount(100, 399);
			}
			//4-5.99
			else if(luckyNum>=701 && luckyNum<=980) {
				luckyAmount = makeRandomAmount(400, 599);
			}
			//6-10.00
			else if(luckyNum>=981 && luckyNum<=995) {
				luckyAmount = makeRandomAmount(600, 1000);
			}
			//10.01-12.00
			else if(luckyNum>=996 && luckyNum<=1000) {
				luckyAmount = makeRandomAmount(1001, 1200);
			}
		}
		//30元
		else if (amountType.equals("2")) {
			//4-9.99
			if(luckyNum>=1 && luckyNum<=100) {
				luckyAmount = makeRandomAmount(400, 999);
			}
			//10-15.99
			else if(luckyNum>=101 && luckyNum<=700) {
				luckyAmount = makeRandomAmount(1000, 1599);
			}
			//16-29.99
			else if(luckyNum>=701 && luckyNum<=850) {
				luckyAmount = makeRandomAmount(1600, 2999);
			}
			//30-35.99
			else if(luckyNum>=851 && luckyNum<=950) {
				luckyAmount = makeRandomAmount(3000, 3599);
			}
			//36-49.99
			else if(luckyNum>=951 && luckyNum<=990) {
				luckyAmount = makeRandomAmount(3600, 4999);
			}
			//50-200
			else if(luckyNum>=991 && luckyNum<=1000) {
				luckyAmount = makeRandomAmount(5000, 20000);
			}
		}
		//50元
		else if (amountType.equals("3")) {
			//4-15.99
			if(luckyNum>=1 && luckyNum<=350) {
				luckyAmount = makeRandomAmount(400, 1599);
			}
			//16-29.99
			else if(luckyNum>=351 && luckyNum<=600) {
				luckyAmount = makeRandomAmount(1600, 2999);
			}
			//30-35.99
			else if(luckyNum>=601 && luckyNum<=750) {
				luckyAmount = makeRandomAmount(3000, 3599);
			}
			//36-49.99
			else if(luckyNum>=751 && luckyNum<=950) {
				luckyAmount = makeRandomAmount(3600, 4999);
			}
			//50-200
			else if(luckyNum>=951 && luckyNum<=1000) {
				luckyAmount = makeRandomAmount(5000, 20000);
			}
		}
		
		//生成开奖金额列表
		HashMap<BigDecimal,BigDecimal> luckyAmountMap = new HashMap<>();
		List<BigDecimal> luckyAmountList = new ArrayList<>();
		//添加开奖金额
		luckyAmountMap.put(luckyAmount, luckyAmount);
		luckyAmountList.add(luckyAmount);
		int i=1;
		while(i!=12) {
			BigDecimal luckyAmountOther = new BigDecimal(0);
			int luckyNumOther = random.nextInt(1000)+1;
			//10元
			if(amountType.equals("1")) {
				//2-14.99
				if(luckyNumOther>=1 && luckyNumOther<=300) {
					luckyAmountOther = makeRandomAmount(200, 1499);
				}
				//15-49.99
				else if(luckyNumOther>=301 && luckyNumOther<=400) {
					luckyAmountOther = makeRandomAmount(1500, 4999);
				}
				//50-79.99
				else if(luckyNumOther>=401 && luckyNumOther<=600) {
					luckyAmountOther = makeRandomAmount(5000, 7999);
				}
				//80-99.99
				else if(luckyNumOther>=601 && luckyNumOther<=750) {
					luckyAmountOther = makeRandomAmount(8000, 9999);
				}
				//100-129.99
				else if(luckyNumOther>=751 && luckyNumOther<=850) {
					luckyAmountOther = makeRandomAmount(10000, 12999);
				}
				//130-149.99
				else if(luckyNumOther>=851 && luckyNumOther<=950) {
					luckyAmountOther = makeRandomAmount(13000, 14999);
				}
				//150-end
				else if(luckyNumOther>=951 && luckyNumOther<=1000) {
					luckyAmountOther = makeRandomAmount(15000, configLuckyAmount*100);
				}
			}
			//30元
			else if (amountType.equals("2")) {
				//4-9.99
				if(luckyNumOther>=1 && luckyNumOther<=100) {
					luckyAmountOther = makeRandomAmount(400, 999);
				}
				//10-15.99
				else if(luckyNumOther>=101 && luckyNumOther<=200) {
					luckyAmountOther = makeRandomAmount(1000, 1599);
				}
				//16-29.99
				else if(luckyNumOther>=201 && luckyNumOther<=350) {
					luckyAmountOther = makeRandomAmount(1600, 2999);
				}
				//30-35.99
				else if(luckyNumOther>=351 && luckyNumOther<=500) {
					luckyAmountOther = makeRandomAmount(3000, 3599);
				}
				//36-49.99
				else if(luckyNumOther>=501 && luckyNumOther<=650) {
					luckyAmountOther = makeRandomAmount(3600, 4999);
				}
				//50-199.99
				else if(luckyNumOther>=651 && luckyNumOther<=800) {
					luckyAmountOther = makeRandomAmount(5000, 19999);
				}
				//200-end
				else if(luckyNumOther>=801 && luckyNumOther<=1000) {
					luckyAmountOther = makeRandomAmount(20000, configLuckyAmount*100);
				}
			}
			//50元
			else if (amountType.equals("3")) {
				//4-15.99
				if(luckyNumOther>=1 && luckyNumOther<=150) {
					luckyAmountOther = makeRandomAmount(400, 1599);
				}
				//16-29.99
				else if(luckyNumOther>=151 && luckyNumOther<=250) {
					luckyAmountOther = makeRandomAmount(1600, 2999);
				}
				//30-35.99
				else if(luckyNumOther>=251 && luckyNumOther<=350) {
					luckyAmountOther = makeRandomAmount(3000, 3599);
				}
				//36-49.99
				else if(luckyNumOther>=351 && luckyNumOther<=450) {
					luckyAmountOther = makeRandomAmount(3600, 4999);
				}
				//50-199.99
				else if(luckyNumOther>=451 && luckyNumOther<=650) {
					luckyAmountOther = makeRandomAmount(5000, 19999);
				}
				//200-399.99
				else if(luckyNumOther>=651 && luckyNumOther<=800) {
					luckyAmountOther = makeRandomAmount(20000, 39999);
				}
				//400-end
				else if(luckyNumOther>=801 && luckyNumOther<=1000) {
					luckyAmountOther = makeRandomAmount(40000, configLuckyAmount*100);
				}
			}
			
			//判断生成的金额是否已存在
			if(luckyAmountMap.get(luckyAmountOther)==null) {
				luckyAmountMap.put(luckyAmountOther, luckyAmountOther);
				luckyAmountList.add(luckyAmountOther);
				i++;
			}
		}
		
		//拼接开奖金额列表字符串
		String luckyAmountListStr = "";
		int j = 0;
		int luckyAmountListSize = luckyAmountList.size();
		//生成随机的金额列表
		while(j!=luckyAmountListSize) {
			int index = random.nextInt(luckyAmountList.size());
			String luckyAmountStr = luckyAmountList.get(index).toString();
			
			boolean existFlag = false;
			for (String luckyAmountExist : luckyAmountListStr.split(",")) {
				if(luckyAmountExist.equals(luckyAmountStr)) {
					existFlag = true;
					break;
				}
			}
			
			if(!existFlag) {
				if(luckyAmountListStr.equals("")) {
					luckyAmountListStr = luckyAmountStr;
				} else {
					luckyAmountListStr = luckyAmountListStr + "," + luckyAmountStr;
				}
				j++;
				luckyAmountList.remove(index);
			}
		}
		
		//设置开奖金额
		hongbaoInfo.setLuckyAmount(luckyAmount);
		//设置开奖金额字列表
		hongbaoInfo.setLuckyAmountList(luckyAmountListStr);
		
	}
	
	/**
	 * 生成指定区间的金额
	 * @Title makeRandomAmount
	 * @Description 
	 * @param max
	 * @param min
	 * @return
	 */
	private BigDecimal makeRandomAmount(int min,int max) {
		Random random = new Random();
		int amountInt = random.nextInt(max)%(max-min+1) + min;
		BigDecimal amount = divide100(amountInt);
		return amount;
	}
	
	/**
	 * 金额除以100
	 * @Title divide100
	 * @Description 
	 * @param amountInt
	 * @return
	 */
	private BigDecimal divide100(int amountInt) {
		BigDecimal amount = new BigDecimal(amountInt).divide(getHundred(), 2, BigDecimal.ROUND_HALF_UP);
		return amount;
	}
	
	/**
	 * 获取100
	 * @Title getHundred
	 * @Description 
	 * @return
	 */
	private BigDecimal getHundred() {
		BigDecimal hundred = BigDecimal.TEN.multiply(BigDecimal.TEN);
		return hundred;
	}
	
	public static void main(String[] args) {
		int count = 1000;
		BigDecimal amount = new BigDecimal(10);
		String amountType = "1";
		
		UserInfo userInfo = new UserInfo(); 
		userInfo.setJoinItemCount(2);
		
		BigDecimal amountSum = new BigDecimal(0);
		BigDecimal luckyAmountSum = new BigDecimal(0);
		
		for (int i = 0; i < count; i++) {
			System.out.println("=======第"+(i+1)+"次生成=======");
			HongbaoInfo hongbaoInfo = new HongbaoInfo();
			hongbaoInfo.setAmount(amount);
			hongbaoInfo.setAmountType(amountType);
			new HongbaoInfoService().calcLuckyAmount(hongbaoInfo,userInfo);
			System.out.println("开奖金额："+hongbaoInfo.getLuckyAmount());
			System.out.println("开奖金额列表："+hongbaoInfo.getLuckyAmountList());
			System.out.println("");
//			
			amountSum = amountSum.add(hongbaoInfo.getAmount());
			luckyAmountSum = luckyAmountSum.add(hongbaoInfo.getLuckyAmount());
		}

		System.out.println("参与次数："+count);
		System.out.println("支付总金额："+amountSum);
		System.out.println("中奖总金额："+luckyAmountSum);
		System.out.println("平均中奖金额："+luckyAmountSum.divide(new BigDecimal(count),BigDecimal.ROUND_HALF_UP));
		System.out.println("利润总金额："+amountSum.subtract(luckyAmountSum));
	}
	
	public static void main1(String[] args) {
		String yuming1 = "vvv360.com.cn";
		String yuming2 = "shengchina.com.cn";
		
		Random random = new Random();
		
		for (int i = 1001; i <= 1020; i++) {
			String prefix1 = random.nextInt(100)+100+"";
			String prefix2 = random.nextInt(100)+100+"";
			
			System.out.println("渠道："+i);
			String url1 = "http://"+prefix1+"."+yuming1+"/anq?channel="+i;
			System.out.println(url1);
			String url2 = "http://"+prefix2+"."+yuming2+"/anq?channel="+i;
			System.out.println(url2);
			
			System.out.println();
		}
	}
}