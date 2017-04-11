package com.hongbao.mobile.modules.user.dao;

import com.hongbao.mobile.common.persistence.CrudDao;
import com.hongbao.mobile.common.persistence.annotation.MyBatisDao;
import com.hongbao.mobile.modules.user.entity.UserImage;

/**
 * 用户图片Dao
 * @ClassName UserImageDao
 * @Description 
 */
@MyBatisDao
public interface UserImageDao extends CrudDao<UserImage> {
	
	/**
	 * 去掉用户所有图片的头像标识
	 * @Title setAvatarFlagNo
	 * @Description 
	 * @param userId
	 * @return
	 */
	public int setAvatarFlagNo(String userId);
}