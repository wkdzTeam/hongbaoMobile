package com.hongbao.mobile.modules.hongbao.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.lock.KeyLock;
import com.hongbao.mobile.common.lock.LockUtils;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.hongbao.service.HongbaoDrawInfoBalancePayService;
import com.hongbao.mobile.modules.hongbao.service.HongbaoDrawInfoService;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.pay.util.PayUtil;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.user.util.UserInfoDataUtil;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;
import com.hongbao.mobile.modules.yongjin.entity.YongjinInfo;
import com.hongbao.mobile.modules.yongjin.service.DuihuanInfoService;
import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

/**
 * 红包转盘Controller
 * @ClassName HongbaoDrawController
 * @Description 
 */
@Controller
@RequestMapping(value = "/hongbaoDraw")
public class HongbaoDrawController extends BaseController {
	
	/**
	 * 日志对象
	 */
	protected Logger drawLogger = LoggerFactory.getLogger(HongbaoDrawInfoService.class);
	
	/**
	 * 日志对象
	 */
	protected Logger tixianLogger = LoggerFactory.getLogger(DuihuanInfoService.class);
	
	/**
	 * 红包转盘信息Service
	 */
	@Autowired
	private HongbaoDrawInfoService hongbaoDrawInfoService;
	
	/**
	 * 红包转盘余额支付Service
	 */
	@Autowired
	private HongbaoDrawInfoBalancePayService hongbaoDrawInfoBalancePayService;
	
	/**
	 * 支付信息Service
	 */
	@Autowired
	private PayInfoService payInfoService;
	
	/**
	 * 用户信息service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 佣金兑换记录service
	 */
	@Autowired
	private DuihuanInfoService duihuanInfoService;
	
	/**
	 * 用户第三方登录记录Service
	 */
	@Autowired
	private UserOauthService userOauthService;
	
	/**
	 * 佣金service
	 */
	@Autowired
	private YongjinInfoService yongjinInfoService;
	
	/**
	 * 获取未打开的转盘
	 * @Title getUnOpen
	 * @Description 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getUnOpen", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getUnOpen(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			//获取session中的用户信息
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			userInfoSession = new UserInfo();
			UserInfoDataUtil.fillUserInfo(userInfoSession);
			
			if(userInfoSession==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			//查询用户信息
			UserInfo userInfo = userInfoService.get(userInfoSession.getId());
			if(userInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			
			//生成支付信息
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getUnOpen(userInfo.getId());
			if(hongbaoDrawInfo!=null) {
				//设置id
				returnJson.put("hongbaoDrawId", hongbaoDrawInfo.getId());
				//支付标识
				returnJson.put("payFlag", hongbaoDrawInfo.getPayFlag());
				//金额
				returnJson.put("amount", hongbaoDrawInfo.getAmount());
				//转盘类型
				returnJson.put("drawType", hongbaoDrawInfo.getDrawType());
			}
			
			
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		} catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		return returnJson.toString();
	}
	
	/**
	 * 获取支付方式
	 * @Title getPayType
	 * @Description 
	 * @param amountType
	 * @param drawType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPayType", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getPayType(String amountType,String drawType,HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			//获取session中的用户信息
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			userInfoSession = new UserInfo();
			UserInfoDataUtil.fillUserInfo(userInfoSession);
			
			if(userInfoSession==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			//查询用户信息
			UserInfo userInfo = userInfoService.get(userInfoSession.getId());
			if(userInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			
			//判断用户支付是否已超上限
			String key_pay_limit = JedisUtils.KEY_PREFIX+":payamount.limit";
			String payAmount_limit  = "10000";
			if( JedisUtils.exists( key_pay_limit ) ){
				payAmount_limit = JedisUtils.get(key_pay_limit);
			}else{
				JedisUtils.set(key_pay_limit, payAmount_limit);
			}
			//if(userInfo.getPayAmount().compareTo(new BigDecimal(Hongbao.getConfig("payAmount.limit")))>=0) {
			if(userInfo.getPayAmount().compareTo(new BigDecimal( payAmount_limit ))>=0) {
				throw new HongbaoException(ResultCodeConstants.C0012,"游戏适可而止,请勿沉迷！");
			}
			
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			String payType = "";
			//获取未支付的红包信息
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getUnPay(amountType, drawType, userInfo.getId());
			if(hongbaoDrawInfo==null) {
				//创建支付信息
//				hongbaoDrawInfo = hongbaoDrawInfoService.makePayInfo(amountType, drawType);
			}
			hongbaoDrawInfo = new HongbaoDrawInfo();
			hongbaoDrawInfo.setPayFlag("1");
			hongbaoDrawInfo.setOpenFlag("0");
			hongbaoDrawInfo.setId("23134546");
			
			//已支付未打开的
			if(hongbaoDrawInfo.getPayFlag().equals("1") && hongbaoDrawInfo.getOpenFlag().equals("0")) {
				payType = "0";//未打开
			} 
			//确认支付中
			else if(hongbaoDrawInfo.getPayFlag().equals("-1")) {
				//支付确认中
				payType = "-1";
			}
			else {
				//微信支付
				if(userInfo.getBalance().compareTo(hongbaoDrawInfo.getAmount())==-1) {
					payType = "1";
					//设置支付地址
					returnJson.put("payUrl", hongbaoDrawInfo.getPayUrl());
				}
				//余额支付
				else {
					payType = "3";
				}
			}
			//设置id
			returnJson.put("hongbaoDrawId", hongbaoDrawInfo.getId());
			
			//设置支付方式
			payType = "3";
			returnJson.put("payType", payType);
			
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		} catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		return returnJson.toString();
	}
	
	/**
	 * 打开红包
	 * @Title openHongbao
	 * @Description 
	 * @param hongbaoDrawId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "openHongbao", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String openHongbao(String hongbaoDrawId,HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		//获取session中的用户信息
		UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
		//获取用户操作锁
		KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
		//锁住用户id
		userOperationLock.lock(userInfoSession.getId());
		
		try {
			//打开红包
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.openHongbao(hongbaoDrawId);
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//设置幸运号码
			returnJson.put("luckyNum", hongbaoDrawInfo.getLuckyNum());
			//设置红包金额
			returnJson.put("luckyAmount", hongbaoDrawInfo.getLuckyAmount());
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			userInfo = userInfoService.get(userInfo.getId());
			//设置余额
			returnJson.put("balance", userInfo.getBalance());
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		finally {
			//操作完成解锁
			userOperationLock.unlock(userInfoSession.getId());
		}
		return returnJson.toString();
	}
	
	/**
	 * 正常支付
	 * @Title normalPay
	 * @Description 
	 * @param hongbaoDrawId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "normalPay", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String normalPay(String hongbaoDrawId,HttpServletRequest request, Model model) {
		JSONObject returnJson = new JSONObject();
		try {
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			
			String payUrl = "";
			
			PayConfig payConfig = PayUtil.getPayConfig();
			//微付通支付
			if(payConfig.getPayMerchant().equals("2")) {
				String openId = PayUtil.getPayOpenId(userInfoSession.getId());
				//openId不存在进行获取
				if(StringUtils.isBlank(openId)) {
					payUrl = "http://"+Hongbao.getDomain()+"/pay/toGetPayOpenId?drawId="+hongbaoDrawId;
				} else {
					//正常支付
					HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.normalPay(hongbaoDrawId,openId);
					payUrl = hongbaoDrawInfo.getPayUrl();
				}
			}
			//浦发支付
			if(payConfig.getPayMerchant().equals("4")) {
				HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.normalPay(hongbaoDrawId,null);
				payUrl = hongbaoDrawInfo.getPayUrl();
			}

			//南粤支付
			if(payConfig.getPayMerchant().equals("6")) {
				HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.normalPay(hongbaoDrawId,null);
				payUrl = hongbaoDrawInfo.getPayUrl();
			}
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//设置支付url
			returnJson.put("payUrl", payUrl);
			
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		} catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		return returnJson.toString();
	}
	
	/**
	 * 获取openId后正常支付
	 * @Title openNormalPay
	 * @Description 
	 * @param hongbaoDrawId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "openNormalPay", produces = { "application/json;charset=UTF-8" })
	public String openNormalPay(String drawId,HttpServletRequest request,RedirectAttributes redirectAttributes, Model model) {
		try {
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			
			String openId = PayUtil.getPayOpenId(userInfoSession.getId());
			if(StringUtils.isBlank(openId)) {
				throw new HongbaoException(ResultCodeConstants.C1108);
			}
			//正常支付
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.normalPay(drawId,openId);
			
			String payUrl = hongbaoDrawInfo.getPayUrl();

			return "redirect:"+payUrl;
			
		} catch (HongbaoException duobao) {
			drawLogger.error(duobao.getResultCode().getMsg());
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "redirect:/draw/index";
		} catch (Exception e) {
			drawLogger.error(e.getMessage());
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "redirect:/draw/index";
		}
	}
	
	/**
	 * 余额支付
	 * @Title balancePay
	 * @Description 
	 * @param hongbaoDrawId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "balancePay", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String balancePay(String hongbaoDrawId,HttpServletRequest request, Model model) {
		JSONObject returnJson = new JSONObject();
		
		UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
		userInfoSession = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfoSession);
		//获取用户操作锁
		KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
		//锁住用户id
		userOperationLock.lock(userInfoSession.getId());
		
		try {
			//获取登录用户
//			UserInfo userInfo = userInfoService.get(userInfoSession.getId());
//			BigDecimal tempBalance = userInfo.getBalance();
//			//余额支付
//			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoBalancePayService.balancePay(hongbaoDrawId);
//			tempBalance = tempBalance.subtract(hongbaoDrawInfo.getAmount());
//			//打开红包
//			hongbaoDrawInfo = hongbaoDrawInfoService.openHongbao(hongbaoDrawInfo.getId());
			//返回成功信息
//			returnJson = ResultCodeConstants.C0.toJsonObject();
//			//设置幸运号码
//			returnJson.put("luckyNum", hongbaoDrawInfo.getLuckyNum());
//			//设置红包金额
//			returnJson.put("luckyAmount", hongbaoDrawInfo.getLuckyAmount());
//			//更新用户
//			userInfo = userInfoService.get(userInfo.getId());
//			//设置余额
//			returnJson.put("balance", userInfo.getBalance());
//			//设置临时余额
//			returnJson.put("tempBalance", tempBalance);
			
			
			returnJson = ResultCodeConstants.C0.toJsonObject();
			returnJson.put("luckyNum", "1");
			returnJson.put("luckyAmount", "3");
			returnJson.put("balance", "5");
			returnJson.put("tempBalance", 12);
			
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		finally {
			//操作完成解锁
			userOperationLock.unlock(userInfoSession.getId());
		}
		return returnJson.toString();
	}
	
	
	/**
	 * 提现页面
	 */
	@RequestMapping(value = "tixian")
	public String tixian(HttpServletRequest request, HttpServletResponse response,Model model) {
//		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
//		userInfo = userInfoService.get(userInfo.getId());
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		//佣金
		model.addAttribute("yongjin", userInfo.getYongjin());
		
		//次数限定
		String key_tixian = JedisUtils.KEY_PREFIX + ":tixian:" + time.format(new Date()) +":"+ userInfo.getUserNo();
		int count = 1;
		if( JedisUtils.exists( key_tixian ) ){
			count = Integer.parseInt( JedisUtils.get(key_tixian) );
		}else{
			JedisUtils.set(key_tixian, count+"");
		}
		model.addAttribute("count", count);
		return "modules/hongbaoDraw/tixian";
	}
	
	
	/**
	 * 控制提现时间的变量
	 */
	public static final HashMap<String, Long> map_user = new HashMap<String, Long>();
	
	private static final SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 普通提现
	 */
	@RequestMapping(value = "tixianAjax",produces = { "application/json;charset=UTF-8" }, method=RequestMethod.POST)
	@ResponseBody
	public String tixianAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) {
		JSONObject returnJson = new JSONObject();

		//获取登录用户
		UserInfo userInfoSession = (UserInfo)session.getAttribute("userInfo");
		
		try {
			if(userInfoSession==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			UserInfo userInfo = userInfoService.get(userInfoSession.getId());
			//余额
			BigDecimal balance = userInfo.getBalance();
			if(balance.compareTo(BigDecimal.ONE)<0){
				throw new HongbaoException(ResultCodeConstants.C0012,"提现最低限额1元");
			}
			//验证余额
			if(balance.compareTo(BigDecimal.ZERO)<=0){
				throw new HongbaoException(ResultCodeConstants.C0012,"余额为空");
			}
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		
		//获取用户操作锁
		KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
		//锁住用户id
		userOperationLock.lock(userInfoSession.getId());
		
		try {
			//提现限制------start
			String key_userno = userInfoSession.getUserNo()+"_tixian";
			if(map_user.get(key_userno) == null){
				map_user.put(key_userno, System.currentTimeMillis());
			} else {
				long value = map_user.get(key_userno);
				long currentTime = System.currentTimeMillis();
				if( (currentTime - value) < 60 * 1000 ){    // 60 秒
					throw new HongbaoException(ResultCodeConstants.C0012,"由于提现的用户过多,限定每分钟只能提现一次.");
					
				}else{
					map_user.put(key_userno, currentTime);
				}
			}
			
			//次数限定
			String key_tixian = JedisUtils.KEY_PREFIX + ":tixian:" + time.format(new Date()) +":"+ userInfoSession.getUserNo();
			int count = 1;
			if( JedisUtils.exists( key_tixian ) ){
				count = Integer.parseInt( JedisUtils.get(key_tixian) );
			}
			if( count > 5){ 
				throw new HongbaoException(ResultCodeConstants.C0012,"每天限制提现5次,您超过5次,请您明天再来提现.");
			}
			JedisUtils.set(key_tixian, ( count + 1 ) +"");  //设置
			//提现限制------end
			
			duihuanInfoService.tixian();
			
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		finally {
			//操作完成解锁
			userOperationLock.unlock(userInfoSession.getId());
		}
		//获取客户端ip
		String ip = IpUtils.getRequestIp(request);
		tixianLogger.info("【"+userInfoSession.getUserNo()+"】【"+ip+"】提现返回结果：【"+returnJson.toString()+"】");
		return returnJson.toString();
	}
	
	
	/**
	 * 佣金提现
	 */
	@RequestMapping(value = "tixianYonjinAjax",produces = { "application/json;charset=UTF-8" }, method=RequestMethod.POST)
	@ResponseBody
	public String tixianYonjinAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) {
		JSONObject returnJson = new JSONObject();
		//获取登录用户
		UserInfo userInfoSession = (UserInfo)session.getAttribute("userInfo");
		try {
			if(userInfoSession==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"非法的用户");
			}
			UserInfo userInfo = userInfoService.get(userInfoSession.getId());
			//余额
			BigDecimal yongjin = userInfo.getYongjin();
			//验证余额
			if(yongjin.compareTo(BigDecimal.ONE)<0){
				throw new HongbaoException(ResultCodeConstants.C0012,"提现最低限额1元");
			}
			if(yongjin.compareTo(BigDecimal.ZERO)<=0){
				throw new HongbaoException(ResultCodeConstants.C0012,"余额为空");
			}
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		
		//获取用户操作锁
		KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
		//锁住用户id
		userOperationLock.lock(userInfoSession.getId());
		
		try {
			//佣金提现限制------start
			String key_userno = userInfoSession.getUserNo()+"_tixian";
			if(map_user.get(key_userno) == null){
				map_user.put(key_userno, System.currentTimeMillis());
			} else {
				long value = map_user.get(key_userno);
				long currentTime = System.currentTimeMillis();
				if( (currentTime - value) < 60 * 1000 ){    // 60 秒
					throw new HongbaoException(ResultCodeConstants.C0012,"由于提现的用户过多,限定每分钟只能提现一次.");
					
				}else{
					map_user.put(key_userno, currentTime);
				}
			}
			//提现限制------end
			
			//TODO 佣金提现
			duihuanInfoService.tixianYongjin();
			
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
		}
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		finally {
			//操作完成解锁
			userOperationLock.unlock(userInfoSession.getId());
		}
		//获取客户端ip
		String ip = IpUtils.getRequestIp(request);
		tixianLogger.info("【"+userInfoSession.getUserNo()+"】【"+ip+"】提现返回结果：【"+returnJson.toString()+"】");
		return returnJson.toString();
	}
	
	
	
	
	/**
	 * 提现记录
	 */
	@RequestMapping(value = "drawDeposit")
	public String drawDeposit(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//测试用户
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		
		userInfo = userInfoService.get(userInfo.getId());
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		//查询结果从第0条开始，查询条数记录
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userInfo.getId());
		//params.put("transferType", "2");
		List<DuihuanInfo> list = duihuanInfoService.getDuihuanListByUser(params);
		model.addAttribute("pageList", list);
		model.addAttribute("pageCount", list.size());
		
		return "modules/hongbaoDraw/draw_deposit";
	}
	
	
	/**
	 * 佣金列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "yongjin")
	public String yongjin(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		userInfo = userInfoService.get(userInfo.getId());
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userInfo.getId());
		List<YongjinInfo> list = yongjinInfoService.getYongjinListByUser(map); 
		model.addAttribute("pageList", list);
		model.addAttribute("pageCount", list.size());
		
		//HashMap< String, Object> map_sum = yongjinInfoService.getYongjinSumByUser(map);
		//model.addAttribute("yongjinSum", map_sum.get("yongjinSum"));
		String yongjin_key = JedisUtils.KEY_PREFIX + ":yongjin:" + userInfo.getUserNo();
	    if (JedisUtils.exists(yongjin_key))
	      model.addAttribute("yongjinSum", JedisUtils.get(yongjin_key));
	    else {
	      model.addAttribute("yongjinSum", "0.00");
	    }
		return "modules/hongbaoDraw/yongjin";
	}
	
	
	
	/**
	 * 代理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "daili")
	public String daili(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//测试用户数据
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		
		userInfo = userInfoService.get(userInfo.getId());
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		return "modules/hongbaoDraw/daili";
	}
	
	/**
	 * shuoming
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "shuoming")
	public String shuoming(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		userInfo = userInfoService.get(userInfo.getId());
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		return "modules/hongbaoDraw/shuoming";
	}
	
	/**
	 * 生成二维码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/userCode")
    public void userCode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String context_path = request.getSession().getServletContext().getRealPath("");
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			String channel = "0";
			String parentid_1 = userInfo.getParentUserid();  //向上第一级父类id
			if( parentid_1.equals("0") ){
				channel = userInfo.getChannel();
			} else {
				UserInfo userInfo_tmp_1 = userInfoService.get(parentid_1);
				if(userInfo_tmp_1 != null ){
					String parentid_2 = userInfo_tmp_1.getParentUserid(); //向上第二级父类id
					if( parentid_2.equals("0") ){
						channel = userInfo.getChannel();
					}
				}
			}
			
			//设置用户编号
			String userNo = userInfo.getUserNo();
			String parentUserid = userInfo.getId();
			//先删除
			String path_new  = context_path +"/codepic/user_draw_"+userNo+".jpg";
			File file_old = new File(path_new);
			if(file_old.exists()) {
				file_old.delete();
			}
			
			//生成前缀
			String prefix = new Random().nextInt(100)+100+"";
			String daili  = JedisUtils.get(JedisUtils.KEY_PREFIX+":domain.daili");  
			String[] dailis = daili.split(",");
			Random random = new Random();
			String domain_daili = dailis[random.nextInt(dailis.length)];
			
			String url = "http://"+prefix+"." + domain_daili + "/anq?channel="+channel+"&parentUserid="+parentUserid+"&path=/draw/index";
			
			//二维码
			String content = url;//"http://www.baidu.com";// 二维码内容
			String code_path = context_path +"/codepic/"+System.currentTimeMillis()+"_code_draw.png";
            BitMatrix byteMatrix;  
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    		hints.put(EncodeHintType.MARGIN, 1);
            byteMatrix = new MultiFormatWriter().encode(content,  BarcodeFormat.QR_CODE, 255, 255,hints);  
            File file = new File(code_path);  
            MatrixToImageWriter.writeToFile(byteMatrix, "png", file); 
            
            //背景图片
            //String bgPicPath = context_path +"/codepic/hongboa_draw.jpg";
            
            String key_bgPicName = JedisUtils.KEY_PREFIX+":bg_picname";
			String bgPicName = "1";  //1 、右边空白  2、中间空白   3、左边空白
			if( JedisUtils.exists( key_bgPicName ) ){
				bgPicName = JedisUtils.get(key_bgPicName);
			}else{
				JedisUtils.set(key_bgPicName, bgPicName);
			}
            
            String bgPicPath = context_path +"/codepic/"+bgPicName+".jpg";
			BufferedImage big = ImageIO.read(new File(bgPicPath));
			BufferedImage small = ImageIO.read(new File(code_path));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2 + 10;  //默认中间
			if(bgPicName.equals("1")){ //右边
				x = big.getWidth() - small.getWidth() - 95;
			}else if(bgPicName.equals("3")){ //左边
				x = 95;
			}
			int y = (big.getHeight() - small.getHeight()) / 2+171;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			
			ImageIO.write(big, "jpg", new File(path_new));
			file.delete(); //删除二维码图片
			
			FileInputStream hFile = new FileInputStream(path_new); 
			//得到文件大小 
			int i=hFile.available(); 
			byte data[]=new byte[i]; 
			//读数据 
			hFile.read(data); 
			//得到向客户端输出二进制数据的对象
			OutputStream toClient=response.getOutputStream(); 
			//输出数据 
			toClient.write(data); 
			toClient.flush(); 
			toClient.close(); 
			hFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
	/**
	 * 客服
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "kefu")
	public String kefu(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//测试用户信息
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		
		userInfo = userInfoService.get(userInfo.getId());
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		return "modules/hongbaoDraw/kefu";
	}
	
	/**
	 * 投诉
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tousu")
	public String tousu(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		userInfo = new UserInfo();
		UserInfoDataUtil.fillUserInfo(userInfo);
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//余额
		model.addAttribute("balance", userInfo.getBalance());
		
		return "modules/hongbaoDraw/tousu";
	}
}
