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
 * 用户工具类
 * @ClassName UserUtil
 * @Description 
 */
public class UserBalanceUtil {
	
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
	 * 改变用户余额
	 * @Title changeBalance
	 * @Description 
	 * @param userInfo
	 * @param money
	 * @throws HongbaoException
	 */
	public static void changeBalance(String userId,BigDecimal money,String remark,HttpServletRequest request) throws HongbaoException {
		
		//获取用户余额锁
		KeyLock<String> userBalanceLock = LockUtils.getUserBalanceLock();
		//锁住用户id
		userBalanceLock.lock(userId);
		
		try {
			doChangeBalance(userId, money, remark,request);
		}
		catch(HongbaoException hongbao) {
			throw hongbao;
		}
		catch (Exception e) {
			e.printStackTrace();
			//设置系统异常
			throw new HongbaoException(ResultCodeConstants.C1005);
		}
		finally {
			//操作完成解锁
			userBalanceLock.unlock(userId);
		}
		
	}
    
	
	/**
	 * 执行余额修改操作
	 * @Title doChangeBalance
	 * @Description 
	 * @param userInfo
	 * @param money
	 * @param remark
	 */
	public static void doChangeBalance(String userId,BigDecimal money,String remark,HttpServletRequest request) {
		UserInfo userInfo = userInfoService.get(userId);
		if(userInfo==null) {
			//0012:用户不存在
			throw new HongbaoException(ResultCodeConstants.C0012,"用户不存在");
		}
		
		//记录旧的信息
		BigDecimal oldBalance = userInfo.getBalance();
		//变更后的总参与人次
		BigDecimal changeBalance = BigDecimal.ZERO;
		//操作类型
		String type = "";
		//更改后余额
		changeBalance = userInfo.getBalance().add(money);
		//增加
		if(money.compareTo(BigDecimal.ZERO)==1) {
			type = "增加";
		}
		//减少
		else {
			//判断小于0
			if(changeBalance.compareTo(BigDecimal.ZERO)==-1) {
				//0012:总参与人次溢出
				throw new HongbaoException(ResultCodeConstants.C0012,"用户余额溢出");
			}
			type = "减少";
		}
		//修改用户余额
		userInfo.setBalance(changeBalance);
		
		String msg = "用户【"+userInfo.getUserNo()+"】余额进行【"+type+"】操作，操作金额【"+ money.toString() +"】，余额由【"+oldBalance.toString()+"】变更为【"+userInfo.getBalance().toString()+"】，操作说明【"+remark+"】";
		logger.info(msg);
		writeFailDodMsg(userInfo.getUserNo(), time.format(new Date())+"...."+msg,request);
		msg = null;
		userInfoService.updateBalance(userInfo);
		
	}
	
	/**
	 * 写用户log
	 * @param userNo
	 * @param msg
	 */
	public static void writeFailDodMsg(String userNo, String msg,HttpServletRequest request) { 
        String logAddress = request.getSession().getServletContext().getRealPath("");
        String fileAddress = logAddress + File.separator + "log" + File.separator + userNo + ".log";
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
