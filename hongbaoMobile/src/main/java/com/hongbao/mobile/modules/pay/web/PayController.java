/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.web;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.lock.KeyLock;
import com.hongbao.mobile.common.lock.LockUtils;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.common.utils.Md5Utils;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.hongbao.service.HongbaoDrawInfoPayFinishService;
import com.hongbao.mobile.modules.hongbao.service.HongbaoDrawInfoService;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.entity.PayPush;
import com.hongbao.mobile.modules.pay.service.PayConfigService;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.pay.service.PayPushService;
import com.hongbao.mobile.modules.pay.util.PayNanyueUtil;
import com.hongbao.mobile.modules.pay.util.PayUtil;
import com.hongbao.mobile.modules.pay.util.SignUtils;
import com.hongbao.mobile.modules.pay.util.XmlUtils;
import com.hongbao.mobile.modules.user.entity.UserInfo;

/**
 * 支付信息Controller
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController extends BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(PayInfoService.class);

	/**
	 * 支付信息Service
	 */
	@Autowired
	private PayInfoService payInfoService;
	
	/**
	 * 支付配置Service
	 */
	@Autowired
	private PayConfigService payConfigService;
	
	/**
	 * 红包信息Service
	 */
	@Autowired
	private HongbaoInfoService hongbaoInfoService;
	
	/**
	 * 红包转盘信息Service
	 */
	@Autowired
	private HongbaoDrawInfoService hongbaoDrawInfoService;
	
	/**
	 * 红包转盘完成支付Service
	 */
	@Autowired
	private HongbaoDrawInfoPayFinishService hongbaoDrawInfoPayFinishService;
	
	/**
	 * 支付推送Service
	 */
	@Autowired
	private PayPushService payPushService;
	
	
	/**
	 * 红包转盘威富通异步通知
	 * @Title weifutongAsyncDrawNotify
	 * @Description 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "weifutong/asyncDrawNotify", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String weifutongAsyncDrawNotify(HttpServletRequest request, Model model) {
		String result = "";
		String hongbaoDrawNo = "";
		try {
			String xml = XmlUtils.parseRequst(request);
			
			//获取客户端ip
			String ip = IpUtils.getRequestIp(request);
			logger.info("威富通异步通知信息（请求ip【"+ip+"】)传递xml："+xml);
			
			//转换request参数
			Map<String,String> resultMap = XmlUtils.toMap(xml.getBytes(), "UTF-8");
			
			//获取商户号
			String mchId = resultMap.get("mch_id");
			
			//保存支付推送信息
			PayPush payPush = new PayPush();
			payPush.setMchId(mchId);//商户号
			payPush.setPayMerchant("2");//支付商家
			payPush.setPushData(xml);//推送报文
			payPush.setPayFlag("0");//支付标识
			payPush.setServerIp(ip);//服务器ip
			payPushService.insert(payPush);
			
			
			PayConfig payConfig = payConfigService.getWeifutongByMchId(mchId);
			String key = payConfig.getKey();
			
			if (!SignUtils.checkParam(resultMap, key)) {
				throw new HongbaoException(ResultCodeConstants.C0012,"验证签名不通过");
			}
			if (!("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code")))) {
				throw new HongbaoException(ResultCodeConstants.C0012,"订单支付失败，message："+resultMap.get("message"));
			}
			
			hongbaoDrawNo = resultMap.get("out_trade_no");
			hongbaoDrawNo = hongbaoDrawNo.replaceAll(payConfig.getOrderPrefix(), "");
			
			//查询订单信息
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
			if(hongbaoDrawInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
			}
			//判断金额是否匹配
			Integer payAmount = Integer.parseInt(resultMap.get("total_fee"));
			if(payAmount!=(hongbaoDrawInfo.getAmount().multiply(new BigDecimal(100)).intValue())) {
				throw new HongbaoException(ResultCodeConstants.C0012,"金额不匹配");
			}
			
			String tradeNo = resultMap.get("transaction_id");
			
			logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】进入异步通知，执行加锁操作");
			//获取转盘红包锁
			KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
			//锁住转盘编号
			hongbaoDrawLock.lock(hongbaoDrawNo);
			try {
				logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行异步通知");
				//判断是否已支付
				if("1".equals(hongbaoDrawInfo.getPayFlag())) {
					throw new HongbaoException(ResultCodeConstants.C1001,"订单已支付");
				}
				//完成支付订单信息
				hongbaoDrawInfo = hongbaoDrawInfoPayFinishService.finishPayOrder(hongbaoDrawNo,mchId,tradeNo,"","1");
				
				//修改支付标识
				payPush.setPayFlag("1");
				payPushService.updatePayFlag(payPush);
			}
			catch (HongbaoException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new HongbaoException(ResultCodeConstants.C1005);
			}
			finally {
				//操作完成解锁
				hongbaoDrawLock.unlock(hongbaoDrawNo);
			}
			
			//返回成功信息
			result = "success";
		} catch (HongbaoException duobao) {
			//捕获业务异常
			result = "fail";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，原因："+duobao.getResultCode().getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			result = "fail";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，系统异常："+e.getMessage());
		}
		logger.info("【"+hongbaoDrawNo+"】返回结果："+result);
		return result;
	}
	
	/**
	 * 红包转盘威富通同步通知
	 * @Title weifutongSyncDrawNotify
	 * @Description 
	 * @param out_trade_no
	 * @param trade_status
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "weifutong/syncDrawNotify")
	public String weifutongSyncDrawNotify(String call_param,String trade_status,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		try {
			logger.info("转盘红包同步通知信息：call_param："+call_param);
			if(StringUtils.isNotBlank(call_param)) {
				String[] call_params = null;
				try {
					call_params = call_param.split(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(call_params.length>0) {
					//获取红包编号
					String hongbaoDrawNo = call_params[2];
					if(StringUtils.isNotBlank(hongbaoDrawNo)) {
						//获取转盘红包锁
						KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
						//锁住转盘编号
						hongbaoDrawLock.lock(hongbaoDrawNo);
						try {
							logger.info("【转盘红包】【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行同步通知");
							//查询订单信息
							HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
							if(hongbaoDrawInfo!=null && !"1".equals(hongbaoDrawInfo.getPayFlag())) {
								logger.info("修改转盘红包【"+ hongbaoDrawNo +"】为支付确认中");
								//修改支付标识（确认中）
								hongbaoDrawInfo.setPayFlag("-1");
								//修改红包信息
								hongbaoDrawInfoService.update(hongbaoDrawInfo);
							}
							
						}
						catch (HongbaoException e) {
							throw e;
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new HongbaoException(ResultCodeConstants.C1005);
						}
						finally {
							//操作完成解锁
							hongbaoDrawLock.unlock(hongbaoDrawNo);
						}
					}
					
					String amountType = call_params[0];
					redirectAttributes.addAttribute("amountType", amountType);
					String drawType = call_params[1];
					redirectAttributes.addAttribute("drawType", drawType);
				}
			}
			
			try {
				//等待1.5秒
				Thread.sleep(1500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (HongbaoException duobao) {
			logger.error(duobao.getResultCode().getMsg());
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "redirect:/draw/index";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "redirect:/draw/index";
		}
		return "redirect:/draw/index";
	}
	
	/**
	 * 获取支付openId
	 * @Title toGetPayOpenId
	 * @Description 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toGetPayOpenId")
	public String toGetPayOpenId(String drawId,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			
			//获取当前支付信息
			PayConfig payConfig = PayUtil.getPayConfig();
			
			//获取微信跳转域名
			String domain = payConfig.getWeixinCallbackDomain();
			//拼接回调url
			String serviceUrl = URLEncoder.encode("http://" + domain + "/weixin/openOauth?userId="+userInfoSession.getId()+"&drawId="+drawId, "UTF-8");
			//拼接微信登录url
			StringBuilder oauthUrl = new StringBuilder();
			oauthUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");  
	        oauthUrl.append("appid=").append(payConfig.getAppId());  
	        oauthUrl.append("&redirect_uri=").append(serviceUrl);
	        oauthUrl.append("&response_type=code");
	        oauthUrl.append("&scope=snsapi_base");
	        oauthUrl.append("&state=1#wechat_redirect");
	        
            //跳转微信登录页面
			return "redirect:"+oauthUrl.toString();
		} catch (HongbaoException duobao) {
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "error/500";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/500";
		}
		
	}
	
	
	/**
	 * 浦发异步通知
	 * @Title pufaAsyncDrawNotify
	 * @Description 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pufa/asyncDrawNotify", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String pufaAsyncDrawNotify(String orderno,String wxno,String fee,String sign,String app_id,String attach,String pay_type,HttpServletRequest request, Model model) {
		String result = "";
		String hongbaoDrawNo = "";
		try {
			//获取客户端ip
			String ip = IpUtils.getRequestIp(request);
			logger.info("浦发异步通知信息（请求ip【"+ip+"】)，传递参数：orderno:"+orderno+",fee:"+fee+",sign:"+sign+",app_id:"+app_id+",attach:"+attach+",pay_type:"+pay_type);
			
			//获取key
			PayConfig payConfig = payConfigService.getPufaByAppId(app_id);
			String key = payConfig.getKey();
			
			String signMake = Md5Utils.getMD5LowerCase(orderno+fee+key);
			
			if (!signMake.equals(sign)) {
				throw new HongbaoException(ResultCodeConstants.C0012,"验证签名不通过");
			}
			hongbaoDrawNo = orderno.replaceAll(payConfig.getOrderPrefix(), "");
			
			//查询订单信息
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
			if(hongbaoDrawInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
			}
			//判断金额是否匹配
			Integer payAmount = Integer.parseInt(fee);
			if(payAmount!=(hongbaoDrawInfo.getAmount().multiply(new BigDecimal(100)).intValue())) {
				throw new HongbaoException(ResultCodeConstants.C0012,"金额不匹配");
			}
			
			
			logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】进入异步通知，执行加锁操作");
			//获取转盘红包锁
			KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
			//锁住转盘编号
			hongbaoDrawLock.lock(hongbaoDrawNo);
			try {
				logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行异步通知");
				//判断是否已支付
				if("1".equals(hongbaoDrawInfo.getPayFlag())) {
					throw new HongbaoException(ResultCodeConstants.C1001,"订单已支付");
				}
				//完成支付订单信息
				hongbaoDrawInfo = hongbaoDrawInfoPayFinishService.finishPayOrder(hongbaoDrawNo,app_id,wxno,"","1");
			}
			catch (HongbaoException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new HongbaoException(ResultCodeConstants.C1005);
			}
			finally {
				//操作完成解锁
				hongbaoDrawLock.unlock(hongbaoDrawNo);
			}
			
			//返回成功信息
			result = "ok";
		} catch (HongbaoException duobao) {
			//捕获业务异常
			result = "fail";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，原因："+duobao.getResultCode().getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			result = "fail";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，系统异常："+e.getMessage());
		}
		logger.info("【"+hongbaoDrawNo+"】返回结果："+result);
		return result;
	}
	
	/**
	 * 浦发同步通知
	 * @Title pufaSyncDrawNotify
	 * @Description 
	 * @param call_param
	 * @param trade_status
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "pufa/syncDrawNotify")
	public String pufaSyncDrawNotify(String call_param,String trade_status,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		try {
			logger.info("转盘红包同步通知信息：call_param："+call_param);
			if(StringUtils.isNotBlank(call_param)) {
				String[] call_params = null;
				try {
					call_params = call_param.split(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(call_params.length>0) {
					//获取红包编号
					String hongbaoDrawNo = call_params[2];
					if(StringUtils.isNotBlank(hongbaoDrawNo)) {
						//获取转盘红包锁
						KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
						//锁住转盘编号
						hongbaoDrawLock.lock(hongbaoDrawNo);
						try {
							logger.info("【转盘红包】【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行同步通知");
							//查询订单信息
							HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
							if(hongbaoDrawInfo!=null && !"1".equals(hongbaoDrawInfo.getPayFlag())) {
								logger.info("修改转盘红包【"+ hongbaoDrawNo +"】为支付确认中");
								//修改支付标识（确认中）
								hongbaoDrawInfo.setPayFlag("-1");
								//修改红包信息
								hongbaoDrawInfoService.update(hongbaoDrawInfo);
							}
							
						}
						catch (HongbaoException e) {
							throw e;
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new HongbaoException(ResultCodeConstants.C1005);
						}
						finally {
							//操作完成解锁
							hongbaoDrawLock.unlock(hongbaoDrawNo);
						}
					}
					
					String amountType = call_params[0];
					redirectAttributes.addAttribute("amountType", amountType);
					String drawType = call_params[1];
					redirectAttributes.addAttribute("drawType", drawType);
				}
			}
			
			try {
				//等待1.5秒
				Thread.sleep(1500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (HongbaoException duobao) {
			logger.error(duobao.getResultCode().getMsg());
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "redirect:/draw/index";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "redirect:/draw/index";
		}
		return "redirect:/draw/index";
	}
	
	/**
	 * 南粤异步通知
	 * @Title nanyueAsyncDrawNotify
	 * @Description 
	 * @param notifyContent
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "nanyue/asyncDrawNotify", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String nanyueAsyncDrawNotify(@RequestBody String notifyContent,HttpServletRequest request, Model model) {
		String result = "";
		String hongbaoDrawNo = "";
		try {
			//{"returnCode":"0","resultCode":"0","sign":"1C93B0C761B301DAFD2D1C313489749E","postscript":"2元大盘","status":"02","channel":"wxPub","body":"2元大盘","outTradeNo":"zhuanpan-draw-order-11175245","amount":2.00,"currency":"CNY","transTime":"20170120004837","subject":"2元大盘","payChannelType":"weixin"}
			
			notifyContent = notifyContent.replaceAll("&quot;", "\"");
			notifyContent = notifyContent.replaceAll("&apos;", "'");
			
			//获取客户端ip
			String ip = IpUtils.getRequestIp(request);
			logger.info("南粤异步通知信息（请求ip【"+ip+"】)传递json："+notifyContent);
			
			JSONObject json = JSONObject.fromObject(notifyContent);
			
			String returnCode = json.getString("returnCode");
			if(!returnCode.equals("0")){
				throw new HongbaoException(ResultCodeConstants.C0012,"状态错误");
			}
			
			//获取密钥
			String mchId = json.containsKey("postscript")?json.getString("postscript"):"";
			
			//保存支付推送信息
			PayPush payPush = new PayPush();
			payPush.setMchId(mchId);//商户号
			payPush.setPayMerchant("6");//支付商家
			payPush.setPushData(notifyContent);//推送报文
			payPush.setPayFlag("0");//支付标识
			payPush.setServerIp(ip);//服务器ip
			payPushService.insert(payPush);
			
			PayConfig payConfig = payConfigService.getNanyueByMchId(mchId);
			String key = payConfig.getKey();
			if(StringUtils.isBlank(key)) {
				key = PayUtil.getPayConfig().getKey();
			}
			
			//验签
			if(!PayNanyueUtil.validSign(json, key)){
				throw new HongbaoException(ResultCodeConstants.C0012,"验证签名不通过");
			}
			
			String status = json.getString("status");
			
			logger.info("订单状态:{}" , status);
			
			hongbaoDrawNo = json.getString("outTradeNo");
			hongbaoDrawNo = hongbaoDrawNo.replaceAll(payConfig.getOrderPrefix(), "");
			
			//查询订单信息
			HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
			if(hongbaoDrawInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
			}
			//判断金额是否匹配
			BigDecimal amount = new BigDecimal(json.getString("amount"));
			if(hongbaoDrawInfo.getAmount().compareTo(amount)!=0) {
				throw new HongbaoException(ResultCodeConstants.C0012,"金额不匹配");
			}
			
			String tradeNo = json.containsKey("outChannelNo")?json.getString("outChannelNo"):"";
			
			logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】进入异步通知，执行加锁操作");
			//获取转盘红包锁
			KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
			//锁住转盘编号
			hongbaoDrawLock.lock(hongbaoDrawNo);
			try {
				logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行异步通知");
				//判断是否已支付
				if("1".equals(hongbaoDrawInfo.getPayFlag())) {
					throw new HongbaoException(ResultCodeConstants.C1001,"订单已支付");
				}
				//完成支付订单信息
				hongbaoDrawInfo = hongbaoDrawInfoPayFinishService.finishPayOrder(hongbaoDrawNo,mchId,tradeNo,"","1");
				
				//修改支付标识
				payPush.setPayFlag("1");
				payPushService.updatePayFlag(payPush);
			}
			catch (HongbaoException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new HongbaoException(ResultCodeConstants.C1005);
			}
			finally {
				//操作完成解锁
				hongbaoDrawLock.unlock(hongbaoDrawNo);
			}
			
			//返回成功信息
			result = "SUCCESS";
		} catch (HongbaoException duobao) {
			//捕获业务异常
			result = "FAIL";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，原因："+duobao.getResultCode().getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			result = "FAIL";
			logger.error("【"+hongbaoDrawNo+"】异步通知失败，系统异常："+e.getMessage());
		}
		logger.info("【"+hongbaoDrawNo+"】返回结果："+result);
		return result;
	}
	
	/**
	 * 南粤同步通知
	 * @Title nanyueSyncDrawNotify
	 * @Description 
	 * @param call_param
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "nanyue/syncDrawNotify")
	public String nanyueSyncDrawNotify(String call_param,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		try {
			logger.info("转盘红包同步通知信息：call_param："+call_param);
			if(StringUtils.isNotBlank(call_param)) {
				String[] call_params = null;
				try {
					call_params = call_param.split(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(call_params.length>0) {
					//获取红包编号
					String hongbaoDrawNo = call_params[2];
					if(StringUtils.isNotBlank(hongbaoDrawNo)) {
						//获取转盘红包锁
						KeyLock<String> hongbaoDrawLock = LockUtils.getHongbaoDrawLock();
						//锁住转盘编号
						hongbaoDrawLock.lock(hongbaoDrawNo);
						try {
							logger.info("【转盘红包】【"+Thread.currentThread().getName()+"】【"+hongbaoDrawNo+"】执行同步通知");
							//查询订单信息
							HongbaoDrawInfo hongbaoDrawInfo = hongbaoDrawInfoService.getByHongbaoDrawNo(hongbaoDrawNo);
							if(hongbaoDrawInfo!=null && !"1".equals(hongbaoDrawInfo.getPayFlag())) {
								logger.info("修改转盘红包【"+ hongbaoDrawNo +"】为支付确认中");
								//修改支付标识（确认中）
								hongbaoDrawInfo.setPayFlag("-1");
								//修改红包信息
								hongbaoDrawInfoService.update(hongbaoDrawInfo);
							}
							
						}
						catch (HongbaoException e) {
							throw e;
						}
						catch (Exception e) {
							e.printStackTrace();
							throw new HongbaoException(ResultCodeConstants.C1005);
						}
						finally {
							//操作完成解锁
							hongbaoDrawLock.unlock(hongbaoDrawNo);
						}
					}
					
					String amountType = call_params[0];
					redirectAttributes.addAttribute("amountType", amountType);
					String drawType = call_params[1];
					redirectAttributes.addAttribute("drawType", drawType);
				}
			}
			
			try {
				//等待1.5秒
				Thread.sleep(1500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (HongbaoException duobao) {
			logger.error(duobao.getResultCode().getMsg());
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "redirect:/draw/index";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "redirect:/draw/index";
		}
		return "redirect:/draw/index";
	}
}