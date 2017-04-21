package com.hongbao.mobile.modules.weixin.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.utils.IdGen;
import com.hongbao.mobile.common.utils.PropertiesLoader;
import com.hongbao.mobile.common.utils.StringUtils;
import com.hongbao.mobile.modules.weixin.exception.WeixinException;
import com.hongbao.mobile.modules.weixin.service.WeixinService;

/**
 * 微信工具类
 * @ClassName WeixinUtil
 * @Description 
 */
public class WeixinUtil {
	/**
	 * 日志
	 */
	public static Logger logger = LoggerFactory.getLogger(WeixinService.class);
	
	/**
	 * 连接超时时间，30s
	 */
	private static String CLIENT_TIMEOUT = "30000";
	
	/**
	 * 配置文件
	 */
	private static PropertiesLoader weixinConfig = null;
	
	/**
	 * 微信接口凭证的Json信息
	 */
	private static JSONObject accessTokenJobj = null;
	
	/**
	 * 获取微信接口凭证
	 * @Title getAccessToken
	 * @Description 
	 * @return
	 */
	public static String getAccessToken() throws WeixinException {
		String accessToken = "";
		try {
			Long now = System.currentTimeMillis();
			//判断是否存在微信接口凭证信息
			if(accessTokenJobj!=null) {
				//获取超时时间
				Long expiresTime = accessTokenJobj.getLong("expires_time");
				//判断是否超时
				if(now<expiresTime) {
					accessToken = accessTokenJobj.getString("access_token");
					return accessToken;
				}
				logger.info("【"+accessTokenJobj.getString("access_token")+"】已过期，过期时间【"+accessTokenJobj.getString("expires_time")+"】，当前时间【"+now+"】，重新获取微信凭证");
			}
			//获取应用ID
			String AppID = getAppID();
			//获取应用密钥
			String AppSecret = getAppSecret();
			//获取微信接口凭证获取url
			String url = getAccessTokenUrl()+"&appid="+ AppID + "&secret=" + AppSecret;

			//必须是get方式请求
			accessTokenJobj = clientGet(url);
			
			//获取接口请求凭证
			accessToken = accessTokenJobj.getString("access_token");
			//获取凭证有效时间，单位：秒
			int expiresIn = accessTokenJobj.getInt("expires_in");
			//设置失效时间
			accessTokenJobj.put("expires_time", System.currentTimeMillis()+((expiresIn-200)*1000));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new WeixinException("系统异常，异常原因："+e.getMessage());
		}
		return accessToken;
	}
	
	/**
	 * 获取令牌
	 * @Title getToken
	 * @Description 
	 * @return
	 */
	public static String getToken() {
		return getWeixinConfig().getProperty("Token");
	}
	
	/**
	 * 获得中奖推送模板
	 */
	public static String getPrizeTempletId() {
		return getWeixinConfig().getProperty("prizeTempletId");
	}
	
	/**
	 * 获取消息加解密密钥
	 * @Title getEncodingAESKey
	 * @Description 
	 * @return
	 */
	public static String getEncodingAESKey() {
		return getWeixinConfig().getProperty("EncodingAESKey");
	}
	
	/**
	 * 获取应用ID
	 * @Title getAppID
	 * @Description 
	 * @return
	 */
	public static String getAppID() {
		return getWeixinConfig().getProperty("AppID");
	}
	
	/**
	 * 获取应用密钥
	 * @Title getAppSecret
	 * @Description 
	 * @return
	 */
	public static String getAppSecret() {
		return getWeixinConfig().getProperty("AppSecret");
	}
	
	/**
	 * 获取微信接口凭证获取url
	 * @Title getAppSecret
	 * @Description 
	 * @return
	 */
	public static String getAccessTokenUrl() {
		return getWeixinConfig().getProperty("AccessTokenUrl");
	}

	/**
	 * 获取创建菜单url
	 * @Title getCreateMenuUrl
	 * @Description 
	 * @return
	 */
	public static String getCreateMenuUrl() {
		return getWeixinConfig().getProperty("CreateMenuUrl");
	}
	
	/**
	 * 获取查询菜单url
	 * @Title getGetMenuUrl
	 * @Description 
	 * @return
	 */
	public static String getGetMenuUrl() {
		return getWeixinConfig().getProperty("GetMenuUrl");
	}
	
	/**
	 * 获取删除菜单url
	 * @Title getDeleteMenuUrl
	 * @Description 
	 * @return
	 */
	public static String getDeleteMenuUrl() {
		return getWeixinConfig().getProperty("DeleteMenuUrl");
	}
	
	/**
	 * 获取推送模板消息url
	 * @Title getSendTempletMsgUrl
	 * @Description 
	 */
	public static String getSendTempletMsgUrl() {
		return getWeixinConfig().getProperty("SendTempletMsgUrl");
	}
	
	/**
	 * 获取商户号
	 * @Title getMchid
	 * @Description 
	 * @date 2016年11月16日 上午11:12:25
	 * @return
	 */
	public static String getMchid() {
		return getWeixinConfig().getProperty("Mchid");
	}
	
	/**
	 * 获取商户号key
	 * @Title getMchkey
	 * @Description 
	 * @return
	 */
	public static String getMchkey() {
		return getWeixinConfig().getProperty("Mchkey");
	}
	
	/**
	 * 获取证书路径
	 * @Title getCertPath
	 * @Description 
	 * @return
	 */
	public static String getCertPath() {
		return getWeixinConfig().getProperty("CertPath");
	}
	
	/**
	 * Post请求
	 * @Title clientPost
	 * @Description 
	 * @param urlStr
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static JSONObject clientPost(String urlStr,String param) throws WeixinException {
		JSONObject jobj = new JSONObject();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", CLIENT_TIMEOUT);// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", CLIENT_TIMEOUT); // 读取超时30秒

			http.connect();
			OutputStream os = http.getOutputStream();
			System.out.println("微信传递参数："+param);
			os.write(param.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			logger.info("post请求返回信息："+message);

			//判断信息是否为空
			if(StringUtils.isBlank(message)) {
				String errorlog = "没有获取到请求返回信息";
				logger.error(errorlog);
				throw new WeixinException(errorlog);
			}
			
			//获取json对象
			jobj = JSONObject.fromObject(message);
			//判断是否失败
			if(jobj.containsKey("errcode")) {
				String errcode = jobj.getString("errcode");
				if(!errcode.equals("0")) {
					String errmsg = jobj.getString("errmsg");
					String errorlog = "接口调用失败，失败原因："+errmsg+"，errcode："+errcode;
					logger.error(errorlog);
					throw new WeixinException(errorlog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorlog = "接口调用失败，系统异常，异常原因："+e.getMessage();
			logger.error(errorlog);
			throw new WeixinException(errorlog);
		}
		

        return jobj;
	}
	
	/**
	 * GET请求
	 * @Title clientGet
	 * @Description 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static JSONObject clientGet(String urlStr) throws WeixinException {
		JSONObject jobj = new JSONObject();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", CLIENT_TIMEOUT);// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", CLIENT_TIMEOUT); // 读取超时30秒

			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			logger.info("get请求返回信息："+message);

			//判断信息是否为空
			if(StringUtils.isBlank(message)) {
				String errorlog = "没有获取到请求返回信息";
				logger.error(errorlog);
				throw new WeixinException(errorlog);
			}
			
			//获取json对象
			jobj = JSONObject.fromObject(message);
			//判断是否失败
			if(jobj.containsKey("errcode")) {
				String errcode = jobj.getString("errcode");
				if(!errcode.equals("0")) {
					String errmsg = jobj.getString("errmsg");
					String errorlog = "接口调用失败，失败原因："+errmsg+"，errcode："+errcode;
					logger.error(errorlog);
					throw new WeixinException(errorlog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorlog = "接口调用失败，系统异常，异常原因："+e.getMessage();
			logger.error(errorlog);
			throw new WeixinException(errorlog);
		}

        return jobj;
	}
	
	/**
	 * 获取微信配置文件
	 * @Title getWeixinConfig
	 * @Description 
	 * @return
	 */
	public static PropertiesLoader getWeixinConfig() {
		//非空验证
		if(weixinConfig==null) {
			//获取配置文件
			weixinConfig = new PropertiesLoader("/properties/weixinConfig.properties");
		}
		return weixinConfig;
	}
	
	/**
	 * 判断是否是微信浏览器
	 * @Title isWeixin
	 * @Description 
	 * @param request
	 * @return
	 */
	public static boolean isWeixin(HttpServletRequest request) {
		boolean weixinFlag = false;
		if(request!=null) {
			String userAgent = request.getHeader("user-agent").toLowerCase();
			//是微信浏览器
			if (userAgent.indexOf("micromessenger") > 0) {
				weixinFlag = true;
			}
		}
		return weixinFlag;
	}
	
	/**
	 * 获取Oauth授权令牌 
	 * @Title getOauthAccessToken
	 * @Description 
	 * @param code
	 * @return
	 */
    public static JSONObject getOauthAccessToken(String code) {  
        return getOauthAccessToken(code, getAppID(), getAppSecret());
    }
    
    /**
     * 获取Oauth授权令牌 
     * @Title getOauthAccessToken
     * @Description 
     * @param code
     * @param appid
     * @param secret
     * @return
     */
    public static JSONObject getOauthAccessToken(String code,String appid,String secret) {  
        StringBuilder url = new StringBuilder();  
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");  
        url.append("appid=" + appid);  
        url.append("&secret=").append(secret);  
        url.append("&code=").append(code);  
        url.append("&grant_type=authorization_code");  
        return clientGet(url.toString());  
    }

	/**
	 * 获取Oauth用户信息
	 * @Title getUserInfo
	 * @Description 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static JSONObject getUserInfo(String accessToken, String openid) {
		StringBuilder url = new StringBuilder();
		url.append("https://api.weixin.qq.com/sns/userinfo?");
		url.append("access_token=" + accessToken);
		url.append("&openid=").append(openid);
		url.append("&lang=zh_CN");
		return clientGet(url.toString());
	}

	/**
	 * 获取微信的配置信息
	 * @Title getWxConfig
	 * @Description 
	 * @param request
	 * @return
	 */
	public static HashMap<String, String> getWxConfig(HttpServletRequest request) {
		HashMap<String, String> ret = new HashMap<String, String>();

		String appId = getAppID(); // 必填，公众号的唯一标识

		String requestUrl = request.getRequestURL()+(StringUtils.isNotBlank(request.getQueryString())?"?"+request.getQueryString():"");
		System.out.println(requestUrl);
		String access_token = "";
		String jsapi_ticket = "";
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
		String nonceStr = IdGen.uuid(); // 必填，生成签名的随机串
		
		access_token = getAccessToken();

		if (access_token != null) {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
			JSONObject json = clientGet(url);
			if (json != null) {
				jsapi_ticket = json.getString("ticket");
			}
		}
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sign.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("appId", appId);
		ret.put("timestamp", timestamp);
		ret.put("nonceStr", nonceStr);
		ret.put("signature", signature);
		return ret;
	}

	/**
	 * 字符串加密辅助方法 
	 * @Title byteToHex
	 * @Description 
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;

	}
	
	/**
	 * 获取域名
	 * @Title getDomain
	 * @Description 
	 * @return
	 */
	public static String getDomain() {
		return getDomainPrefix()+"."+getDomainSuffix();
	}
	
	/**
	 * 获取域名前缀
	 * @Title getDomainPrefix
	 * @Description 
	 * @return
	 */
	public static String getDomainPrefix() {
		return getWeixinConfig().getProperty("domain.prefix");
	}
	
	/**
	 * 获取域名后缀
	 * @Title getDomainSuffix
	 * @Description 
	 * @return
	 */
	public static String getDomainSuffix() {
		return getWeixinConfig().getProperty("domain.suffix");
	}
	
}
