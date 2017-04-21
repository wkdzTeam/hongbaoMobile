package com.hongbao.mobile.modules.pay.service;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;

/**
 * 支付通知
 * @ClassName PayNotify
 * @Description 
 */
public class PayNotify {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private String hongbaoNo;
	private ReadWriteLock lock;
	
	public PayNotify(String hongbaoNo) {  
		this.hongbaoNo = hongbaoNo;
        lock = new ReentrantReadWriteLock(); 
	}
	
	/**
	 * 同步通知
	 * @Title syncNotify
	 * @Description 
	 */
	public void syncNotify(HongbaoInfoService hongbaoInfoService) throws HongbaoException {
		logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】进入同步通知");
		//写入锁
		lock.writeLock().lock();
		try {
			logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】执行同步通知");
			//查询订单信息
			HongbaoInfo hongbaoInfo = hongbaoInfoService.getByHongbaoNo(hongbaoNo);
			if(hongbaoInfo!=null && !"1".equals(hongbaoInfo.getPayFlag())) {
				logger.info("修改红包【"+ hongbaoNo +"】为支付确认中");
				//修改支付标识（确认中）
				hongbaoInfo.setPayFlag("-1");
				//修改红包信息
				hongbaoInfoService.update(hongbaoInfo);
			}
		} 
		catch (HongbaoException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//释放写入锁
			lock.writeLock().unlock();
		}
		logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】同步通知结束");
	}
	
	/**
	 * 异步通知
	 * @Title asyncNotify
	 * @Description 
	 * @throws HongbaoException
	 */
	public void asyncNotify(HongbaoInfoService hongbaoInfoService,String trade_code,String trade_paycode) throws HongbaoException {
		logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】进入异步通知");
		//写入锁
		lock.writeLock().lock();
		try {
			logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】执行异步通知");
			//查询订单信息
			HongbaoInfo hongbaoInfo = hongbaoInfoService.getByHongbaoNo(hongbaoNo);
			if(hongbaoInfo==null) {
				throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
			}
			//判断是待支付的情况
			if(!"1".equals(hongbaoInfo.getPayFlag())) {
				//完成支付订单信息
				hongbaoInfo = hongbaoInfoService.finishPayOrder(hongbaoNo,trade_code,trade_paycode);
			}
		} 
		catch (HongbaoException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//释放写入锁
			lock.writeLock().unlock();
		}
		logger.info("【"+Thread.currentThread().getName()+"】【"+hongbaoNo+"】异步通知结束");
	}
}
