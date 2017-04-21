package com.hongbao.mobile.modules.pay.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.HttpClientUtil2;
import com.hongbao.mobile.common.utils.IpUtils;
import com.hongbao.mobile.common.utils.Md5Utils;
import com.hongbao.mobile.common.utils.PropertiesLoader;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoDrawInfo;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;

/**
 * 盾行支付工具类
 * @ClassName PayUtil
 * @Description 
 */
public class PayDunxingUtil {
	
	public static Logger logger = LoggerFactory.getLogger(PayDunxingUtil.class);

	private static PropertiesLoader payConfig = null;

	/**
	 * 支付订单map
	 */
	private static HashMap<String, HongbaoInfo> payHongbaoInfoMap;
	
	/**
	 * 转盘红包支付订单map
	 */
	private static HashMap<String, HongbaoDrawInfo> payHongbaoDrawInfoMap;
	
	/**
	 * 获取支付订单map
	 * @Title getPayHongbaoInfoMap
	 * @Description 
	 * @return
	 */
	public synchronized static HashMap<String, HongbaoInfo> getPayHongbaoInfoMap() {
		if(payHongbaoInfoMap==null) {
			payHongbaoInfoMap = new HashMap<String, HongbaoInfo>();
		}
		return payHongbaoInfoMap;
	}
	
	/**
	 * 设置支付订单map数据
	 * @Title setPayHongbaoInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 * @param hongbaoInfo
	 */
	public synchronized static void setPayHongbaoInfoMapData(String hongbaoNo,HongbaoInfo hongbaoInfo) {
		getPayHongbaoInfoMap().put(hongbaoNo, hongbaoInfo);
	}
	
	/**
	 * 获取支付订单map数据
	 * @Title getPayHongbaoInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 */
	public synchronized static HongbaoInfo getPayHongbaoInfoMapData(String hongbaoNo) {
		return getPayHongbaoInfoMap().get(hongbaoNo);
	}

	/**
	 * 移除支付订单map数据
	 * @Title removePayHongbaoInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 */
	public synchronized static void removePayHongbaoInfoMapData(String hongbaoNo) {
		getPayHongbaoInfoMap().remove(hongbaoNo);
	}
	
	/**
	 * 获取转盘红包支付订单map
	 * @Title getPayHongbaoDrawInfoMap
	 * @Description 
	 * @return
	 */
	public synchronized static HashMap<String, HongbaoDrawInfo> getPayHongbaoDrawInfoMap() {
		if(payHongbaoDrawInfoMap==null) {
			payHongbaoDrawInfoMap = new HashMap<String, HongbaoDrawInfo>();
		}
		return payHongbaoDrawInfoMap;
	}
	
	/**
	 * 设置转盘红包支付订单map数据
	 * @Title setPayHongbaoDrawInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 * @param hongbaoInfo
	 */
	public synchronized static void setPayHongbaoDrawInfoMapData(String hongbaoNo,HongbaoDrawInfo hongbaoInfo) {
		getPayHongbaoDrawInfoMap().put(hongbaoNo, hongbaoInfo);
	}
	
	/**
	 * 获取转盘红包支付订单map数据
	 * @Title getPayHongbaoDrawInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 */
	public synchronized static HongbaoDrawInfo getPayHongbaoDrawInfoMapData(String hongbaoNo) {
		return getPayHongbaoDrawInfoMap().get(hongbaoNo);
	}

	/**
	 * 移除转盘红包支付订单map数据
	 * @Title removePayHongbaoDrawInfoMapData
	 * @Description 
	 * @param hongbaoNo
	 */
	public synchronized static void removePayHongbaoDrawInfoMapData(String hongbaoNo) {
		getPayHongbaoDrawInfoMap().remove(hongbaoNo);
	}

	/**
	 * 获取支付配置文件
	 * @Title getPayConfig
	 * @Description 
	 * @return
	 */
	public static PropertiesLoader getPayConfig() {
		//非空验证
		if(payConfig==null) {
			//获取配置文件
			payConfig = new PropertiesLoader("/properties/payDunxingConfig.properties");
		}
		return payConfig;
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
	public static JSONObject post(String url, HashMap<String, String> param) throws HongbaoException {
		JSONObject jobj = new JSONObject();
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		try {
			HttpPost httpPost = new HttpPost(url);

	        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
	        //请求参数
			for(String key : param.keySet()){
				params.add(new BasicNameValuePair(key,param.get(key))); 
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			// httpPost.setHeader("Content-Type","text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			response = client.execute(httpPost);
			if (response != null && response.getEntity() != null) {
				jobj = JSONObject.fromObject(response.getEntity());
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
		return jobj;
	}
	
	/**
	 * 获取应用key
	 * @Title getAppKey
	 * @Description 
	 * @return
	 */
	public static String getAppKey() {
		return getPayConfig().getProperty("app_key");
	}
	
	/**
	 * 获取应用秘钥
	 * @Title getAppSecret
	 * @Description 
	 * @return
	 */
	public static String getAppSecret() {
		return getPayConfig().getProperty("app_secret");
	}
	
	/**
	 * 获取支付接口地址
	 * @Title getPayApiUrl
	 * @Description 
	 * @return
	 */
	public static String getPayApiUrl() {
		return getPayConfig().getProperty("pay_api_url");
	}
	
	/**
	 * 获取商品编号
	 * @Title getItemNo
	 * @Description 
	 * @return
	 */
	public static String getItemNo() {
		return getPayConfig().getProperty("item_no");
	}

	/**
	 * 获取支付签名验证地址
	 * @Title getPaySignUrl
	 * @Description 
	 * @return
	 */
	public static String getPaySignUrl() {
		return getPayConfig().getProperty("pay_sign_url");
	}
	
	/**
	 * 获取通知url
	 * @Title getNotifyUrl
	 * @Description 
	 * @return
	 */
	public static String getNotifyUrl() {
		//获取域名
		String domain = Hongbao.getDomain();
		String url = "http://"+domain+getPayConfig().getProperty("notify_url");
		return url;
	}
	
	/**
	 * 获取同步通知url
	 * @Title getSyncNotifyUrl
	 * @Description 
	 * @return
	 */
	public static String getSyncNotifyUrl() {
		//获取域名
		String domain = Hongbao.getDomain();
		String url = "http://"+domain+getPayConfig().getProperty("sync_notify_url");
		return url;
	}
	

	/**
	 * 获取转盘通知url
	 * @Title getNotifyUrl
	 * @Description 
	 * @return
	 */
	public static String getDrawNotifyUrl() {
		//获取域名
		String domain = Hongbao.getDomain();
		String url = "http://"+domain+getPayConfig().getProperty("draw_notify_url");
		return url;
	}
	
	/**
	 * 获取转盘同步通知url
	 * @Title getSyncNotifyUrl
	 * @Description 
	 * @return
	 */
	public static String getDrawSyncNotifyUrl() {
		//获取域名
		String domain = Hongbao.getDomain();
		String url = "http://"+domain+getPayConfig().getProperty("draw_sync_notify_url");
		return url;
	}
	
	/**
	 * 创建红包支付json
	 * @Title createHongbaoPayJson
	 * @Description 
	 * @param orderNo
	 * @param itemName
	 * @param amount
	 * @return
	 */
	public static JSONObject createHongbaoPayJson(String orderNo,String itemName,BigDecimal amount) {
        return createPayJson(orderNo, itemName, amount, getNotifyUrl(), getSyncNotifyUrl());
	}
	
	/**
	 * 创建红包转盘支付json
	 * @Title createHongbaoDrawPayJson
	 * @Description 
	 * @param orderNo
	 * @param itemName
	 * @param amount
	 * @return
	 */
	public static JSONObject createHongbaoDrawPayJson(String orderNo,String itemName,BigDecimal amount) {
        return createPayJson(orderNo, itemName, amount, getDrawNotifyUrl(), getDrawSyncNotifyUrl());
	}
	
	/**
	 * 创建支付json
	 * @Title createPayJson
	 * @Description 
	 * @param orderNo
	 * @param itemName
	 * @param amount
	 * @param notifyUrl
	 * @param syncNotifyUrl
	 * @return
	 */
	public static JSONObject createPayJson(String orderNo,String itemName,BigDecimal amount,String notifyUrl,String syncNotifyUrl) {
		JSONObject pay = new JSONObject();
		//商户订单编号
        pay.put("o_bizcode", orderNo);
        //平台生成唯一应用Key
        pay.put("o_appkey", PayDunxingUtil.getAppKey());
        //终端唯一标示（IP地址，MD5加密）
        pay.put("o_term_key", Md5Utils.encryPassword(IpUtils.getLocalIP()));
        //异步通知地址（不传就已后台配置为准）
        pay.put("o_address", notifyUrl);
        //H5同步通知地址（不传就已后台配置为准）
        pay.put("o_showaddress", syncNotifyUrl);
        //支付类型(1:支付宝，2：微信，3：银联，4：微信公众号，5：微信APP)，H5收银台模式以商户后台支付配置为准。
        pay.put("o_paymode_id", "4");
        //商品id（后台自动生成）
        pay.put("o_goods_id", getItemNo());
        //商品名称（不传就已后台配置为准）
        pay.put("o_goods_name", itemName);
        //货币类型以及单位：RMB – 人民币（单位：元）
        pay.put("o_price", amount.toString());
        //pay.put("o_price", "0.01");
        //商户私有信息,放置需要回传的信息(utf-8编码格式，用户自定义信息)
        pay.put("o_privateinfo", "");
        
        return pay;
	}
	
	/**
	 * 验证签名
	 * @Title validateSign
	 * @Description 
	 * @param trade_no
	 * @param trade_sign
	 * @return
	 * @throws HongbaoException
	 */
	public static String validateSign(String trade_no,String trade_sign) throws HongbaoException {
		String result = "";
		try {
			//获取签名验证地址
			String signUrl = getPaySignUrl();
			//设置参数
			HashMap<String, String> params = new HashMap<>();
			params.put("trade_no", trade_no);
			params.put("trade_sign", trade_sign);
			String appsign = getAppSecret();
			params.put("app_sign", appsign);
			result = HttpClientUtil2.getData(signUrl, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		String result = validateSign("hongbao-order-10000161", "C93790BC1537599B48D8F40AEB07417D143E0DFD72A0134D6FB0CC5F3823C8CA5B4410EE774B9BFA70FF85E4A9C3757704C0DB9E9FF499F17A024C6E0FACA87EDA21A9E2517E5FE7849D0274D15456BD646CCDC31E6EF344E749135081B5348BDEF6AA996B53FFD760FA954D53405DC0F55674D5A386733E979554807334D01183B55D5168B4AF4F848E1F6D9F6B6A068F0E8FD1349AC1F3E0B0351EF7CF1C3DDD57A2C7975B2FAB3553322E2CA2523207ED22CD605ECAAE356B2ABDFFA26D57");
		System.out.println(result);
	}
}

