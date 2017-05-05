package com.hongbao.mobile.common.config;

import java.nio.charset.Charset;
import java.util.Map;

import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.utils.PropertiesLoader;
import com.hongbao.mobile.common.utils.StringUtils;
import com.google.common.collect.Maps;

/**
 * 红包全局配置类
 * @ClassName: Hongbao   
 * @Description: TODO  
 * @date:2015年12月15日 上午12:49:21
 */
public class Hongbao {
		
	/**
	 * 名称
	 */
	public static final String NAME = "store"; //zhuanpan

	/**
	 * UTF-8编码
	 */
    public static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * 当前对象实例
	 */
	private static Hongbao hongbao = new Hongbao();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("/properties/hongbao.properties");

	/**
	 * 获取当前对象实例
	 */
	public static Hongbao getInstance() {
		return hongbao;
	}
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取域名
	 * @Title getDomain
	 * @Description 
	 * @date 2016年5月30日 下午11:09:17
	 * @return
	 */
	public static String getDomain() {
//		return getDomainPrefix()+"."+getDomainSuffix();
		return "www.zelepa.cn/hongbao";
	}
	
	/**
	 * 获取域名前缀
	 * @Title getDomainPrefix
	 * @Description 
	 * @date 2016年11月25日 上午12:58:17
	 * @return
	 */
	public static String getDomainPrefix() {
		return JedisUtils.get(JedisUtils.KEY_PREFIX+":domain.prefix");
	}
	
	/**
	 * 获取域名后缀
	 * @Title getDomainSuffix
	 * @Description 
	 * @date 2016年11月25日 上午12:58:25
	 * @return
	 */
	public static String getDomainSuffix() {
		return JedisUtils.get(JedisUtils.KEY_PREFIX+":domain.suffix");
	}
}
