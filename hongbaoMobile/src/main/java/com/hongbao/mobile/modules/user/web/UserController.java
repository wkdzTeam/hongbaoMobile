package com.hongbao.mobile.modules.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;

/**
 * 用户信息Controller
 * @ClassName UserController
 * @Description 
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	
	/**
	 * 用户信息Service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	
	/**
	 * 注销
	 * @Title doLogout
	 * @Description 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doLogout")
	public String doLogout(Model model,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			
			//移除用户信息
			session.removeAttribute("userInfo");
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			addMessage(model, hongbao.getResultCode().getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			//捕获业务异常
			addMessage(model, "系统异常");
		}
		return "redirect:/index";
	}
	
	/**
	 * 查询用户余额
	 * @Title getBalance
	 * @Description 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getBalance", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getBalance(Model model,HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			//获取登录用户信息
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			userInfo = userInfoService.get(userInfo.getId());
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//设置余额
			returnJson.put("balance", userInfo.getBalance());
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
	
}