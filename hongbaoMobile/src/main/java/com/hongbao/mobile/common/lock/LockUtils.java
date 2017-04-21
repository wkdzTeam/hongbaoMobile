package com.hongbao.mobile.common.lock;

import java.util.HashMap;

public class LockUtils {
	
	/**
	 * 用户操作锁
	 */
	public static final String USER_OPERATION_LOCK = "user_operation_lock";
	
	/**
	 * 用户余额锁
	 */
	public static final String USER_BALANCE_LOCK = "user_balance_lock";
	
	/**
	 * 转盘红包锁
	 */
	public static final String HONGBAO_DRAW_LOCK = "hongbao_draw_lock";
	
	/**
	 * 用户佣金锁
	 */
	public static final String USER_YONGJIN_LOCK = "user_yongjin_lock";
	
	/**
	 * 锁列表
	 */
	private static String[] lockKeys = {USER_OPERATION_LOCK,USER_BALANCE_LOCK,HONGBAO_DRAW_LOCK, USER_YONGJIN_LOCK};
	
	/**
	 * 锁map
	 */
	private static HashMap<String, KeyLock<String>> lockMap;
	
	/**
	 * 获取用户操作锁
	 * @Title getUserOperationLock
	 * @Description 
	 * @return
	 */
	public static synchronized KeyLock<String> getUserOperationLock() {
		return getLockMap().get(USER_OPERATION_LOCK);
	}
	
	/**
	 * 获取用户余额锁
	 * @Title getUserBalanceLock
	 * @Description 
	 * @return
	 */
	public static synchronized KeyLock<String> getUserBalanceLock() {
		return getLockMap().get(USER_BALANCE_LOCK);
	}

	/**
	 * 获取转盘红包锁
	 * @Title getHongbaoDrawLock
	 * @Description 
	 * @return
	 */
	public static synchronized KeyLock<String> getHongbaoDrawLock() {
		return getLockMap().get(HONGBAO_DRAW_LOCK);
	}
	
	/**
	 * 获取用户佣金锁
	 * @return
	 */
	public static synchronized KeyLock<String> getUserYongjinLock() {
		return getLockMap().get(USER_YONGJIN_LOCK);
	}
	
	
	/**
	 * 获取锁map
	 * @Title getLockMap
	 * @Description 
	 * @return
	 */
	public static synchronized HashMap<String, KeyLock<String>> getLockMap() {
		if(lockMap==null) {
			//初始化锁map
			lockMap = new HashMap<>();
			for (String lockKey : lockKeys) {
				KeyLock<String> lock = new KeyLock<String>();
				lockMap.put(lockKey, lock);
			}
		}
		return lockMap;
	}
	
	/**
	 * 获取主键锁
	 * @Title getKeyLock
	 * @Description 
	 * @param key
	 * @return
	 */
	public static synchronized KeyLock<String> getKeyLock(String key) {
		return getLockMap().get(key);
	}
	
}
