package com.hongbao.mobile.modules.sys.listener;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.hongbao.mobile.modules.sys.service.SystemService;

public class WebContextListener extends ContextLoaderListener {
	//日志
	public static Logger logger = LoggerFactory.getLogger(WebContextListener.class);
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
