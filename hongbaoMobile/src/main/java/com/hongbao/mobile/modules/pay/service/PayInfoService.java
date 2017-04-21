/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Hongbao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.pay.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.persistence.Page;
import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.channeluser.service.ChannelUserService;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.pay.dao.PayInfoDao;
import com.hongbao.mobile.modules.pay.entity.PayInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.user.service.UserOauthService;
import com.hongbao.mobile.modules.weixin.service.WeixinService;
import com.hongbao.mobile.modules.yongjin.service.YongjinInfoService;

/**
 * 支付信息Service
 */
@Service
@Transactional(readOnly = true)
public class PayInfoService extends CrudService<PayInfoDao, PayInfo> {
	//日志
	public static Logger logger = LoggerFactory.getLogger(PayInfoService.class);
	
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
	 * 微信service
	 */
	@Autowired
	private WeixinService weixinService;
	
	/**
	 * 渠道
	 */
	@Autowired
	private ChannelUserService channelUserService;
	
	/**
	 * 红包信息Service
	 */
	@Autowired
	private HongbaoInfoService hongbaoInfoService;
	
	/**
	 * 佣金信息Service
	 */
	@Autowired
	private YongjinInfoService yongjinInfoService;
	
	public PayInfo get(String id) {
		return super.get(id);
	}
	
	public List<PayInfo> findList(PayInfo payInfo) {
		return super.findList(payInfo);
	}
	
	public Page<PayInfo> findPage(Page<PayInfo> page, PayInfo payInfo) {
		return super.findPage(page, payInfo);
	}
	
	@Transactional(readOnly = false)
	public int save(PayInfo payInfo) {
		return super.save(payInfo);
	}
	
	@Transactional(readOnly = false)
	public int delete(PayInfo payInfo) {
		return super.delete(payInfo);
	}
	
	
}