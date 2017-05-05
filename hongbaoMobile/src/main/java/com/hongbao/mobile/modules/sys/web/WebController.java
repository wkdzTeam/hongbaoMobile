package com.hongbao.mobile.modules.sys.web;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;
import com.hongbao.mobile.modules.hongbao.service.HongbaoDrawInfoService;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.util.UserInfoDataUtil;
import com.hongbao.mobile.modules.weixin.util.WeixinUtil;

/**
 * web总线Controller
 * @ClassName WebController
 * @Description 
 */
@Controller
public class WebController extends BaseController {
	
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
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 转盘
	 * @Title index
	 * @Description 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "draw/index")
	public String drawIndex(String amountType,String drawType,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		try {
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			
			logger.info("============userInfo:{}=======",userInfo);
			
			//判断是否存在openid2
			if(StringUtils.isBlank(userInfo.getOpenId2())) {
	            //跳转openid2登录
	            return "redirect:/weixin/toGetOpenIdOther";
			}
			
			//TODO  黑名单拦截
			String userNo = userInfo.getUserNo();
			String key_balckList = JedisUtils.KEY_PREFIX+":balckList";
			String balckList  = "";
			if( JedisUtils.exists(key_balckList) ){
				balckList = JedisUtils.get(key_balckList); //黑名单
				boolean isbalck = false;
				if( balckList != null){
					String[] balckLists = balckList.split(",");
					for(String balck : balckLists){
						if(balck.equals(userNo)){
							isbalck = true;
							break;
						}
					}
				}
				if(isbalck){
					String key_balckUrl = JedisUtils.KEY_PREFIX+":balckUrl";
					String balckUrl = request.getContextPath() + "/weixin110";
					if( JedisUtils.exists(key_balckUrl) ){
						String temp = JedisUtils.get( key_balckUrl );
						if( temp != null && temp.length() > 0){
							balckUrl  = JedisUtils.get( key_balckUrl );								
						}
					}
					//response.sendRedirect("http://www.qq.com");
					response.sendRedirect(balckUrl);
				}
			}else{
				JedisUtils.set( key_balckList , balckList );
			}
			
			//设置用户编号
			model.addAttribute("userNo", userInfo.getUserNo());
			//余额
			model.addAttribute("balance", userInfo.getBalance());
			//查询中奖信息
			String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":drawLuckyUserList");
			if(StringUtils.isNotBlank(luckyUserListStr)) {
				JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
				model.addAttribute("luckyUserList", luckyUserList);
			}
			
			//查询未打开的红包
			HongbaoDrawInfo unOpenHongbaoDrawInfo = hongbaoDrawInfoService.getUnOpen(userInfo.getId());
			//存在未打开的红包
			if(unOpenHongbaoDrawInfo!=null) {
				model.addAttribute("hongbaoDrawId", unOpenHongbaoDrawInfo.getId());
				amountType = unOpenHongbaoDrawInfo.getAmountType();
				drawType = unOpenHongbaoDrawInfo.getDrawType();
			}

			//金额类型
			if(StringUtils.isBlank(amountType)) {
				amountType = "1";
			}
			model.addAttribute("amountType", amountType);
			//转盘类型
			if(StringUtils.isBlank(drawType)) {
				drawType = "1";
			}
			model.addAttribute("drawType", drawType);
			
			return "modules/hongbaoDraw/index";
		} catch (HongbaoException duobao) {
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "error/404";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/404";
		}
		
	}
	
	private static final SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 安全跳转
	 * @Title anq
	 * @Description 
	 * @param request
	 * @param rid
	 * @param channel
	 * @param path
	 * @return
	 */
	@RequestMapping(value= {"/anq", "/mzt" , "/mqt" , "/desc", "/hbm" })
	public String anq(HttpServletRequest request,Model model,String rid,String channel,String parentUserid,String path) {
		String url = "";
		try {
			String anqKey = "";
			Random rand = new Random();
			//请求记录map
			Map<String, String> reqMap = new HashMap<>();
			//判断是否第一次跳转
			boolean firstFlag = StringUtils.isBlank(rid);
			if(firstFlag) {
				rid = IdGen.uuid();
				anqKey = JedisUtils.KEY_PREFIX+":anq:"+ time.format(new Date()) +":"+rid;
			} else {
				anqKey = JedisUtils.KEY_PREFIX+":anq:"+ time.format(new Date()) +":"+rid;
				reqMap = JedisUtils.getMap(anqKey);
			}
			
			if(reqMap==null) {
				reqMap = new HashMap<>();
			}
			//设置渠道
			if(StringUtils.isBlank(channel)) {
				channel="0";
			}
			//设置父类用户id
			if(StringUtils.isBlank(parentUserid)) {
				parentUserid="0";
			}
			//设置路径
			if(StringUtils.isBlank(path)) {
				path = "draw/index";
			}
			//获取域名列表
			String yuming  = JedisUtils.get(JedisUtils.KEY_PREFIX+":yuming");
			String [] yumings = yuming.split(",");
			//获取域名次数
			int yumingNum = yumings.length;
			//获取结束标志
			boolean endFlag = reqMap.size()==yumingNum;
			
			//前缀
			String prefix = "";
			//域名
			String ming = "";
			if(!endFlag) {
				while(true) {
					//生成域名数组下标
					int yumingIndex = rand.nextInt(yumingNum);
					ming = yumings[yumingIndex];
					//获取域名请求记录
					String yumingInfoStr = reqMap.get(ming);
					if(yumingInfoStr==null) {
						break;
					}
				}

				//生成前缀
				prefix = "1";
			} else {
				//获取默认域名
				prefix = Hongbao.getDomainPrefix();
				ming = Hongbao.getDomainSuffix();
			}
			
			//判断是否结束跳转
			if(!endFlag) {
				reqMap.put(ming, ming);
				//设置redis信息
				JedisUtils.setMap(anqKey , reqMap);
				//拼接url
				url = "http://"+prefix+"."+ming+"/anq?rid="+rid+"&path="+path+"&channel="+channel+"&parentUserid="+parentUserid;
				//判断是否第一次
				if(firstFlag) {
					model.addAttribute("url",url);
					//跳转到jsp页面，通过js跳转
					return "modules/anq/anq";
				}
			}
			else {
				url = "http://"+prefix+"."+ming+"/"+path+"?v="+ System.currentTimeMillis() +"&channel="+channel+"&parentUserid="+parentUserid;
				//删除redis记录
				long rows = JedisUtils.del(anqKey);
				System.out.println("rid:"+( anqKey ) +".....rows:"+rows);
			}
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+url;
	}
	
	/**
	 * 主页
	 * @Title index
	 * @Description 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		try {
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			//设置用户编号
			model.addAttribute("userNo", userInfo.getUserNo());
			
			//查询中奖信息
			String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
			if(StringUtils.isNotBlank(luckyUserListStr)) {
				JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
				model.addAttribute("luckyUserList", luckyUserList);
			}
			
			//查询未打开的红包
			HongbaoInfo unOpenHongbaoInfo = hongbaoInfoService.getUnOpen(userInfo.getId());
			//存在未打开的红包
			if(unOpenHongbaoInfo!=null) {
				model.addAttribute("hongbaoId", unOpenHongbaoInfo.getId());
				model.addAttribute("luckyAmountList", unOpenHongbaoInfo.getLuckyAmountList());
				model.addAttribute("amount", unOpenHongbaoInfo.getAmount());
				return "modules/hongbao/chai";
			} else {
				return "modules/hongbao/index";
			}
		} catch (HongbaoException duobao) {
			//捕获业务异常
			addMessage(redirectAttributes, duobao.getResultCode().getMsg());
			return "error/404";
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(redirectAttributes, "系统异常");
			return "error/404";
		}
		
	}
	
	/**
	 * 跳转
	 * @Title jump
	 * @Description 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "jump")
	public String jump(HttpServletRequest request,Model model) {
		String oldUrl = "";
		Object object_oldUrl = request.getSession().getAttribute("oldUrl");
		if(object_oldUrl != null && object_oldUrl.toString().length()>0){
			oldUrl = object_oldUrl.toString();
		}
		if(StringUtils.isNotBlank(oldUrl)) {
			request.getSession().removeAttribute("oldUrl");
			return "redirect:"+oldUrl;
		}
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		logger.info("userInfo:{}",userInfo);
		model.addAttribute("userInfo", userInfo);
		return "redirect:/draw/index";
	}
	
	/**
	 * 我的红包
	 * @Title hongbao
	 * @Description 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hongbao")
	public String hongbao(HttpServletRequest request,Model model) {
		return "modules/hongbao/hongbao";
	}
	
	/**
	 * 登录
	 * @Title login
	 * @Description 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) {
		try {
			//测试用去掉注释
			//UserInfo userInfo = userInfoService.get("fe54ddbbf58746df85742dad5d2637e0");
			//UserInfo userInfo = userInfoService.get("42f2e45b3e6e436e96a62775fb6e306f");
			//request.getSession().setAttribute("userInfo",z userInfo);
//			UserInfo userInfo = userInfoService.get("12312465465797899");
//			request.getSession().setAttribute("userInfo", userInfo);
			
			if(request.getSession().getAttribute("userInfo")!=null) {
				return "redirect:/draw/index";
			}
			
			//获取渠道号
			String channel = "0";
			Object object = request.getSession().getAttribute("channel");
			if(object != null && object.toString().length()>0){
				channel = object.toString();
			}
			//父类用户id
			String parentUserid = "0";
			Object object_parent = request.getSession().getAttribute("parentUserid");
			if(object_parent != null && object_parent.toString().length()>0){
				parentUserid = object_parent.toString();
			}
			//旧地址
			String oldUrl = "";
			Object object_oldUrl = request.getSession().getAttribute("oldUrl");
			if(object_oldUrl != null && object_oldUrl.toString().length()>0){
				oldUrl = object_oldUrl.toString();
			}
			
			//判断是否是微信
			if(WeixinUtil.isWeixin(request)){
				//获取微信跳转域名
				String domain = WeixinUtil.getDomain();
				//拼接回调url
				String serviceUrl = URLEncoder.encode("http://" + domain + "/weixin/oauth?channel="+channel+"&parentUserid="+parentUserid+"&oldUrl="+oldUrl, "UTF-8");
				//拼接微信登录url
				StringBuilder oauthUrl = new StringBuilder();
				oauthUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");  
	            oauthUrl.append("appid=").append(WeixinUtil.getAppID());  
	            oauthUrl.append("&redirect_uri=").append(serviceUrl);
	            oauthUrl.append("&response_type=code");
	            oauthUrl.append("&scope=snsapi_base");
	            oauthUrl.append("&state=1#wechat_redirect");
	            //跳转微信登录页面
				return "redirect:"+oauthUrl.toString();
			}
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
		
		return "error/404";
	}
	
	
	/**
	 * 微信屏蔽页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "weixin110")
	public String weixin110(HttpServletRequest request,Model model) {
		System.out.println("weixin110...........");
		return "modules/weixin/weixin";
	}
}