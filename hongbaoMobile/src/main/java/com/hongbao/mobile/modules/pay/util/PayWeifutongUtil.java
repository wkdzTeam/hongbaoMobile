package com.hongbao.mobile.modules.pay.util;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.DateUtils;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.entity.PayDataInfo;
import com.hongbao.mobile.modules.pay.service.PayInfoService;

public class PayWeifutongUtil {
	
	public static Logger logger = LoggerFactory.getLogger(PayInfoService.class);

	/**
	 * 创建威富通转盘红包支付
	 * @Title createWeifutongHongbaoDrawPay
	 * @Description 
	 * @param out_trade_no
	 * @param body
	 * @param attach
	 * @param sub_openid
	 * @param total_fee
	 * @param createDate
	 * @param callbackParam
	 * @return
	 * @throws HongbaoException
	 */
	public static PayDataInfo createWeifutongHongbaoDrawPay(String out_trade_no,String body,String attach,String sub_openid,Integer total_fee,Long createDate,String callbackParam) throws HongbaoException {
		PayConfig payConfig = PayUtil.getPayConfig();
		return createWeifutongHongbaoDrawPay(payConfig,out_trade_no, body, attach, sub_openid, total_fee, createDate, callbackParam);
	}
	
	/**
	 * 创建威富通转盘红包支付
	 * @Title createWeifutongHongbaoDrawPay
	 * @Description 
	 * @param out_trade_no
	 * @param body
	 * @param attach
	 * @param sub_openid
	 * @param total_fee
	 * @param createDate
	 * @return
	 * @throws HongbaoException
	 */
	public static PayDataInfo createWeifutongHongbaoDrawPay(PayConfig payConfig,String out_trade_no,String body,String attach,String sub_openid,Integer total_fee,Long createDate,String callbackParam) throws HongbaoException {
		return createWeifutongPay(payConfig,out_trade_no, body, attach, sub_openid, total_fee, createDate, payConfig.getPayDrawAsyncNotifyUrl(), payConfig.getPayDrawSyncNotifyUrl(),callbackParam);
	}
	
	/**
	 * 创建威富通支付
	 * @Title createWeifutongPay
	 * @Description 
	 * @param out_trade_no
	 * @param body
	 * @param attach
	 * @param sub_openid
	 * @param total_fee
	 * @param createDate
	 * @param notify_url
	 * @param callback_url
	 * @return
	 * @throws HongbaoException
	 */
	public static PayDataInfo createWeifutongPay(PayConfig payConfig,String out_trade_no,String body,String attach,String sub_openid,Integer total_fee,Long createDate,String notify_url,String callback_url,String callbackParam) throws HongbaoException {
		SortedMap<String,String> param = new TreeMap<String,String>();
		
		//订单编号
        param.put("out_trade_no", payConfig.getOrderPrefix()+out_trade_no);
        //商品信息
        param.put("body", body);
        //附加信息
        param.put("attach", attach);
		//总金额(单位：分 整型)
        param.put("total_fee", total_fee+"");
        //终端IP
        param.put("mch_create_ip", IpUtils.getLocalIP());
        //订单创建时间
        param.put("time_start", DateUtils.parseTimestamp(createDate, "yyyyMMddHHmmss"));
        //订单超时时间
		Long timeout = new Long(Hongbao.getConfig("order.timeout"));
        param.put("time_expire", DateUtils.parseTimestamp(createDate+(timeout*1000), "yyyyMMddHHmmss"));

        //通知地址
        param.put("notify_url", notify_url);
        //回调地址
        String callbackUrl = callback_url+(StringUtils.isNoneBlank(callbackParam)?"?call_param="+callbackParam:"");
        param.put("callback_url",callbackUrl);
        
        //版本号
        param.put("version", "1.0");
		//字符集
        param.put("charset", "UTF-8");
		//签名方式
        param.put("sign_type", "MD5");
        
        //随机数
        param.put("nonce_str", String.valueOf(new Date().getTime()));
        
		//接口类型
        param.put("service", "pay.weixin.jspay");
		//威富通商户号
        param.put("mch_id", payConfig.getMchId());
    	//添加openid
        param.put("sub_openid",sub_openid);
        
        String key = payConfig.getKey();
        //生成签名
        String sign = SignUtils.makeSign(param, key);
        param.put("sign", sign);
        
        //请求地址
        String reqUrl = payConfig.getReqUrl();
        //获取请求结果
        Map<String, String> resultMap = post(reqUrl, param);
		if (!("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code")))) {
			logger.error("订单失败记录："+resultMap.toString());
			throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单失败，请重试");
		}
		if (!SignUtils.checkParam(resultMap, key)) {
			logger.error("验证签名不通过");
			throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单异常，请重试");
		}
		//订单支付信息
        PayDataInfo payWeifutongInfo = new PayDataInfo();
        
    	//威富通的预支付ID
    	String tokenId = resultMap.get("token_id");
    	payWeifutongInfo.setTokenId(tokenId);
		//支付地址
        String payUrl = payConfig.getPayUrl();
        payUrl = payUrl+"?token_id="+tokenId+"&showwxtitle=1";
        payWeifutongInfo.setPayUrl(payUrl);

        return payWeifutongInfo;
	}
	
	/**
	 * post请求
	 * @Title post
	 * @Description 
	 * @param url
	 * @param param
	 * @return
	 * @throws HongbaoException
	 */
	public static Map<String, String> post(String url, SortedMap<String, String> param) throws HongbaoException {
		Map<String, String> resultMap = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			String reqParams = XmlUtils.parseXML(param);
			StringEntity entityParams = new StringEntity(reqParams, "utf-8");
			httpPost.setEntity(entityParams);
			// httpPost.setHeader("Content-Type","text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			response = client.execute(httpPost);
			if (response != null && response.getEntity() != null) {
				resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
			} else {
				throw new HongbaoException(ResultCodeConstants.C0012);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HongbaoException(ResultCodeConstants.C1005);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new HongbaoException(ResultCodeConstants.C1005);
			}
			
		}
		return resultMap;
	}
	
}
