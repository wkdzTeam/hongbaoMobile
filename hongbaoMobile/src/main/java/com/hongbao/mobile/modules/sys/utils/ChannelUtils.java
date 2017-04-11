package com.hongbao.mobile.modules.sys.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hongbao.mobile.common.utils.JedisUtils;

public class ChannelUtils {
	
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 添加ip
	 * @param ip
	 * @param channel
	 */
	public static void insertredis(String ip,String channel){
		if(channel == null || channel.length()==0){
			channel = "0";
		}
		ip = ip.replaceAll(":", ".");
		String times = df.format(new Date());
		//redis存储ip地址以及ip地址进来多少次
		String redis_key = JedisUtils.KEY_PREFIX+":"+times+":"+channel+":"+ip;   //日期:渠道:ip
		String nums = JedisUtils.get( redis_key );
		if(nums==null || nums.equals("")){
			JedisUtils.set( redis_key , "1");
			String redis_key_count = "hongbao:"+times+":"+channel+":count";
			String countnums =  JedisUtils.get(redis_key_count);
			if(countnums==null || countnums.equals("")){
				JedisUtils.set(redis_key_count, "1");
			}else{
				int x = Integer.parseInt(countnums)+1;
				JedisUtils.set(redis_key_count, x+"");
			}
		}else{
			int x = Integer.parseInt(nums)+1;
			JedisUtils.set( redis_key , x+"");
		}
	}
}
