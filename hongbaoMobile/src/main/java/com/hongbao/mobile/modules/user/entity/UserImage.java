package com.hongbao.mobile.modules.user.entity;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 用户图片Entity
 * @ClassName UserImage
 * @Description 
 */
public class UserImage extends DataEntity<UserImage> {

	private static final long serialVersionUID = -706328578233899191L;
	
	/**
     * 用户id
     */
    private String userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原文件名
     */
    private String oldFileName;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 路径
     */
    private String path;

    /**
     * 七牛云存储key
     */
    private String qiniuKey;

    /**
     * 文件哈希码
     */
    private String fileHash;

    /**
     * 图片类型（1：图片，2：头像）
     */
    private String imageType;


    /**
     * 获取 用户id
     *
     * @return 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置  用户id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 文件名
     *
     * @return 
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置  文件名
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取 原文件名
     *
     * @return 
     */
    public String getOldFileName() {
        return oldFileName;
    }

    /**
     * 设置  原文件名
     *
     * @param oldFileName
     */
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName == null ? null : oldFileName.trim();
    }

    /**
     * 获取 后缀
     *
     * @return 
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置  后缀
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * 获取 路径
     *
     * @return 
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置  路径
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取 七牛云存储key
     *
     * @return 
     */
    public String getQiniuKey() {
        return qiniuKey;
    }

    /**
     * 设置  七牛云存储key
     *
     * @param qiniuKey
     */
    public void setQiniuKey(String qiniuKey) {
        this.qiniuKey = qiniuKey == null ? null : qiniuKey.trim();
    }

    /**
     * 获取 文件哈希码
     *
     * @return 
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * 设置  文件哈希码
     *
     * @param fileHash
     */
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash == null ? null : fileHash.trim();
    }

    /**
     * 获取 图片类型（1：图片，2：头像）
     *
     * @return 
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * 设置  图片类型（1：图片，2：头像）
     *
     * @param imageType
     */
    public void setImageType(String imageType) {
        this.imageType = imageType == null ? null : imageType.trim();
    }

}