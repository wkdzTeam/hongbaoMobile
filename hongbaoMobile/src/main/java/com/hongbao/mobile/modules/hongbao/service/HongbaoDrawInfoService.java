/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.utils.UUIDGenerator;
import com.hongbao.mobile.modules.hongbao.dao.HongbaoDrawInfoDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawDetail;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.hongbao.util.HongbaoDrawRateUtil;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.entity.PayDataInfo;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.pay.util.PayAiyiUtil;
import com.hongbao.mobile.modules.pay.util.PayNanyueUtil;
import com.hongbao.mobile.modules.pay.util.PayPufaUtil;
import com.hongbao.mobile.modules.pay.util.PayUtil;
import com.hongbao.mobile.modules.pay.util.PayWeifutongUtil;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.user.util.UserBalanceUtil;
import com.hongbao.mobile.modules.yongjin.service.DuihuanInfoService;

/**
 * 红包转盘信息Service
 */
@Service
@Transactional(readOnly = true)
public class HongbaoDrawInfoService extends CrudService<HongbaoDrawInfoDao, HongbaoDrawInfo> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(HongbaoDrawInfoService.class);
	
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
	 * 佣金兑换记录service
	 */
	@Autowired
	private DuihuanInfoService duihuanInfoService;

	public HongbaoDrawInfo get(String id) {
		return super.get(id);
	}
	
	public List<HongbaoDrawInfo> findList(HongbaoDrawInfo hongbaoDrawInfo) {
		return super.findList(hongbaoDrawInfo);
	}
	
	public Page<HongbaoDrawInfo> findPage(Page<HongbaoDrawInfo> page, HongbaoDrawInfo hongbaoDrawInfo) {
		return super.findPage(page, hongbaoDrawInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(HongbaoDrawInfo hongbaoDrawInfo) {
		return super.save(hongbaoDrawInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(HongbaoDrawInfo hongbaoDrawInfo) {
		return super.delete(hongbaoDrawInfo);
	}

	/**
	 * 获取未支付的信息 
	 * @Title getUnPay
	 * @Description 
	 * @return
	 */
	public HongbaoDrawInfo getUnPay(String amountType,String drawType,String userId) {
		HongbaoDrawInfo param = new HongbaoDrawInfo();
		param.setUserId(userId);//用户id
		param.setAmountType(amountType);//金额类型
		param.setDrawType(drawType);//转盘类型
		//获取超时时间
		Long timeout = new Long(Hongbao.getConfig("order.timeout"));
		param.setCreateDate(System.currentTimeMillis()-(timeout*1000));
		List<HongbaoDrawInfo> hongbaoDrawInfoList = dao.getUnPay(param);
		if(hongbaoDrawInfoList!=null && hongbaoDrawInfoList.size()>0) {
			return hongbaoDrawInfoList.get(0);
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
	public HongbaoDrawInfo getUnOpen(String userId) {
		HongbaoDrawInfo param = new HongbaoDrawInfo();
		param.setUserId(userId);//用户id
		List<HongbaoDrawInfo> hongbaoDrawInfoList = dao.getUnOpen(param);
		if(hongbaoDrawInfoList!=null && hongbaoDrawInfoList.size()>0) {
			return hongbaoDrawInfoList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据红包编号查询
	 * @Title getByHongbaoDrawNo
	 * @Description 
	 * @param hongbaoDrawNo
	 * @return
	 */
	public HongbaoDrawInfo getByHongbaoDrawNo(String hongbaoDrawNo) {
		HongbaoDrawInfo param = new HongbaoDrawInfo();
		param.setHongbaoDrawNo(hongbaoDrawNo);
		List<HongbaoDrawInfo> hongbaoDrawInfoList = dao.findList(param);
		if(hongbaoDrawInfoList!=null && hongbaoDrawInfoList.size()>0) {
			return hongbaoDrawInfoList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取下一个红包编号
	 * @Title makeHongbaoDrawNo
	 * @Description 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String makeHongbaoDrawNo() {
		//获取下一个红包编号
		String hongbaoDrawNo = dao.getHongbaoDrawNoNext();
		return hongbaoDrawNo;
	}
	
	/**
	 * 生成支付信息
	 * @Title makePayInfo
	 * @Description 
	 * @param amountType
	 * @param amount
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo makePayInfo(String amountType,String drawType) throws HongbaoException {
		if(StringUtils.isBlank(amountType)) {
			throw new HongbaoException(ResultCodeConstants.C0012,"错误的金额");
		}
		if(StringUtils.isBlank(drawType)) {
			throw new HongbaoException(ResultCodeConstants.C0012,"错误的类型");
		}
		if(!"1".equals(drawType) && !"2".equals(drawType) && !"3".equals(drawType)) {
			throw new HongbaoException(ResultCodeConstants.C0012,"错误的类型");
		}
		//判断金额和类型是否匹配
		Integer configAmount = Integer.parseInt(Hongbao.getConfig("hongbao.draw.amount."+amountType));
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
		
		//查询是否有未打开的红包信息
		HongbaoDrawInfo unOpenHongbaoDrawInfo = this.getUnOpen(userInfo.getId());
		if(unOpenHongbaoDrawInfo!=null && unOpenHongbaoDrawInfo.getAmountType().equals(amountType) && unOpenHongbaoDrawInfo.getDrawType().equals(drawType)) {
			//获取旧渠道
			String channelOld = unOpenHongbaoDrawInfo.getChannel();
			//重新设置渠道
			if(!channel.equals(channelOld)) {
				unOpenHongbaoDrawInfo.setChannel(channel);
				unOpenHongbaoDrawInfo.setParentUserid(parentUserid);
				this.update(unOpenHongbaoDrawInfo);
			}
			return unOpenHongbaoDrawInfo;
		}
		
		//查询是否已创建未过期的支付信息
		HongbaoDrawInfo unPayHongbaoDrawInfo = this.getUnPay(amountType,drawType,userInfo.getId());
		if(unPayHongbaoDrawInfo!=null) {
			//获取旧渠道
			String channelOld = unPayHongbaoDrawInfo.getChannel();
			//重新设置渠道
			if(!channel.equals(channelOld)) {
				unPayHongbaoDrawInfo.setChannel(channel);
				unPayHongbaoDrawInfo.setParentUserid(parentUserid);
				this.update(unPayHongbaoDrawInfo);
			}
			return unPayHongbaoDrawInfo;
		}
		
		Long now = System.currentTimeMillis();
		
		//创建红包订单信息
		HongbaoDrawInfo hongbaoDrawInfo = new HongbaoDrawInfo();
		hongbaoDrawInfo.setId(IdGen.uuid());//id
//		hongbaoDrawInfo.setHongbaoDrawNo(makeHongbaoDrawNo());//订单编号
		hongbaoDrawInfo.setHongbaoDrawNo(UUIDGenerator.generate());//订单编号
		hongbaoDrawInfo.setUserId(userInfo.getId());//用户id
		hongbaoDrawInfo.setDrawType(drawType);//转盘类型
		hongbaoDrawInfo.setAmountType(amountType);//金额类型
		hongbaoDrawInfo.setAmount(new BigDecimal(configAmount));//金额
		hongbaoDrawInfo.setLuckyAmount(BigDecimal.ZERO);//开奖金额
		hongbaoDrawInfo.setOpenFlag("0");//打开标识
		hongbaoDrawInfo.setPayFlag("0");//支付标识
		hongbaoDrawInfo.setChannel(channel);//渠道
		hongbaoDrawInfo.setParentUserid(parentUserid);
		
		hongbaoDrawInfo.setCreateDate(now);//创建时间
		hongbaoDrawInfo.setUpdateDate(now);//修改时间

		//保存订单信息
		super.insert(hongbaoDrawInfo);
		logger.info("转盘红包【"+hongbaoDrawInfo.getHongbaoDrawNo()+"】创建完毕，用户编号【"+userInfo.getUserNo()+"】，渠道【"+ hongbaoDrawInfo.getChannel() +"】");
		
		return hongbaoDrawInfo;
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
	public HongbaoDrawInfo openHongbao(String hongbaoId) throws HongbaoException {
		HongbaoDrawInfo hongbaoDrawInfo = null;
		
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
		
		//查询红包信息
		hongbaoDrawInfo = this.get(hongbaoId);
		if(hongbaoDrawInfo==null) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包不存在");
		}
		//判断是否已支付
		if(!hongbaoDrawInfo.getPayFlag().equals("1")) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包未支付");
		}
		//判断是否已打开
		if(!hongbaoDrawInfo.getOpenFlag().equals("0")) {
			throw new HongbaoException(ResultCodeConstants.C0012,"红包已被打开");
		}
		
		//判断是否为当前用户的红包
		if(!hongbaoDrawInfo.getUserId().equals(userInfo.getId())) {
			throw new HongbaoException(ResultCodeConstants.C0012,"非法的红包");
		}
		Long now = System.currentTimeMillis();
		
		//修改红包信息
		hongbaoDrawInfo.setOpenFlag("1");//已打开
		hongbaoDrawInfo.setOpenDate(now);//打开时间
		//计算红包金额
		calcLuckyAmount(hongbaoDrawInfo, userInfo);
		
		//修改红包信息
		this.update(hongbaoDrawInfo);
		
		//修改用户余额
		UserBalanceUtil.changeBalance(userInfo.getId(), hongbaoDrawInfo.getLuckyAmount(),"打开红包["+hongbaoDrawInfo.getHongbaoDrawNo()+"]",getRequest());
			
		return hongbaoDrawInfo;
		
	}
	
	/**
	 * 计算开奖金额
	 * @Title calcLuckyAmount
	 * @Description 
	 * @param hongbaoDrawInfo
	 * @throws HongbaoException
	 */
	public void calcLuckyAmount(HongbaoDrawInfo hongbaoDrawInfo,UserInfo userInfo) throws HongbaoException {
		//获取金额类型
		String amountType = hongbaoDrawInfo.getAmountType();
		//获取转盘类型
		String drawType = hongbaoDrawInfo.getDrawType();
		
		//创建随机数
		Random random = new Random();
		//幸运金额
		BigDecimal luckyAmount = new BigDecimal(0);
		//幸运数字
		int luckyNum = 0;
		//生成1-10000000的数字
		int luckyAmountNum = random.nextInt(10000000)+1;
		
		//设置时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		//用户今日参与次数key
		String countKey = JedisUtils.KEY_PREFIX+":buyCount:"+ userInfo.getUserNo() +":"+date;
		//获取用户今日参与次数
		Integer buyCount = 1;
		String buyCountStr = JedisUtils.get(countKey);
		if(StringUtils.isNotBlank(buyCountStr)) {
			buyCount = Integer.parseInt(buyCountStr);
		} else {
			buyCount = 1;
		}
		
		//获取获取红包转盘详情列表
		List<HongbaoDrawDetail> hongbaoDrawDetailList = new ArrayList<>();
		if(buyCount<=3) {
			logger.info("【"+userInfo.getUserNo()+"】今日【"+date+"】第【"+buyCount+"】次参加，使用【新用户概率】");
			hongbaoDrawDetailList = HongbaoDrawRateUtil.getHongbaoDrawDetailNewUserList(amountType, drawType);
		} else {
			logger.info("【"+userInfo.getUserNo()+"】今日【"+date+"】第【"+buyCount+"】次参加，使用【正常概率】");
			hongbaoDrawDetailList = HongbaoDrawRateUtil.getHongbaoDrawDetailList(amountType, drawType);
		}
		for (HongbaoDrawDetail hongbaoDrawDetail : hongbaoDrawDetailList) {
			if(luckyAmountNum>=hongbaoDrawDetail.getBeginNum() && luckyAmountNum<=hongbaoDrawDetail.getEndNum()) {
				luckyNum = hongbaoDrawDetail.getId();
				luckyAmount = hongbaoDrawDetail.getLuckyAmount();
			}
		}
		
		//重新设置参与次数
		buyCount++;
		JedisUtils.set(countKey, buyCount.toString());
		
		//设置幸运数字
		hongbaoDrawInfo.setLuckyNum(luckyNum);
		//设置开奖金额
		hongbaoDrawInfo.setLuckyAmount(luckyAmount);
	}

	/**
	 * 正常支付
	 * @Title normalPay
	 * @Description 
	 * @param hongbaoDrawId
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo normalPay(String hongbaoDrawId,String openId) throws HongbaoException {
		//查询订单信息
		HongbaoDrawInfo hongbaoDrawInfo = this.get(hongbaoDrawId);
		
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
		
		if(StringUtils.isBlank(hongbaoDrawInfo.getPayUrl())) {
			String drawType = hongbaoDrawInfo.getDrawType();
			String amountType = hongbaoDrawInfo.getAmountType();
			/*
			String drawName = "";
			if(drawType.equals("1")) {
				drawName = "小盘";
			}
			else if(drawType.equals("2")) {
				drawName = "中盘";
			}
			else if(drawType.equals("3")) {
				drawName = "大盘";
			}
			String itemName = hongbaoDrawInfo.getAmount().intValue()+"元"+drawName;
			*/
			
			String itemName = "";
			if(amountType.equals("1")) {
				itemName = "三日会员充值";
			}
			if(amountType.equals("2")) {
				itemName = "周会员充值";
			}
			if(amountType.equals("3")) {
				itemName = "月会员充值";
			}
			if(amountType.equals("4")) {
				itemName = "三个月会员充值";
			}
			if(amountType.equals("5")) {
				itemName = "半年会员充值";
			}
			if(amountType.equals("6")) {
				itemName = "一年钻石会员充值";
			}
			if(amountType.equals("7")) {
				itemName = "三年钻石会员充值";
			}
			if(amountType.equals("8")) {
				itemName = "十年钻石会员充值";
			}
			
			/*
			if(amountType.equals("1")) {
				if(drawType.equals("1")) {
					itemName = "饮料";
				}
				if(drawType.equals("2")) {
					itemName = "纸巾";
				}
				if(drawType.equals("3")) {
					itemName = "棒棒糖";
				}
			}
			if(amountType.equals("2")) {
				if(drawType.equals("1")) {
					itemName = "泡面";
				}
				if(drawType.equals("2")) {
					itemName = "肥皂";
				}
				if(drawType.equals("3")) {
					itemName = "白糖";
				}
			}
			if(amountType.equals("3")) {
				if(drawType.equals("1")) {
					itemName = "小鸡腿";
				}
				if(drawType.equals("2")) {
					itemName = "酱油";
				}
				if(drawType.equals("3")) {
					itemName = "米醋";
				}
			}
			if(amountType.equals("4")) {
				if(drawType.equals("1")) {
					itemName = "烟";
				}
				if(drawType.equals("2")) {
					itemName = "盆子";
				}
				if(drawType.equals("3")) {
					itemName = "锅";
				}
			}
			if(amountType.equals("5")) {
				if(drawType.equals("1")) {
					itemName = "保温壶";
				}
				if(drawType.equals("2")) {
					itemName = "电子表";
				}
				if(drawType.equals("3")) {
					itemName = "饮水机";
				}
			}
			if(amountType.equals("6")) {
				if(drawType.equals("1")) {
					itemName = "打印机";
				}
				if(drawType.equals("2")) {
					itemName = "床被四件套";
				}
				if(drawType.equals("3")) {
					itemName = "写字板";
				}
			}
			if(amountType.equals("7")) {
				if(drawType.equals("1")) {
					itemName = "白酒";
				}
				if(drawType.equals("2")) {
					itemName = "红酒";
				}
				if(drawType.equals("3")) {
					itemName = "沙发";
				}
			}
			if(amountType.equals("8")) {
				if(drawType.equals("1")) {
					itemName = "电视";
				}
				if(drawType.equals("2")) {
					itemName = "床";
				}
				if(drawType.equals("3")) {
					itemName = "空调";
				}
			}
			*/
			
			String callbackParam = hongbaoDrawInfo.getAmountType()+","+hongbaoDrawInfo.getDrawType()+","+hongbaoDrawInfo.getHongbaoDrawNo();
			
			
			PayConfig payConfig = PayUtil.getPayConfig();
			//微付通支付
			if(payConfig.getPayMerchant().equals("2")) {
				//创建威富通支付信息
				PayDataInfo payWeifutongInfo = PayWeifutongUtil.createWeifutongHongbaoDrawPay(hongbaoDrawInfo.getHongbaoDrawNo(), itemName , itemName ,openId ,  hongbaoDrawInfo.getAmount().multiply(new BigDecimal(100)).intValue(), hongbaoDrawInfo.getCreateDate(),callbackParam);
				//设置支付url
				hongbaoDrawInfo.setPayUrl(payWeifutongInfo.getPayUrl());
				//威富通的预支付ID
				hongbaoDrawInfo.setPayTokenId(payWeifutongInfo.getTokenId());
				//设置威富通支付
				hongbaoDrawInfo.setPayMerchant("2");
				//修改信息
				this.update(hongbaoDrawInfo);
			}
			//环迅支付
			if(payConfig.getPayMerchant().equals("3")) {
				
			}
			//浦发支付
			if(payConfig.getPayMerchant().equals("4")) {
				itemName = itemName+"，客服电话：18681452552";
				String payUrl = PayPufaUtil.createPufaHongbaoDrawPay(payConfig, hongbaoDrawInfo.getHongbaoDrawNo(), itemName, itemName, hongbaoDrawInfo.getAmount().multiply(new BigDecimal(100)).intValue(), callbackParam);
				//设置浦发支付
				hongbaoDrawInfo.setPayMerchant("4");
				hongbaoDrawInfo.setPayUrl(payUrl);
				//修改信息
				this.update(hongbaoDrawInfo);
			}
			//爱益支付
			if(payConfig.getPayMerchant().equals("5")) {
				//创建爱益支付信息
				PayDataInfo payDataInfo = PayAiyiUtil.createAiyiHongbaoDrawPay(hongbaoDrawInfo.getHongbaoDrawNo(), itemName , itemName ,openId ,  hongbaoDrawInfo.getAmount().multiply(new BigDecimal(100)).intValue(), hongbaoDrawInfo.getCreateDate(),callbackParam);
				//设置支付url
				hongbaoDrawInfo.setPayUrl(payDataInfo.getPayUrl());
				//预支付ID
				hongbaoDrawInfo.setPayTokenId(payDataInfo.getTokenId());
				//设置爱益支付
				hongbaoDrawInfo.setPayMerchant("5");
				//修改信息
				this.update(hongbaoDrawInfo);
			}
			//南粤支付
			if(payConfig.getPayMerchant().equals("6")) {
				//创建爱益支付信息
				PayDataInfo payDataInfo = PayNanyueUtil.createNanyueHongbaoDrawPay(hongbaoDrawInfo.getHongbaoDrawNo(), itemName, itemName, itemName, hongbaoDrawInfo.getAmount(), hongbaoDrawInfo.getCreateDate(), callbackParam);
				//设置支付url
				hongbaoDrawInfo.setPayUrl(payDataInfo.getPayUrl());
				hongbaoDrawInfo.setPayTokenId(payDataInfo.getTokenId());
				//设置南粤支付
				hongbaoDrawInfo.setPayMerchant("6");
				//修改信息
				this.update(hongbaoDrawInfo);
			}
			
		}
		
		return hongbaoDrawInfo;
	}
		
}