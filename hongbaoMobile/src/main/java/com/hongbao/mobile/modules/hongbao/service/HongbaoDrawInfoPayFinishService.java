package com.hongbao.mobile.modules.hongbao.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.lock.KeyLock;
import com.hongbao.mobile.common.lock.LockUtils;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.channeluser.entity.ChannelUser;
import com.hongbao.mobile.modules.channeluser.service.ChannelUserService;
import com.hongbao.mobile.modules.hongbao.dao.HongbaoDrawInfoDao;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.pay.entity.PayInfo;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

/**
 * 红包转盘完成支付Service
 * @ClassName HongbaoDrawInfoPayFinishService
 * @Description 
 */
@Service
@Transactional(readOnly = true)
public class HongbaoDrawInfoPayFinishService extends CrudService<HongbaoDrawInfoDao, HongbaoDrawInfo> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(HongbaoDrawInfoService.class);
	
	/**
	 * 红包转盘信息Service
	 */
	@Autowired
	private HongbaoDrawInfoService hongbaoDrawInfoService;
	
	/**
	 * 支付信息Service
	 */
	@Autowired
	private PayInfoService payInfoService;
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
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
	 * 获取红包转盘信息Service
	 * @Title getHongbaoDrawInfoService
	 * @Description 
	 * @return
	 */
	public HongbaoDrawInfoService getHongbaoDrawInfoService() {
		return hongbaoDrawInfoService;
	}

	/**
	 * 完成支付订单信息
	 * @Title finishPayOrder
	 * @Description 
	 * @param hongbaoDrawNo
	 * @param tradeCode
	 * @param tradePaycode
	 * @param payType
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo finishPayOrder(String hongbaoDrawNo,String mchId,String tradeCode,String tradePaycode,String payType) throws HongbaoException {
		HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
		return finishPayOrder(hongbaoDrawInfo,mchId, tradeCode,tradePaycode,payType);
	}
	
	/**
	 * 完成支付订单信息
	 * @Title finishPayOrder
	 * @Description 
	 * @param hongbaoDrawInfo
	 * @param tradeCode
	 * @param tradePaycode
	 * @param payType
	 * @return
	 * @throws HongbaoException
	 */
	@Transactional(readOnly = false)
	public HongbaoDrawInfo finishPayOrder(HongbaoDrawInfo hongbaoDrawInfo,String mchId,String tradeCode,String tradePaycode,String payType) throws HongbaoException {
		if(hongbaoDrawInfo==null || StringUtils.isBlank(hongbaoDrawInfo.getId())) {
			throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
		}
		//订单状态为待支付
		if(!"1".equals(hongbaoDrawInfo.getPayFlag())) {
			UserInfo userInfo = userInfoService.get(hongbaoDrawInfo.getUserId());//用户id
			//获取用户操作锁
			KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
			//锁住用户id
			userOperationLock.lock(userInfo.getId());
			try {
				Long now = System.currentTimeMillis();
				
				//设置支付类型
				hongbaoDrawInfo.setPayType(payType);
				
				//创建支付信息
				PayInfo payInfo = new PayInfo();
				payInfo.setUserId(hongbaoDrawInfo.getUserId());//用户id
				payInfo.setHongbaoId(hongbaoDrawInfo.getId());//红包id
				payInfo.setTradeCode(tradeCode);//盾行支付平台流水号
				payInfo.setTradePaycode(tradePaycode);//第三方支付平台流水号
				payInfo.setPayTitle("大赚盘"+hongbaoDrawInfo.getAmount().intValue()+"元");//支付标题
				payInfo.setPayType(hongbaoDrawInfo.getPayType());//支付类型（1：公众号支付，2：wap支付，3：余额支付）
				payInfo.setPayBusinessType("2");//转盘红包
				payInfo.setMchId(mchId);//商户号
				payInfo.setDescribe("大赚盘"+hongbaoDrawInfo.getAmount().intValue()+"元");//描述
				payInfo.setAmount(hongbaoDrawInfo.getAmount());//金额
				payInfo.setChannel(StringUtils.isNotBlank(hongbaoDrawInfo.getChannel()) ?hongbaoDrawInfo.getChannel() : "0");//从红包中获取渠道号
				payInfo.setParentUserid(StringUtils.isNotBlank(hongbaoDrawInfo.getParentUserid()) ? hongbaoDrawInfo.getParentUserid() : "0"); //获取用户id
				payInfo.setPayMerchant(hongbaoDrawInfo.getPayMerchant());//支付商家
				
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
				hongbaoDrawInfo.setPayFlag("1");//状态【已支付】
				hongbaoDrawInfo.setPayId(payInfo.getId());//支付id
				hongbaoDrawInfo.setPayDate(now);//支付时间
				//保存红包信息
				this.update(hongbaoDrawInfo);
				//设置支付信息
				hongbaoDrawInfo.setPayInfo(payInfo);
				
				//修改参与次数
				userInfo.setJoinItemCount(userInfo.getJoinItemCount()+1);
				//公众号支付余额累加
				if(payType.equals("1")) {
					//修改用户支付金额
					BigDecimal payAmount = userInfo.getPayAmount();
					if(payAmount==null) {
						payAmount = new BigDecimal(0);
					}
					userInfo.setPayAmount(payAmount.add(payInfo.getAmount()));
				}
				
				userInfoService.updateNow(userInfo);
				
				//佣金奖励
				//if( !hongbaoDrawInfo.getPayType().equals("3") ){
				yongjinInfoService.DrawYongjin(hongbaoDrawInfo.getUserId(),hongbaoDrawInfo.getPayType(),hongbaoDrawInfo.getHongbaoDrawNo(), hongbaoDrawInfo.getAmount());
				//}
			}
			catch(HongbaoException hongbao) {
				throw hongbao;
			}
			catch (Exception e) {
				e.printStackTrace();
				//设置系统异常
				throw new HongbaoException(ResultCodeConstants.C1005);
			}
			finally {
				//操作完成解锁
				userOperationLock.unlock(userInfo.getId());
			}
		}
		
		return hongbaoDrawInfo;
	}
	
}
