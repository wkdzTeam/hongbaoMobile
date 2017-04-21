/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.tousu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.tousu.entity.TousuInfo;
import com.hongbao.mobile.modules.tousu.service.TousuInfoService;
import com.hongbao.mobile.modules.user.entity.UserInfo;

/**
 * 用户投诉Controller
 */
@Controller
@RequestMapping(value = "/tousu")
public class TousuInfoController extends BaseController {

	@Autowired
	private TousuInfoService tousuInfoService;
	
	/**
	 * 投诉
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tousu",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject tousu(HttpServletRequest request, HttpServletResponse response, String type,String number, String content) {
		JSONObject object = new JSONObject();
		try {
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			TousuInfo tousuInfo = new TousuInfo();
			tousuInfo.setId(IdGen.uuid());
			tousuInfo.setUserNo(userInfo.getUserNo());
			tousuInfo.setNumber(number);
			tousuInfo.setContent(content);
			tousuInfo.setType(type); //类型  0：开始 
			tousuInfo.setDelFlag("0");
			tousuInfoService.insert(tousuInfo);
			
			object.put("status", 0);
			object.put("msg", "投诉成功");
		} catch (Exception e) {
			object.put("status", 1);
			object.put("msg", "投诉失败");
		}
		return object;
	}
}