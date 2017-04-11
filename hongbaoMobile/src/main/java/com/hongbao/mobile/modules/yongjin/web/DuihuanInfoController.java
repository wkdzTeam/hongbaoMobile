/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.yongjin.service.DuihuanInfoService;

/**
 * 兑换Controller
 */
@Controller
@RequestMapping(value = "/duihuan")
public class DuihuanInfoController extends BaseController {

	@Autowired
	private DuihuanInfoService duihuanInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 用户第三方登录记录Service
	 */
	@Autowired
	private UserOauthService userOauthService;
	
	/**
	 * 我的佣金兑换ajax请求
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/duihuan",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject duihuan(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject object = new JSONObject();
		try {
			object.put("status", 1);
			object.put("msg", "提现失败");
		} catch (Exception e) {
			object.put("status", 1);
			object.put("msg", "系统异常");
		}
		return object;
	}
	
	
	public static void main(String[] args) {
		BigDecimal decimal = new BigDecimal("32.12");
		//System.out.println((int)decimal.floatValue()*100);
		System.out.println(new BigDecimal("-"+decimal));
		
	}
	
	
	
	
}