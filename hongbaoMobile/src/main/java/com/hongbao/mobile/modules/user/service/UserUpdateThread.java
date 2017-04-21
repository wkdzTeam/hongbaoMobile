package com.hongbao.mobile.modules.user.service;

import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.entity.UserOauth;

public class UserUpdateThread extends Thread {
	
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	
	private UserOauthService userOauthService;
	private UserOauth userOauth;
	
	public UserUpdateThread(UserInfoService userInfoService,UserInfo userInfo,UserOauthService userOauthService,UserOauth userOauth) {
		this.userInfoService = userInfoService;
		this.userInfo = userInfo;
		this.userOauthService = userOauthService;
		this.userOauth = userOauth;
	}
	

	@Override
	public void run() {
		//保存用户
		userInfoService.updateNow(userInfo);
		//保存用户第三方登录记录
        userOauthService.update(userOauth);
	}
}
