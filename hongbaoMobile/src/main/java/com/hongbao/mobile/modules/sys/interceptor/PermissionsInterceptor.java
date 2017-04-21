package com.hongbao.mobile.modules.sys.interceptor;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hongbao.mobile.common.service.BaseService;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.utils.SpringContextHolder;
import com.hongbao.mobile.modules.sys.utils.ChannelUtils;
import com.hongbao.mobile.modules.sys.utils.PermissionsUtils;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;

/**
 * 权限拦截器
 * @ClassName: PermissionsInterceptor   
 * @Description: TODO  
 */
public class PermissionsInterceptor extends BaseService implements HandlerInterceptor {
	
	/**
	 * 用户信息Service
	 */
	private UserInfoService userInfoService = SpringContextHolder.getBean("userInfoService");
	
	
	/**
	 * 在此验证权限
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
					
			//设置渠道信息
			try {
				//获取渠道
				String channel = request.getParameter("channel");
				//设置默认渠道
				if(StringUtils.isBlank(channel)) {
					channel = "0";
				}
				//获取session中的渠道
				String channelSession = request.getSession().getAttribute("channel")!=null?request.getSession().getAttribute("channel").toString():"0";
				if(!(channel.equals("0") && !channelSession.equals("0"))) {
					if(request.getSession().getAttribute("channel")==null || !channel.equals(channelSession)) {
						request.getSession().setAttribute("channel", channel);
					}
				}
				//添加ip统计
				ChannelUtils.insertredis(IpUtils.getRequestIp(request) , channel);
				
				//获取父类用户id
				String parentUserid = request.getParameter("parentUserid");
				//设置默认父类用户id
				if(StringUtils.isBlank(parentUserid)) {
					parentUserid = "0";
				}
				
				//获取session中的父类用户id
				String parentUseridSession = request.getSession().getAttribute("parentUserid")!=null?request.getSession().getAttribute("parentUserid").toString():"0";
				if(!(parentUserid.equals("0") && !parentUseridSession.equals("0"))) {
					if(request.getSession().getAttribute("parentUserid")==null || !parentUserid.equals(parentUseridSession)) {
						request.getSession().setAttribute("parentUserid", parentUserid);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//获取跳转用户id
			String userNoRedirect = request.getParameter("userNoRedirect");
			//获取跳转渠道
			String channelRedirect = request.getParameter("channelRedirect");
			//获取跳转父类用户id
			String parentUseridRedirect = request.getParameter("parentUseridRedirect");
			//获取跳转验证码
			String keyRedirect = request.getParameter("keyRedirect");
			//获取随机数
			String nonceRedirect = request.getParameter("nonceRedirect");
			//获取旧地址
			String oldUrlRedirect = request.getParameter("oldUrlRedirect");
			
			if(request.getSession().getAttribute("userInfo")==null && StringUtils.isNotBlank(userNoRedirect)) {
				logger.info("userNoRedirect："+userNoRedirect);
				logger.info("channelRedirect："+channelRedirect);
				logger.info("parentUseridRedirect："+parentUseridRedirect);
				logger.info("keyRedirect："+keyRedirect);
				boolean successFlag = PermissionsUtils.validateUserIdKey(userNoRedirect, keyRedirect,nonceRedirect);
				if(successFlag) {
					//查询用户信息
					UserInfo userInfoRedirect = userInfoService.getByUserNo(userNoRedirect);
					//设置用户信息
					if(userInfoRedirect!=null) {
						request.getSession().setAttribute("userInfo", userInfoRedirect);
					}
					//设置渠道
					if(StringUtils.isNotBlank(channelRedirect)) {
						request.getSession().setAttribute("channel", channelRedirect);
					}
					//设置父类用户id
					if(StringUtils.isNotBlank(parentUseridRedirect)) {
						request.getSession().setAttribute("parentUserid", parentUseridRedirect);
					}
					//设置旧地址
					if(StringUtils.isNotBlank(oldUrlRedirect)) {
						request.getSession().setAttribute("oldUrl", oldUrlRedirect);
					}
					
				}
			}
			
			//查询用户信息
			Object userInfoObj = request.getSession().getAttribute("userInfo");
			if(userInfoObj==null) {
				//获取请求的url
				String query = request.getQueryString();
				String oldUrl = request.getServletPath()+(StringUtils.isNotBlank(query)&&!query.equals("null")?"?"+query:"");
				request.getSession().setAttribute("oldUrl", oldUrl);
				response.sendRedirect(request.getContextPath()+"/login");
				return false;
			}else{
				UserInfo userInfo = (UserInfo)userInfoObj;
				//TODO  黑名单拦截
				String userNo = userInfo.getUserNo();
				String balckList = JedisUtils.get(JedisUtils.KEY_PREFIX+":balckList");
				if(balckList!=null && balckList.length() > 0){
					String[] balckLists = balckList.split(",");
					boolean isbalck = false;
					for(String balck : balckLists){
						if(balck.equals(userNo)){
							isbalck = true;
							break;
						}
					}
					if(isbalck){
						String key_balckUrl = JedisUtils.KEY_PREFIX+":balckUrl";
						String balckUrl = request.getContextPath() + "/weixin110";
						if( JedisUtils.exists(key_balckUrl) ){
							String temp = JedisUtils.get( key_balckUrl );
							if( temp != null && temp.length() > 1){
								balckUrl  = JedisUtils.get( key_balckUrl );								
							}
						} 
						response.sendRedirect(balckUrl);
						//response.sendRedirect("http://www.qq.com");
						return false;
					}
				}
			}
		} catch (Exception e) {
			//设置编码和返回格式
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("html/text; charset=utf-8");  
			//获取输出
			PrintWriter out = response.getWriter();
			out.print("系统异常");
			out.close();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
	@SuppressWarnings("all")
	private TreeMap<String, String> getRequestParams(HttpServletRequest request) throws UnsupportedEncodingException {
		TreeMap<String, String> requestParams = new TreeMap<String, String>();
		
		Map map = request.getParameterMap();
		Set keSet = map.entrySet();
		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			//获取key
			String key = me.getKey().toString();;
			//获取value
			Object valueObj = me.getValue();
			String[] values = new String[1];
			if (valueObj instanceof String[]) {
				values = (String[]) valueObj;
			} else {
				values[0] = valueObj.toString();
			}
			String value = values[0];
			//添加到map中
			requestParams.put(key, value);
		}
		
		return requestParams;
	}
}
