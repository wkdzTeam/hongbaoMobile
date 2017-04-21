/**
 * Copyright &copy; 2015-2016 <a href="http://www.hongbao.com">Duobao</a> All rights reserved.
 */
package com.hongbao.mobile.modules.channeluser.entity;

import org.hibernate.validator.constraints.Length;

import com.hongbao.mobile.common.persistence.DataEntity;

/**
 * 渠道信息Entity
 */
public class ChannelUser extends DataEntity<ChannelUser> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 渠道登录名称
	 */
	private String channelLoginname;
			
	/**
	 * 渠道登录密码
	 */
	private String channelPassword;
			
	/**
	 * 名字
	 */
	private String channelName;
			
	/**
	 * 座机
	 */
	private String phone;
			
	/**
	 * 手机
	 */
	private String mobile;
			
	/**
	 * QQ
	 */
	private String qq;
			
	/**
	 * email
	 */
	private String email;
			
	/**
	 * 银行名字，支付宝
	 */
	private String bankname;
			
	/**
	 * 银行号码，支付宝账号
	 */
	private String banknumber;
			
	/**
	 * 1:cps
	 */
	private String intoType;
			
	/**
	 * 分成100百分比
	 */
	private String intoScale;
			
	/**
	 * 扣量比例
	 */
	private String deductedScale;
			
	/**
	 * 0:使用,1:停用
	 */
	private String status;
			
	/**
	 * 父类渠道id
	 */
	private String parentChid;
			
	/**
	 * 最后登陆IP
	 */
	private String loginIp;
			
	/**
	 * 最后登陆时间
	 */
	private Long loginDate;
			
	/**
	 * 备注
	 */
	private String remark;
			
	
	public ChannelUser() {
		super();
	}

	public ChannelUser(String id){
		super(id);
	}

	@Length(min=1, max=20, message="渠道登录名称长度必须介于 1 和 20 之间")
	/**
	 * 获取  渠道登录名称
	 */
	public String getChannelLoginname() {
		return channelLoginname;
	}

	/**
	 * 设置  渠道登录名称
     * @param channelLoginname
	 */
	public void setChannelLoginname(String channelLoginname) {
		this.channelLoginname = channelLoginname;
	}
	
	@Length(min=1, max=50, message="渠道登录密码长度必须介于 1 和 50 之间")
	/**
	 * 获取  渠道登录密码
	 */
	public String getChannelPassword() {
		return channelPassword;
	}

	/**
	 * 设置  渠道登录密码
     * @param channelPassword
	 */
	public void setChannelPassword(String channelPassword) {
		this.channelPassword = channelPassword;
	}
	
	@Length(min=1, max=20, message="名字长度必须介于 1 和 20 之间")
	/**
	 * 获取  名字
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * 设置  名字
     * @param channelName
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=20, message="座机长度必须介于 0 和 20 之间")
	/**
	 * 获取  座机
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置  座机
     * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=20, message="手机长度必须介于 0 和 20 之间")
	/**
	 * 获取  手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置  手机
     * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=20, message="QQ长度必须介于 0 和 20 之间")
	/**
	 * 获取  QQ
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 设置  QQ
     * @param qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=50, message="email长度必须介于 0 和 50 之间")
	/**
	 * 获取  email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置  email
     * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=50, message="银行名字，支付宝长度必须介于 0 和 50 之间")
	/**
	 * 获取  银行名字，支付宝
	 */
	public String getBankname() {
		return bankname;
	}

	/**
	 * 设置  银行名字，支付宝
     * @param bankname
	 */
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@Length(min=0, max=50, message="银行号码，支付宝账号长度必须介于 0 和 50 之间")
	/**
	 * 获取  银行号码，支付宝账号
	 */
	public String getBanknumber() {
		return banknumber;
	}

	/**
	 * 设置  银行号码，支付宝账号
     * @param banknumber
	 */
	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}
	
	@Length(min=0, max=2, message="1:cps长度必须介于 0 和 2 之间")
	/**
	 * 获取  1:cps
	 */
	public String getIntoType() {
		return intoType;
	}

	/**
	 * 设置  1:cps
     * @param intoType
	 */
	public void setIntoType(String intoType) {
		this.intoType = intoType;
	}
	
	@Length(min=0, max=5, message="分成100百分比长度必须介于 0 和 5 之间")
	/**
	 * 获取  分成100百分比
	 */
	public String getIntoScale() {
		return intoScale;
	}

	/**
	 * 设置  分成100百分比
     * @param intoScale
	 */
	public void setIntoScale(String intoScale) {
		this.intoScale = intoScale;
	}
	
	@Length(min=0, max=5, message="扣量比例长度必须介于 0 和 5 之间")
	/**
	 * 获取  扣量比例
	 */
	public String getDeductedScale() {
		return deductedScale;
	}

	/**
	 * 设置  扣量比例
     * @param deductedScale
	 */
	public void setDeductedScale(String deductedScale) {
		this.deductedScale = deductedScale;
	}
	
	@Length(min=0, max=2, message="0:使用,1:停用长度必须介于 0 和 2 之间")
	/**
	 * 获取  0:使用,1:停用
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置  0:使用,1:停用
     * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="父类渠道id长度必须介于 0 和 50 之间")
	/**
	 * 获取  父类渠道id
	 */
	public String getParentChid() {
		return parentChid;
	}

	/**
	 * 设置  父类渠道id
     * @param parentChid
	 */
	public void setParentChid(String parentChid) {
		this.parentChid = parentChid;
	}
	
	@Length(min=0, max=40, message="最后登陆IP长度必须介于 0 和 40 之间")
	/**
	 * 获取  最后登陆IP
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置  最后登陆IP
     * @param loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	/**
	 * 获取  最后登陆时间
	 */
	public Long getLoginDate() {
		return loginDate;
	}

	/**
	 * 设置  最后登陆时间
     * @param loginDate
	 */
	public void setLoginDate(Long loginDate) {
		this.loginDate = loginDate;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	/**
	 * 获取  备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置  备注
     * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}