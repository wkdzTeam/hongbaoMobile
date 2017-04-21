/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.yongjin.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

/**
 * 佣金信息Controller
 */
@Controller
@RequestMapping(value = "/yongjin")
public class YongjinInfoController extends BaseController {

	@Autowired
	private YongjinInfoService yongjinInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 测试佣金
	 *//*
	@RequestMapping(value = "/yongjin_test")
	@ResponseBody
	public String yongjin_test(HttpServletRequest request, HttpServletResponse response) {
		//获取登录用户
		UserInfo userInfo = userInfoService.get("42f2e45b3e6e436e96a62775fb6e306f");
		yongjinInfoService.DrawYongjin(userInfo.getId(), "1", "123123", new BigDecimal("5"));
		return "ok";
	}*/
	
}