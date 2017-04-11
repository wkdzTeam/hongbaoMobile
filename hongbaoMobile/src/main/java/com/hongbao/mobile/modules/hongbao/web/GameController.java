/**
 * Copyright &copy; 2015-2016 <a href="http://www.duobao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.hongbao.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.user.entity.UserInfo;

/**
 * 游戏Controller
 */
@Controller
@RequestMapping(value = "/game")
public class GameController extends BaseController {
	
	/**
	 * 抽奖plus
	 * @Title luckyDrawPlus
	 * @Description 
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "luckyDrawPlus")
	public String luckyDrawPlus(HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		return "modules/game/luckyDrawPlus";
	}
	
	/**
	 * 执行抽奖plus 
	 * @Title doLuckyDrawPlus
	 * @Description 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doLuckyDrawPlus", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String doLuckyDrawPlus(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			//查询用户信息
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			if(!userInfo.getUserNo().equals("10001") && !userInfo.getUserNo().equals("10002")) {
				//从redis中获取用户编号是否存在
				String redisKey = "doLuckyDrawPlus:userNo:"+userInfo.getUserNo();
				String ipExist = JedisUtils.get(redisKey);
				if(StringUtils.isNotBlank(ipExist)) {
					throw new HongbaoException(ResultCodeConstants.C0012,"您已参与过抽奖，谢谢参与");
				}
				JedisUtils.set(redisKey, userInfo.getUserNo());
			}
			
			//返回成功
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//获取幸运号码
			returnJson.put("luckyNum", "2");
			//获取商品名称
			returnJson.put("itemName", "车载手机支架");
		} catch (HongbaoException duobao) {
			//捕获业务异常
			return duobao.getResultJson();
		} catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		return returnJson.toString();
	}
}