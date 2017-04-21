package com.hongbao.mobile.modules.pay.util;

import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.HttpClientUtil2;
import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.Md5Utils;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.service.PayInfoService;

/**
 * 浦发银行支付
 * @ClassName PayPufaUtil
 * @Description 
 */
public class PayPufaUtil {
	public static Logger logger = LoggerFactory.getLogger(PayInfoService.class);
	
	/**
	 * 创建浦发转盘红包支付
	 * @Title createPufaHongbaoDrawPay
	 * @Description 
	 * @param payConfig
	 * @param order_no
	 * @param body
	 * @param attach
	 * @param sub_openid
	 * @param total_fee
	 * @param callbackParam
	 * @return
	 * @throws HongbaoException
	 */
	public static String createPufaHongbaoDrawPay(PayConfig payConfig,String order_no,String body,String attach,Integer total_fee,String callbackParam) throws HongbaoException {
		return createPufaPay(payConfig, order_no, body, attach, total_fee, payConfig.getPayDrawAsyncNotifyUrl(), payConfig.getPayDrawSyncNotifyUrl(), callbackParam);
	}
	
	/**
	 * 创建浦发银行支付
	 * @Title createPufaPay
	 * @Description 
	 * @param payConfig
	 * @param order_no
	 * @param body
	 * @param attach
	 * @param sub_openid
	 * @param total_fee
	 * @param notify_url
	 * @param callback_url
	 * @param callbackParam
	 * @return
	 * @throws HongbaoException
	 */
	public static String createPufaPay(PayConfig payConfig,String order_no,String body,String attach,Integer total_fee,String notify_url,String callback_url,String callbackParam) throws HongbaoException {
		String payUrl = "";
		try {
			String url = payConfig.getReqUrl();
			SortedMap<String,String> params = new TreeMap<String,String>();
			String para_id = payConfig.getMchId(); // 我方分配的商户编号
			String app_id = payConfig.getAppId(); //我方分配的产品编号
			
			String key = payConfig.getKey();
			
			//订单编号
	        params.put("order_no", payConfig.getOrderPrefix()+order_no);
	        //商品信息
	        params.put("body", body);
	        //附加信息
	        params.put("attach", attach);
			//总金额(单位：分 整型)
	        params.put("total_fee", total_fee+"");
	        
	        params.put("para_id", para_id);
	        params.put("app_id", app_id);

	        //通知地址
	        params.put("notify_url", notify_url);
	        //回调地址
	        String callbackUrl = callback_url+(StringUtils.isNoneBlank(callbackParam)?"?call_param="+callbackParam:"");
	        params.put("callback_url",callbackUrl);
	        
	    	//添加openid
	        params.put("sub_openid",IdGen.uuid());
	        
	        //生成签名
	        String sign = Md5Utils.getMD5LowerCase(para_id+app_id+order_no+total_fee+key);
	        params.put("sign", sign);
	        
	        String result = HttpClientUtil2.postData(url, params, 10000);
	        JSONObject jobj = JSONObject.fromObject(result);
	        if(!jobj.getString("status").equals("0")) {
	        	logger.error("创建订单失败记录："+jobj.toString());
	        	throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单失败，请重试");
	        }
	        payUrl = jobj.getString("payurl");
		}
		catch (HongbaoException hongbao) {
			throw hongbao;
		}
		catch (Exception e) {
			logger.error("支付创建异常："+e.getMessage());
			throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单异常，请重试");
		}
		return payUrl;
		
	}
}
