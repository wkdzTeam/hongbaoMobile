package com.hongbao.mobile.modules.sys.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 系统启动初始化加载方法
 * @ClassName InitDataListener
 * @Description 
 */
//参考页面：http://www.cnblogs.com/rollenholt/p/3612440.html
public class InitDataListener implements ApplicationListener<ContextRefreshedEvent>{
	//日志
	public static Logger logger = LoggerFactory.getLogger(InitDataListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//过滤spring mvc容器启动
		if(event.getApplicationContext().getParent() == null){
			
		}
	}
}
