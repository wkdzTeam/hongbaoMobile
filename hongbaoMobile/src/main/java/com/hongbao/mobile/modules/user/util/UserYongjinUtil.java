package com.hongbao.mobile.modules.user.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.lock.KeyLock;
import com.hongbao.mobile.common.lock.LockUtils;
import com.hongbao.mobile.common.utils.SpringContextHolder;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;

/**
 * 用户佣金工具类
 * @ClassName UserUtil
 * @Description 
 */
public class UserYongjinUtil {
	
	/**
	 * 用户信息Service
	 */
	private static UserInfoService userInfoService = SpringContextHolder.getBean(UserInfoService.class);
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	/**
	 * 时间
	 */
	private static final SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss"); 
	

	/**
	 * 改变用户佣金
	 * @Title changeYongjin
	 * @Description 
	 * @param userInfo
	 * @param money
	 * @throws HongbaoException
	 */
	public static void changeYongjin(String userId,BigDecimal money,String remark,HttpServletRequest request) throws HongbaoException {
		//获取用户佣金锁
		KeyLock<String> userYongjinLock = LockUtils.getUserYongjinLock();
		//锁住用户id
		userYongjinLock.lock(userId);
		
		try {
			doChangeYongjin(userId, money, remark,request);
		} catch(HongbaoException hongbao) {
			throw hongbao;
		} catch (Exception e) {
			e.printStackTrace();
			//设置系统异常
			throw new HongbaoException(ResultCodeConstants.C1005);
		} finally {
			//操作完成解锁
			userYongjinLock.unlock(userId);
		}
		
	}
    
	
	/**
	 * 执行佣金修改操作
	 * @Title doChangeYongjin
	 * @Description 
	 * @param userInfo
	 * @param money
	 * @param remark
	 */
	public static void doChangeYongjin(String userId,BigDecimal money,String remark,HttpServletRequest request) throws HongbaoException {
		UserInfo userInfo = userInfoService.get(userId);
		if(userInfo==null) {
			//0012:用户不存在
			throw new HongbaoException(ResultCodeConstants.C0012,"用户不存在");
		}
		
		//保留2位小数
		//money = money.setScale(2);
		
		//记录旧的信息
		BigDecimal oldYongjin = userInfo.getYongjin();
		//变更后的总参与人次
		BigDecimal changeYongjin = BigDecimal.ZERO;
		//操作类型
		String type = "";
		//更改后佣金
		changeYongjin = userInfo.getYongjin().add(money);
		//增加
		if(money.compareTo(BigDecimal.ZERO)==1) {
			type = "增加";
		}
		//减少
		else {
			//判断小于0
			if(changeYongjin.compareTo(BigDecimal.ZERO)==-1) {
				//0012:总参与人次溢出
				throw new HongbaoException(ResultCodeConstants.C0012,"用户佣金溢出");
			}
			type = "减少";
		}
		//修改用户佣金
		userInfo.setYongjinAmount(money);
		
		String msg = "用户【"+userInfo.getUserNo()+"】佣金进行【"+type+"】操作，操作金额【"+ money.toString() +"】，佣金由【"+oldYongjin.toString()+"】变更为【"+changeYongjin.toString()+"】，操作说明【"+remark+"】";
		logger.info(msg);
		writeFailDodMsg(userInfo.getUserNo(), time.format(new Date())+"...."+msg, request);
		msg = null;
		userInfoService.updateYongjin(userInfo);
		
	}
	
	/**
	 * 写用户log
	 * @param userNo
	 * @param msg
	 */
	public static void writeFailDodMsg(String userNo, String msg, HttpServletRequest request) { 
        //String logAddress = Hongbao.getConfig("user.log.path");
        //String fileAddress = logAddress + File.separator + userNo + "_yongjin.log";
		String logAddress = request.getSession().getServletContext().getRealPath("");
	    String fileAddress = logAddress + File.separator + "log" + File.separator + userNo + "_yongjin.log";
        File file = new File(fileAddress); 
        if (!file.exists()) {
            try {  
                file.createNewFile();   
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("time:"+time.format(new Date())+",userNo:"+userNo+"........msg:"+msg);
            } 
        }
        try {
            //true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileAddress, true);
            writer.write(msg+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("time:"+time.format(new Date())+",userNo:"+userNo+".........msg:"+msg);
        }

    }  
}
