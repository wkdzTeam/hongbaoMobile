package com.hongbao.mobile.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.mobile.common.service.CrudService;
import com.hongbao.mobile.modules.user.dao.UserImageDao;
import com.hongbao.mobile.modules.user.entity.UserImage;

/**
 * 用户图片Service
 * @ClassName UserImageService
 * @Description 
 */
@Service
@Transactional(readOnly = true)
public class UserImageService extends CrudService<UserImageDao, UserImage> {
	
	/**
	 * 去掉用户所有图片的头像标识
	 * @Title setAvatarFlagNo
	 * @Description 
	 * @param userId
	 * @return
	 */
	public int setAvatarFlagNo(String userId) {
		return dao.setAvatarFlagNo(userId);
	}
	
	/**
	 * 根据用户id查询头像信息
	 * @Title getAvatarUserId
	 * @Description 
	 * @param userId
	 * @return
	 */
	public UserImage getAvatarUserId(String userId) {
		UserImage param = new UserImage();
		param.setUserId(userId);
		param.setImageType("2");
		return this.getUserImage(param);
	}
	
	/**
	 * 获取用户图片信息
	 * @Title getUserImage
	 * @Description 
	 * @param param
	 * @return
	 */
	public UserImage getUserImage(UserImage param) {
		List<UserImage> userImageList = dao.findList(param);
		if(userImageList!=null && userImageList.size()>0) {
			return userImageList.get(0);
		}
		return null;
	}
}
