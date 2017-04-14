package com.hongbao.mobile.modules.user.util;

import java.math.BigDecimal;

import com.hongbao.mobile.modules.user.entity.UserInfo;

public class UserInfoDataUtil 
{
	public static void fillUserInfo(UserInfo userInfo)
	{
		userInfo.setBalance(new BigDecimal(1000));
		userInfo.setId("12312465465797899");
	}
}
