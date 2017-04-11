package com.hongbao.mobile.modules.pay.util;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.security.Digests;
import com.hongbao.mobile.common.utils.DateUtils;
import com.hongbao.mobile.common.utils.Encodes;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.entity.PayDataInfo;
import com.hongbao.mobile.modules.pay.service.PayInfoService;

/**
 * 南粤银行支付
 * @ClassName PayNanyueUtil
 * @Description 
 */
public class PayNanyueUtil {
	
	public static Logger logger = LoggerFactory.getLogger(PayInfoService.class);

	/**
	 * 创建威富通转盘红包支付
	 * @Title createNanyueHongbaoDrawPay
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
	public static PayDataInfo createNanyueHongbaoDrawPay(String outTradeNo,String itemName,String body,String description,BigDecimal amount,Long createDate,String callbackParam) throws HongbaoException {
		PayConfig payConfig = PayUtil.getPayConfig();
		return createNanyueHongbaoDrawPay(payConfig,outTradeNo, itemName, body, description, amount, createDate, callbackParam);
	}
	
	/**
	 * 创建威富通转盘红包支付
	 * @Title createNanyueHongbaoDrawPay
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
	public static PayDataInfo createNanyueHongbaoDrawPay(PayConfig payConfig,String outTradeNo,String itemName,String body,String description,BigDecimal amount,Long createDate,String callbackParam) throws HongbaoException {
		return createNanyuePay(payConfig,outTradeNo, itemName, body, description, amount, createDate, payConfig.getPayDrawAsyncNotifyUrl(), payConfig.getPayDrawSyncNotifyUrl(),callbackParam);
	}
	
	/**
	 * 创建威富通支付
	 * @Title createNanyuePay
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
	public static PayDataInfo createNanyuePay(PayConfig payConfig,String outTradeNo,String itemName,String body,String description,BigDecimal amount,Long createDate,String notify_url,String callback_url,String callbackParam) throws HongbaoException {
		PayDataInfo payDataInfo = new PayDataInfo();
		
		String key = payConfig.getKey();
		
		String requestUrl = payConfig.getReqUrl();
		
		HashMap<String, String> paramMap = new HashMap<>();
		
		//商户号
		String mchId = payConfig.getMchId();
		paramMap.put("mchId", mchId);
		//商品描述
		paramMap.put("body", body);
		//商户订单号
		paramMap.put("outTradeNo", payConfig.getOrderPrefix()+outTradeNo);
		//交易金额
		paramMap.put("amount", amount.toString());
		//附加数据
		paramMap.put("description", payConfig.getMchId());
		//货币类型
		paramMap.put("currency", "CNY");
		//订单支付时间
		paramMap.put("timePaid", DateUtils.parseTimestamp(createDate, "yyyyMMddHHmmss"));
		//订单失效时间
		Long timeout = new Long(Hongbao.getConfig("order.timeout"));
		paramMap.put("timeExpire", DateUtils.parseTimestamp(createDate+(timeout*1000), "yyyyMMddHHmmss"));
		//商品的标题
		paramMap.put("subject", itemName);
		//渠道
		paramMap.put("channel", "wxPub");
		//交易类型
		paramMap.put("tradeType", "cs.pay.submit");
		//版本
		paramMap.put("version", "1.0");
		//成功跳转url
        String callbackUrl = callback_url+(StringUtils.isNoneBlank(callbackParam)?"?call_param="+callbackParam:"");
		paramMap.put("callbackUrl", callbackUrl);
		//结果通知url
		paramMap.put("notifyUrl", notify_url);
		//指定支付方式
		paramMap.put("limitPay", "");
		
		//过滤空值或null
		Map<String, String> filterMap = paraFilter(paramMap);
		
		//拼接
		String toSign = createLinkString(filterMap);
		System.out.println(toSign);
		
		//生成签名sign
		String sign = genSign(key, toSign);
		filterMap.put("sign", sign);

		//转为json串
		String postStr = JSONObject.fromObject(filterMap).toString();
		
		//发送请求
		String returnStr = sendRequest(requestUrl,postStr,"application/json");
		
		//解析返回串
		JSONObject returnJson = JSONObject.fromObject(returnStr);
		String returnCode = returnJson.getString("returnCode");
		String resultCode = returnJson.getString("resultCode");
		if(returnCode.equals("0")&&resultCode.equals("0")){
			//验签
			if(!validSign(returnJson, key)) {
				logger.error("签名验证错误");
				throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单失败，请重试");
			}
			String payUrl = returnJson.getString("payCode");
			String outChannelNo = returnJson.getString("outChannelNo");
			
			payDataInfo.setPayUrl(payUrl);
			payDataInfo.setTokenId(outChannelNo);
		} else{
			logger.error("订单失败记录："+returnJson.toString());
			throw new HongbaoException(ResultCodeConstants.C0012,"创建支付订单失败，请重试");
		}

        return payDataInfo;
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
	
	
	/**
	 * 获取域名url
	 * @Title getDomainUrl
	 * @Description 
	 * @param url
	 * @return
	 */
	public static String getUrl(PayConfig payConfig,String url) {
		//获取域名
		String domain = payConfig.getCallbackDomain();
		String domainUrl = "http://"+domain+url;
		return domainUrl;
	}
	
	
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		DecimalFormat formater = new DecimalFormat("###0.00");
		for (String key : sArray.keySet()) {
			String finalValue = null;
			Object value = sArray.get(key);
			if(value instanceof BigDecimal){
				finalValue = formater.format(value);
			}else {
				finalValue = (String) value;
			}
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")) {
				continue;
			}
			result.put(key, finalValue);
		}

		return result;
	}
	
	public static Map<String, String> paraFilterJson(JSONObject jobj) {
		Map<String, String> result = new HashMap<String, String>();

		if (jobj == null || jobj.size() <= 0) {
			return result;
		}
		DecimalFormat formater = new DecimalFormat("###0.00");

		Iterator iterator = jobj.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String finalValue = null;
			Object value = jobj.get(key);
			if(key.equals("amount")){
				finalValue = formater.format(new BigDecimal(value.toString()));
			}else {
				finalValue = value.toString();
			}
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
				continue;
			}
			result.put(key, finalValue);
		}

		return result;
	}

	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static String genSign(String key,String str){
		return md5(str+"&key="+key).toUpperCase();
	}
	
    public static String md5(String plainText) {
        try {
            return Encodes.encodeHex(Digests.md5(new ByteArrayInputStream(plainText.getBytes("utf-8"))));
        } catch (Exception ex) {
            return "";   
        }
    }
    
    public static boolean validSign(JSONObject json,String key){
    	String oldSign = json.getString("sign");
    	String sign = genSign(key, createLinkString(paraFilterJson(json)));
    	return sign.equalsIgnoreCase(oldSign);
    }
    
    public static HttpClient createAuthNonHttpClient() {
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接参数
        ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charset.forName("utf-8")).build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connConfig);
        connManager.setDefaultSocketConfig(socketConfig);
        //指定cookie存储对象
        BasicCookieStore cookieStore = new BasicCookieStore();
        return HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setConnectionManager(connManager).build();
    }

	
    /**
     * 发送json格式请求到指定地址
     * @param url
     * @param json
     * @return
     */
    public static String sendRequest(String url, String json,String contentType) {
        int timeout=5000;                                     //超时时间
        String strResult = "";
        HttpResponse resp = null;
        HttpClient httpClient = createAuthNonHttpClient();//new DefaultHttpClient();
//        wrapClient(httpClient);
        try {
           /* HttpParams httpParams = httpClient.getParams();
            httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
            httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);*/
        	 HttpPost httpPost = new HttpPost(url);
             httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
             HttpEntity postEntity = new StringEntity(json, "utf-8");
             httpPost.setEntity(postEntity);
             resp = httpClient.execute(httpPost);
             HttpEntity entity = resp.getEntity();
             strResult = EntityUtils.toString(entity);
            System.out.println("请求地址: " + httpPost.getURI());
            System.out.println("响应状态: " + resp.getStatusLine());
            System.out.println("响应长度: " + strResult.length());
            System.out.println("响应内容: " + strResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	HttpClientUtils.closeQuietly(resp);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return strResult;
    }

    /**
     * https 不验证证书
     * @param httpClient
     */
    public static void wrapClient(HttpClient httpClient) {
        try {
            X509TrustManager xtm = new X509TrustManager() {   //创建TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext ctx = SSLContext.getInstance("TLS");
            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[]{xtm}, null);
            //创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static class AnyTrustStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }

    }
    
    
    public static void main(String[] args) {
		HashMap<String, String> param = new HashMap<>();
		param.put("appid", "fhsdfh23g2fdsjf4324");
		param.put("mchId", "00000000001");
		param.put("body", "支付");
		param.put("open_id", "123");
		
		String key = "192006250b4c09247ec02edce69f6a2d";
		
		
		String str = createLinkString(param);
		System.out.println(str);
		String sign = genSign(key, str);
		System.out.println(sign);
		
	}
}
