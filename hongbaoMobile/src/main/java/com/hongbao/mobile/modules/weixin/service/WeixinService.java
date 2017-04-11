package com.hongbao.mobile.modules.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.mapper.JsonMapper;
import com.hongbao.mobile.common.utils.StringUtils;
import com.hongbao.mobile.modules.weixin.entity.TemplateData;
import com.hongbao.mobile.modules.weixin.entity.WeiXinTempletMsg;
import com.hongbao.mobile.modules.weixin.entity.WeixinMenu;
import com.hongbao.mobile.modules.weixin.exception.WeixinException;
import com.hongbao.mobile.modules.weixin.util.WeixinUtil;

/**
 * 微信Service
 * @ClassName WeixinService
 * @Description 
 */
@Service
@Transactional(readOnly = true)
public class WeixinService {
	public static Logger logger = LoggerFactory.getLogger(WeixinService.class);
	
	/**
	 * 创建菜单
	 * @Title createMenu
	 * @Description 
	 * @param weixinMenuList
	 * @return
	 * @throws WeixinException
	 */
	public void createMenu(List<WeixinMenu> weixinMenuList) throws WeixinException {
		//获取access_token
		String accessToken = WeixinUtil.getAccessToken();
		if(StringUtils.isBlank(accessToken)) {
			throw new WeixinException("创建菜单失败，没有获取到accessToken！");
		}
		//获取创建菜单url
		String url = WeixinUtil.getCreateMenuUrl()+"?access_token="+accessToken;
		//转换成json数组
		JSONArray button = JSONArray.fromObject(weixinMenuList);
		//创建菜单json
		JSONObject menuJobj = new JSONObject();
		menuJobj.put("button", button);
		
		logger.info("微信创建菜单请求信息："+menuJobj.toString());
		//获取返回信息
		JSONObject result = WeixinUtil.clientPost(url, menuJobj.toString());
		logger.info("微信创建菜单返回信息："+result.toString());
	}
	
	/**
	 * 查询菜单
	 * @Title getMenu
	 * @Description 
	 * @return
	 * @throws WeixinException
	 */
	public List<WeixinMenu> getMenu() throws WeixinException {
		List<WeixinMenu> weixinMenuList = new ArrayList<WeixinMenu>();
		
		//获取access_token
		String accessToken = WeixinUtil.getAccessToken();
		if(StringUtils.isBlank(accessToken)) {
			throw new WeixinException("查询菜单失败，没有获取到accessToken！");
		}
		//获取查询菜单url
		String url = WeixinUtil.getGetMenuUrl()+"?access_token="+accessToken;
		//获取返回信息
		JSONObject result = WeixinUtil.clientGet(url);
		logger.info("微信查询菜单返回信息："+result.toString());
		
		//获取menu
		JSONObject menu = result.getJSONObject("menu");
		//获取button
		JSONArray button = menu.getJSONArray("button");
		for (Object buttonObj : button) {
			//转换成对象
			WeixinMenu weixinMenu = (WeixinMenu)JsonMapper.fromJsonString(buttonObj.toString(), WeixinMenu.class);
			weixinMenuList.add(weixinMenu);
		}
		logger.info("微信查询菜单转换实体类结果："+(JSONArray.fromObject(weixinMenuList)));
		return weixinMenuList;
	}
	
	/**
	 * 删除菜单
	 * @Title deleteMenu
	 * @Description 
	 * @throws WeixinException
	 */
	public void deleteMenu() throws WeixinException {
		//获取access_token
		String accessToken = WeixinUtil.getAccessToken();
		if(StringUtils.isBlank(accessToken)) {
			throw new WeixinException("查询菜单失败，没有获取到accessToken！");
		}
		//获取删除菜单url
		String url = WeixinUtil.getDeleteMenuUrl()+"?access_token="+accessToken;
		//获取返回信息
		JSONObject result = WeixinUtil.clientGet(url);
		logger.info("微信删除菜单返回信息："+result.toString());
	}
	
	/**
	 * 推送模板信息
	 * 将占位的{{*.DATA}}以,号隔开传传进来,开头是openId，模板Id,详情Id,后面的具体内容"key::value"
	 * temMsg.append("o4ZufwXtGqhaLF9oec1Xa4sIru_o,").append("z7QxHh4wNXDahSL8G7pJ-6_9RnoZSxJUhcdqGMh0EwU,")
		.append(Duobao.getDomain()+"/user/hongbaoRecord,").append("result::恭喜您，夺宝成功,").append("totalWinMoney::【移动100元充值卡】夺宝成功,")
		.append("issueInfo::2000001期,").append("fee::10人次,").append("betTime::2013-10-10 12:22:22,")
		.append("remark::开奖时间:2013-10-10 21:30\n幸运号码:123456\n\n\n请尽快前往领取");
	 * @Title createMenu
	 * @Description 
	 */
	public String sendTempletMsg(String templetMsg) throws WeixinException {
		//获取access_token
		String accessToken = WeixinUtil.getAccessToken();
		if(StringUtils.isBlank(accessToken)) {
			throw new WeixinException("推送模版信息失败，没有获取到accessToken！");
		}
		String[] templetMsgs = templetMsg.split(",");
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("touser", templetMsgs[0]);
		jsonobj.put("template_id",templetMsgs[1]);
		jsonobj.put("url",templetMsgs[2]);
		Map<String,TemplateData> data = new HashMap<String,TemplateData>();
		for (int i = 3; i < templetMsgs.length; i++) {
			String[] keyAndValue = templetMsgs[i].split("::");
			TemplateData templateData = new TemplateData();
			templateData.setValue(keyAndValue[1]);
			templateData.setColor("#000000");
			data.put(keyAndValue[0],templateData);
		}
		jsonobj.put("data", data);
		JSONObject result = WeixinUtil.clientPost(WeixinUtil.getSendTempletMsgUrl()+"?access_token="
					+accessToken,jsonobj.toString());
		logger.info("模板消息推送信息："+result.toString());
		return result.getString("errcode");
	}
	
	public static void main(String[] args) {
		StringBuffer temMsg = new StringBuffer();
		temMsg.append("o4ZufwXtGqhaLF9oec1Xa4sIru_o,").append("z7QxHh4wNXDahSL8G7pJ-6_9RnoZSxJUhcdqGMh0EwU,")
		.append(Hongbao.getDomain()+"/user/hongbaoRecord,").append("result::恭喜您，夺宝成功,").append("totalWinMoney::【移动100元充值卡】夺宝成功,")
		.append("issueInfo::2000001期,").append("fee::10人次,").append("betTime::2013-10-10 12:22:22,").append("remark::开奖时间:2013-10-10 21:30\n幸运号码:123456\n\n\n请尽快前往领取");
		new WeixinService().sendTempletMsg(temMsg.toString());
	}
}
