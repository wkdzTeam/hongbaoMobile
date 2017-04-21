package com.hongbao.mobile.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Http接口调用工具类
 * <p/>
 * 调用例子：param, ApiRe
 * String url = "http://www.xxx.ddd/api?param=data";
 * HttpClientParameter param = new HttpClientParameter(url,"GBK")t(sultDTO.class);
 * <p/>
 *
 * ApiResultDTO apiRst = httpClientUtil.ge
 */
@Service
public class HttpClientUtil2 {

    /**
     * 日志记录类
     */
    private final static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private final static String Log_Prefix = "HTTP_CLIENT_CALL_API_";
    
	/**
	 * 默认超时时间20s
	 */
	private static int timeOut = 20 * 1000;

    /**
     * 检查参数并设置默认值
     *
     * @param param
     * @param isCheckData
     */
    /*private void setDefaultValueAndCheckParam(HttpClientParameter param, boolean isCheckData) {

        Assert.notNull(param, HttpClientParameter.class.getName() + ":param is empty!");

        Assert.notNull(param.getUrl(), HttpClientParameter.class.getName() + ":url is empty!");

        if (isCheckData) {
            Assert.notNull(param.getPostData(), HttpClientParameter.class.getName() + ":postData is empty!");
        }

        if (param.getUrlEncodeChartSet() == null || param.getUrlEncodeChartSet().trim().equals("")) {
            param.setUrlEncodeChartSet("UTF-8");
        }

        if (param.getRequestContentChartSet() == null || param.getRequestContentChartSet().trim().equals("")) {
            param.setRequestContentChartSet("UTF-8");
        }

        if (param.getContentType() == null || param.getContentType().trim().equals("")) {
            param.setContentType("application/json");
        }

        //设置对返回结果解码方式
        if (param.getResponseDecoderChartSet() == null || param.getResponseDecoderChartSet().trim().equals("")) {
            param.setResponseDecoderChartSet(null);
        }

        //设置是否对返回结果打日志的标志
        if (param.getIsLogRtnData() == null) {
            if (param.getIsLogRtnData() == null) {
                param.setIsLogRtnData(false);
            }
        }
    }*/


    /**
     * rest接口 post方式调用
     *
     * @param param   httpClient调用参数
     * @param rtnType 返回类型
     * @return
     */
    /*public <T> T post(HttpClientParameter param, Class<T> rtnType) {
        T rtnRst = null;
        String apiRstStr = "";

        //设置默认值
        setDefaultValueAndCheckParam(param, true);

        String apiUrl = param.getUrl();
        PostMethod post = new PostMethod(apiUrl);
        try {
            String jsonStrData = (param.getPostData() instanceof String) ? (String) param.getPostData() : JsonUtil.toJson(param.getPostData());
            RequestEntity requestEntity = new StringRequestEntity(jsonStrData, param.getContentType(), param.getRequestContentChartSet());
            post.setRequestEntity(requestEntity);
            post.addRequestHeader("Content-Type", param.getContentType());
            post.getParams().setContentCharset(param.getRequestContentChartSet());

            HttpClient httpclient = new HttpClient();
            int result = httpclient.executeMethod(post);
            if (result != 200) {
                log.error(Log_Prefix + "ERR|CODE:" + result + " URL:" + apiUrl);
                return rtnRst;
            }
            apiRstStr = post.getResponseBodyAsString();
            if (param.getResponseDecoderChartSet() != null) {
                apiRstStr = URLDecoder.decode(apiRstStr, param.getResponseDecoderChartSet());
            }
            if (param.getIsLogRtnData()) {
                log.error(Log_Prefix + "TIP|URL:" + apiUrl + " APIRST:" + apiRstStr);
            }
            rtnRst = JsonUtil.fromJson(apiRstStr, rtnType);
        } catch (Exception ex) {
            log.error(Log_Prefix + "EX|MSG:" + ex.getLocalizedMessage() + " URL:" + apiUrl + " APIRST:" + apiRstStr, ex);
        } finally {
            post.releaseConnection();
        }
        return rtnRst;
    }*/

    /**
     * rest接口 get方式调用
     *
     * @param param   httpClient调用参数
     * @param rtnType 返回类型
     * @return
     */
    /*public <T> T get(HttpClientParameter param, Class<T> rtnType) {
        T rtnRst = null;
        String apiRstStr = "";
        //设置默认值
        setDefaultValueAndCheckParam(param, false);

        String apiUrl = param.getUrl();
        GetMethod get = new GetMethod(apiUrl);
        get.addRequestHeader("Content-Type", param.getContentType());
        get.getParams().setContentCharset(param.getRequestContentChartSet());
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(get);
            if (result != 200) {
                log.error(Log_Prefix + "ERR|CODE:" + result + " URL:" + apiUrl);
                return rtnRst;
            }
            apiRstStr = get.getResponseBodyAsString();
            if (log.isInfoEnabled())
                log.info("request url is before " + apiUrl + "url result " + apiRstStr);
            if (param.getResponseDecoderChartSet() != null) {
                apiRstStr = URLDecoder.decode(apiRstStr, param.getResponseDecoderChartSet());
            }
            if (log.isInfoEnabled())
                log.info("request url is " + apiUrl + "url result " + apiRstStr);
            if (param.getIsLogRtnData()) {
                log.error(Log_Prefix + "TIP|URL:" + apiUrl + " APIRST:" + apiRstStr);
            }
            rtnRst = JsonUtil.fromJson(apiRstStr, rtnType);
        } catch (Exception ex) {
            log.error(Log_Prefix + "EX|MSG:" + ex.getLocalizedMessage() + " URL:" + apiUrl + " APIRST:" + apiRstStr, ex);
            throw new RuntimeException("http invoke error!  " + ex.getMessage());
        } finally {
            get.releaseConnection();
        }
        return rtnRst;
    }*/


	public static String getData(String url,Map<String,String> params,int timeOut){
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);

		String resultUrl = null;

		try {
			resultUrl = assembleUrl(url, params);
		} catch (UnsupportedEncodingException uee) {
			log.error("unsupported encoding exception",uee);
		}

		GetMethod getMethod = new GetMethod(resultUrl);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
		//请求重试处理，使用默认的，重试3次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		try{
			int statusCode = client.executeMethod(getMethod);
			if(statusCode != HttpStatus.SC_OK){
				log.info("excute get method for url {} failure with HTTP status code {} ",new Object[]{resultUrl, getMethod.getStatusLine()});
				return getMethod.getStatusLine()+"";
			}
			return getMethod.getResponseBodyAsString();
		}catch (IOException e) {
			log.error("occoured i/o exception when excute HTTP get method",e);
		}finally{
			getMethod.releaseConnection();
		}
		return null;
	}


	public static String getData(String url,int timeOut){
		return getData(url,null,timeOut);
	}

	public static String getData(String url){
		return getData(url,null,timeOut);
	}
	public static String getData(String url,Map<String,String> params){
		return getData(url,params,timeOut);
	}
	public static String postData(String url,Map<String,String> params,int timeOut){
		Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher matcher = p.matcher(url);
		if(!matcher.matches()){
			throw new IllegalArgumentException("illegal argument with url " + url);
		}

		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);

		PostMethod postMethod = new PostMethod(url);
		if(params != null && params.size()>0){
			List<NameValuePair> parametersBody = new ArrayList<NameValuePair>();
			Iterator<String> iterator = params.keySet().iterator();
			try{
				while(iterator.hasNext()){
					String param = iterator.next();
					String value = params.get(param);
					if(checkContainChinese(value)){
						parametersBody.add(new NameValuePair(param,URLEncoder.encode(value,"UTF-8")));
					}else{
						parametersBody.add(new NameValuePair(param,value));
					}
				}
			}catch(UnsupportedEncodingException uee){
				log.error("unsupported encoding exception",uee);
			}

			postMethod.setRequestBody(parametersBody.toArray(new NameValuePair[parametersBody.size()]));
		}

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeOut);
		//请求重试处理，使用默认的，重试3次
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		int statusCode = 0;
		try{
			statusCode = client.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK){
				log.info("execute post method for url {} failure with HTTP status code {} ",new Object[]{url, postMethod.getStatusLine()});
				return postMethod.getStatusLine()+"";
			}
			InputStream inputStream = postMethod.getResponseBodyAsStream();  
			//BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));  
//			return postMethod.getResponseBodyAsString();
			return StreamUtils.InputStreamTOString(inputStream);
			
		
		}catch (IOException e) {
			log.error("occoured i/o exception when excute HTTP post method",e);
			throw new RuntimeException("execute http occured exception");
		}finally{
			postMethod.releaseConnection();
		}
	}


	public static String postData(String url,int timeOut){
		return postData(url,null,timeOut);
	}

	public static String postData(String url){
		return postData(url,null,timeOut);
	}

	public static String assembleUrl(String url,Map<String,String> params) throws UnsupportedEncodingException{

		if(params==null){
			return url;
		}
		Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher matcher = p.matcher(url);
		if(!matcher.matches()){
			throw new IllegalArgumentException("illegal argument with url " + url);
		}
		StringBuilder sb = new StringBuilder(url);
		if(!url.contains("?")){
			sb.append("?");
		}else{
			if(!url.endsWith("?")){
				sb.append("&");
			}
		}
		Iterator<String> iterator = params.keySet().iterator();
		while(iterator.hasNext()){
			String param = iterator.next();
			String value = params.get(param);
			sb.append(param);
			sb.append("=");
			//中文参数编码
			if(checkContainChinese(value)){
				sb.append(URLEncoder.encode(value, "UTF-8"));
			}else{
				sb.append(value);
			}
			if(iterator.hasNext()){
				sb.append("&");
			}
		}
		return sb.toString();
	}


	private static boolean checkContainChinese(String s){
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
 
    
}
