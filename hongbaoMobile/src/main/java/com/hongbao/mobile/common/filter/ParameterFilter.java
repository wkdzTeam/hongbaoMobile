package com.hongbao.mobile.common.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hongbao.mobile.common.utils.StringUtils;
import com.hongbao.mobile.common.wrapper.ParameterRequestWrapper;

/**
 * 参数过滤器
 * @ClassName: ParameterFilter   
 * @Description: TODO  
 */
public class ParameterFilter implements Filter {
	
	/**
	 * 编码方式
	 */
    private static final String CHARSET = "UTF-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		//获取request中的参数集合
		Map<String,String[]> tempParams = new HashMap<String,String[]>(request.getParameterMap());
		//创建最终结果集合
		Map<String,String[]> map = new HashMap<String, String[]>();
		//遍历Map
		Iterator<Map.Entry<String, String[]>> entries = tempParams.entrySet().iterator();  
		while (entries.hasNext()) {  
		    Map.Entry<String, String[]> entry = entries.next();
		    //获取当前参数值数组
		    String[] tempValues = entry.getValue();
		    //创建新的参数值数组
		    String[] values = new String[tempValues.length];
		    //遍历参数值数组
		    for (int i=0;i<tempValues.length;i++) {
		    	String value = tempValues[i];
		    	//URLDecoder解码
		    	if(StringUtils.isNotBlank(value)) {
		    		value = URLDecoder.decode(value, CHARSET);
		    	}
		    	//设置新的参数值
		    	values[i] = value;
			}
		    //添加到最终结果结合
		    map.put(entry.getKey(), values);
		}
		//重置request
		request = new ParameterRequestWrapper((HttpServletRequest)request, map);  
		  
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}
}
