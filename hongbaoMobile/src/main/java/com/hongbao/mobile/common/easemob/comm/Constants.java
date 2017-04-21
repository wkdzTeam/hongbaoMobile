package com.hongbao.mobile.common.easemob.comm;

import com.hongbao.mobile.common.utils.PropertiesLoader;

/**
 * 常量类
 * @ClassName: Constants   
 * @Description: TODO  
 */
public class Constants {
	
	public static PropertiesLoader loader = new PropertiesLoader("/properties/easemobConfig.properties");

	// API_HTTP_SCHEMA
	public static final String API_HTTP_SCHEMA = "https";
	// API_SERVER_HOST
	public static final String API_SERVER_HOST = loader.getProperty("API_SERVER_HOST");
	// APPKEY
	public static final String APPKEY = loader.getProperty("APPKEY");
	// APP_CLIENT_ID
	public static final String APP_CLIENT_ID = loader.getProperty("APP_CLIENT_ID");
	// APP_CLIENT_SECRET
	public static final String APP_CLIENT_SECRET = loader.getProperty("APP_CLIENT_SECRET");
    // ORG_ADMIN_USERNAME
    public static final String ORG_ADMIN_USERNAME = loader.getProperty("ORG_ADMIN_USERNAME");
    // ORG_ADMIN_PASSWORD
    public static final String ORG_ADMIN_PASSWORD = loader.getProperty("ORG_ADMIN_PASSWORD");
	// DEFAULT_PASSWORD
	public static final String DEFAULT_PASSWORD = "123456";
}
