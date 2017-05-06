package com.hongbao.mobile.modules.weixin.web;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.utils.UUIDGenerator;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.util.PayUtil;
import com.hongbao.mobile.modules.sys.utils.PermissionsUtils;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserImageService;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.weixin.util.WeixinUtil;

/**
 * 微信Controller
 * @ClassName WeixinController
 * @Description 
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeixinController extends BaseController {
	
	/**
	 * 用户第三方登录记录Service
	 */
	@Autowired
	private UserOauthService userOauthService; 
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 用户图片Service
	 */
	@Autowired
	private UserImageService userImageService; 

	/**
	 * 线程池
	 */
    private ExecutorService threadPool = Executors.newFixedThreadPool(30);
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);
	/**
	 * 微信oauth登录回调
	 * @Title oauth
	 * @Description 
	 * @param code
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "oauth")
	public String oauth(String code,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
        //获取域名
        String domain = Hongbao.getDomain();
        String url = "http://"+domain+"/jump";
		try {
			JSONObject jobj = WeixinUtil.getOauthAccessToken(code);
			//获取登录令牌
			String accessToken = jobj.containsKey("access_token")?jobj.getString("access_token"):"";
			//获取openid
			String openid = jobj.containsKey("openid")?jobj.getString("openid"):"";
			
			if(StringUtils.isBlank(accessToken) || StringUtils.isBlank(openid)) {
				//0011:没有获取到登录信息
				throw new HongbaoException(ResultCodeConstants.C0011,"没有获取到登录信息");
			}
			
            //根据openid查询用户信息
            UserInfo userInfo = userInfoService.getByOpenId(openid);
            
			//获取渠道号
			String channel = "0";
			Object object = request.getParameter("channel");
			if(object != null && object.toString().length()>0){
				channel = object.toString();
			}
			//父类用户id
			String parentUserid = "0";
			Object object_parent = request.getParameter("parentUserid");
			if(object_parent != null && object_parent.toString().length()>0){
				parentUserid = object_parent.toString();
			}
			//旧地址
			String oldUrl = "";
			Object object_oldUrl = request.getParameter("oldUrl");
			if(object_oldUrl != null && object_oldUrl.toString().length()>0){
				oldUrl = object_oldUrl.toString();
			}
			
            //首次登陆
            if(userInfo==null) {
            	LOGGER.info("==============进入用户信息插入逻辑=============");
            	//生成用户id
            	String userId = IdGen.uuid();
            	//生成用户编号
//            	String userNo = userInfoService.makeUserNo();
            	String userNo = UUIDGenerator.generate();
            	LOGGER.info("==============userId:{},userNo:[{}===========",userId,userNo);
            	//设置用户信息
            	userInfo = new UserInfo();
            	userInfo.setId(userId);//用户id
        		userInfo.setUserNo(userNo);//用户编号
            	userInfo.setLastLoginIp(IpUtils.getRequestIp(request));//最后登录ip
        		userInfo.setLastLoginTime(System.currentTimeMillis());//最后登录时间
        		userInfo.setBalance(BigDecimal.ZERO);//余额
        		userInfo.setPayAmount(BigDecimal.ZERO);//支付金额
        		userInfo.setJoinItemCount(0);//参加夺宝次数
        		userInfo.setImportFlag("0");//导入标识：否
        		userInfo.setChannel(channel);//设置渠道
        		userInfo.setParentUserid(parentUserid); //父类用户id  0：顶端
        		userInfo.setOpenId(openid);//第三方登录id-开发者
        		userInfo.setEquipment(request.getHeader("user-agent")); //设备信息
        		userInfo.setYongjin(BigDecimal.ZERO); //佣金
        		//保存用户
        		userInfoService.insert(userInfo);
            	
            } 
            //更新用户信息
            else {
//        		//设置用户信息
            	userInfo.setLastLoginIp(IpUtils.getRequestIp(request));//最后登录ip
        		userInfo.setLastLoginTime(System.currentTimeMillis());//最后登录时间
        		userInfoService.updateLogin(userInfo);
            }
            //添加用户session
            //request.getSession().setAttribute("userInfo", userInfo);
            //设置渠道信息
            redirectAttributes.addAttribute("channelRedirect", channel);
            //设置父类用户id
            redirectAttributes.addAttribute("parentUseridRedirect", parentUserid);
            //设置用户编号
            redirectAttributes.addAttribute("userNoRedirect", userInfo.getUserNo());
            //设置随机数
            String nonce = System.currentTimeMillis()+"";
            redirectAttributes.addAttribute("nonceRedirect", nonce);
            //设置用户id key，验证是否合法
            redirectAttributes.addAttribute("keyRedirect", PermissionsUtils.makeUserIdKey(userInfo.getUserNo(),nonce));
            //旧地址
            redirectAttributes.addAttribute("oldUrlRedirect", oldUrl);
            
            model.addAttribute(userInfo);
            UserInfo userInfoEmp = (UserInfo)request.getSession().getAttribute("userInfo");
            logger.info("=========userInfoEmp:{}===========",userInfoEmp);
            //跳转首页
            return "redirect:"+url;
           
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			addMessage(redirectAttributes, hongbao.getResultCode().getMsg());
			return "error/500";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/500";
		}
	}
	
	/**
	 * 获取其他openid
	 * @Title toGetOpenIdOther
	 * @Description 
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "toGetOpenIdOther")
	public String toGetOpenIdOther(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			
			logger.info("=====进入toGetOpenIdOther方法=========");
			UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
			
			//获取微信跳转域名
			String domain = WeixinUtil.getDomain();
			//拼接回调url
			String serviceUrl = URLEncoder.encode("http://" + domain + "/hongbao/weixin/oauthOther?userId="+userInfoSession.getId(), "UTF-8");
			//拼接微信登录url
			StringBuilder oauthUrl = new StringBuilder();
			oauthUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");  
	        oauthUrl.append("appid=").append("wx79f94418897ba79a");  
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
	 * 其他oauth登录
	 * @Title oauthOther
	 * @Description 
	 * @param code
	 * @param userId
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "oauthOther")
	public String oauthOther(String code,String userId,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
        //获取域名
        String domain = Hongbao.getDomain();
        String url = "http://"+domain;
		try {
			JSONObject jobj = WeixinUtil.getOauthAccessToken(code,"wx79f94418897ba79a","9dd559bf0a1c0be7779dd68b43410460");
			//获取登录令牌
			String accessToken = jobj.containsKey("access_token")?jobj.getString("access_token"):"";
			//获取openid
			String openid = jobj.containsKey("openid")?jobj.getString("openid"):"";
			
			LOGGER.info("=======accessToken:{}===============",accessToken);
			LOGGER.info("=======openid:{}===============",openid);
			
			if(StringUtils.isBlank(accessToken) || StringUtils.isBlank(openid)) {
				//0011:没有获取到登录信息
				throw new HongbaoException(ResultCodeConstants.C0011,"没有获取到登录信息");
			}
			
			UserInfo userInfo = userInfoService.get(userId);
			userInfo.setOpenId2(openid);
			
			userInfoService.updateOpenId2(userInfo);
			
			LOGGER.info("=======url{}===============",url);
            //跳转首页
            return "redirect:"+url;
           
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			addMessage(redirectAttributes, hongbao.getResultCode().getMsg());
			return "error/500";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/500";
		}
	}
	
	/**
	 * 获取支付用户openid专用
	 * @Title openOauth
	 * @Description 
	 * @param code
	 * @param userId
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "openOauth")
	public String openOauth(String code,String userId,String drawId,HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
        //获取域名
        String domain = Hongbao.getDomain();
        String url = "http://"+domain+"/hongbaoDraw/openNormalPay?drawId="+drawId;
		try {
			//获取当前支付
			PayConfig payConfig = PayUtil.getPayConfig();
			
			JSONObject jobj = WeixinUtil.getOauthAccessToken(code,payConfig.getAppId(),payConfig.getAppSecret());
			//获取登录令牌
			String accessToken = jobj.containsKey("access_token")?jobj.getString("access_token"):"";
			//获取openid
			String openid = jobj.containsKey("openid")?jobj.getString("openid"):"";
			
			if(StringUtils.isBlank(accessToken) || StringUtils.isBlank(openid)) {
				//0011:没有获取到登录信息
				throw new HongbaoException(ResultCodeConstants.C0011,"没有获取到登录信息");
			}
			
			//设置用户openId
			JedisUtils.set(JedisUtils.KEY_PREFIX+":payOpenId:"+userId+":"+payConfig.getId(), openid);
			
            //跳转首页
            return "redirect:"+url;
           
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			addMessage(redirectAttributes, hongbao.getResultCode().getMsg());
			return "error/500";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/500";
		}
	}
}
