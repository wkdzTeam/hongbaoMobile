package com.hongbao.mobile.modules.pay.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hongbao.mobile.common.config.Hongbao;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.utils.DateUtils;
import com.hongbao.mobile.modules.pay.entity.PayConfig;
import com.hongbao.mobile.modules.pay.service.PayInfoService;

/**
 * 环迅支付
 * @ClassName PayHuanxunUtil
 * @Description 
 */
public class PayHuanxunUtil {
	
	public static Logger logger = LoggerFactory.getLogger(PayInfoService.class);
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat reqSdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 获取充值支付信息
	 * @Title getRechargePayData
	 * @Description 
	 * @param payConfig
	 * @param merBillno
	 * @param goodsName
	 * @param ordAmt
	 * @param createDate
	 * @param callbackParam
	 * @return
	 * @throws HongbaoException
	 */
	public static String getRechargePayData(PayConfig payConfig,String merBillno,String goodsName,BigDecimal ordAmt,Long createDate,String callbackParam) throws HongbaoException {
		return getHuanxunPayData(payConfig, merBillno, goodsName, ordAmt, createDate, getDomainUrl(payConfig.getDrawAsyncNotifyUrl()), getDomainUrl(payConfig.getDrawSyncNotifyUrl()), callbackParam);
	}
	
	/**
	 * 获取环迅支付信息
	 * @Title getHuanxunPayData
	 * @Description 
	 * @param payConfig
	 * @param merBillno
	 * @param goodsName
	 * @param ordAmt
	 * @param createDate
	 * @param notify_url
	 * @param callback_url
	 * @param callbackParam
	 * @return
	 * @throws HongbaoException
	 */
	public static String getHuanxunPayData(PayConfig payConfig,String merBillno,String goodsName,BigDecimal ordAmt,Long createDate,String notify_url,String callback_url,String callbackParam) throws HongbaoException {
		StringBuilder xml = new StringBuilder();
		Date now = new Date();
		try {
			Long timeout = new Long(Hongbao.getConfig("order.timeout"));
			
			String account = payConfig.getItemNo();
			String merCode = payConfig.getMchId();
			//获取md5密钥
			String md5Key = payConfig.getKey();
			
			StringBuilder bodyXml = new StringBuilder();
			bodyXml.append("<body>")
			.append("<MerBillno>").append(merBillno).append("</MerBillno>")
			.append("<GoodsInfo>")
			.append("<GoodsName>").append(goodsName).append("</GoodsName>")
			.append("<GoodsCount>").append(1).append("</GoodsCount>")
			.append("</GoodsInfo>")
			.append("<OrdAmt>").append(ordAmt.toString()).append("</OrdAmt>")
			.append("<OrdTime>").append(DateUtils.parseTimestamp(createDate, "yyyy-MM-dd HH:mm:ss")).append("</OrdTime>")
			.append("<MerchantUrl>").append(callback_url).append("</MerchantUrl>")
			.append("<ServerUrl>").append(notify_url).append("</ServerUrl>")
			.append("<BillEXP>").append(DateUtils.parseTimestamp(createDate+(timeout*1000), "yyyy-MM-dd HH:mm:ss")).append("</BillEXP>")
			.append("<ReachBy>").append("阿拉灯神钉").append("</ReachBy>")
			.append("<ReachAddress>").append("北京市朝阳区").append("</ReachAddress>")
			.append("<CurrencyType>").append("156").append("</CurrencyType>")
			.append("<Attach>").append(merBillno).append("</Attach>")
			.append("<RetEncodeType>").append("17").append("</RetEncodeType>")
			.append("</body>");
			
			//商户证书
			String signature = DigestUtils.md5Hex((bodyXml.toString() + merCode + md5Key).getBytes("UTF-8"));
			
			xml.append("<Ips>")
				.append("<WxPayReq>")
				.append("<head>")
				.append("<Version>v1.0.0</Version>")
				.append("<MerCode>").append(merCode).append("</MerCode>")
				.append("<MerName>").append("郑州恒世安网络科技有限公司").append("</MerName>")
				.append("<Account>").append(account).append("</Account>")
				.append("<MsgId></MsgId>")
				.append("<ReqDate>"+reqSdf.format(now)+"</ReqDate>")
				.append("<Signature>").append(signature).append("</Signature>")
				.append("</head>")
				.append(bodyXml.toString())
				.append("</WxPayReq>")
				.append("</Ips>");
			
		} catch (Exception e) {
			e.printStackTrace();

			throw new HongbaoException(ResultCodeConstants.C1005);
		}
		
        return xml.toString();
	}
	
	/**
	 * 获取域名url
	 * @Title getDomainUrl
	 * @Description 
	 * @param url
	 * @return
	 */
	public static String getDomainUrl(String url) {
		//获取域名
		String domain = Hongbao.getDomain();
		String domainUrl = "http://"+domain+url;
		return domainUrl;
	}
	
}
