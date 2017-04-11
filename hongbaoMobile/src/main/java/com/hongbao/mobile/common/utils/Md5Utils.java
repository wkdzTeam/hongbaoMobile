package com.hongbao.mobile.common.utils;

import java.security.MessageDigest;

import com.hongbao.mobile.common.config.Hongbao;

public class Md5Utils {
	
	/**
	 * 获取小写md5
	 */
	public static String getMD5LowerCase(String str) {
		String origMD5 = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(str.getBytes("UTF-8"));
			origMD5 = byteArray2HexStr(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return origMD5;
	}
	
	/**
	 * 获取大写MD5
	 */
	public static String getMD5UpperCase(String str) {
		return getMD5LowerCase(str).toUpperCase();
	}

	/**
	 * 处理字节数组得到MD5密码的方法
	 */
	private static String byteArray2HexStr(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			sb.append(byte2HexStr(b));
		}
		return sb.toString();
	}

	/**
	 * 字节标准移位转十六进制方法
	 */
	private static String byte2HexStr(byte b) {
		String hexStr = null;
		int n = b;
		if (n < 0) {
			// 若需要自定义加密,请修改这个移位算法即可
			n = b & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr;
	}

	/**
	 * 提供一个MD5多次加密方法
	 */
	public static String getMD5LowerCase(String origString, int times) {
		String md5 = getMD5LowerCase(origString);
		for (int i = 0; i < times - 1; i++) {
			md5 = getMD5LowerCase(md5);
		}
		return getMD5LowerCase(md5);
	}
	
	/**
	 * 加密密码
	 */
	public static String encryPassword(String password) {
		return getMD5LowerCase(password,getMd5Time());
	}
	
	/**
	 * 获取密码md5加密次数
	 */
	public static int getMd5Time() {
		int md5Time = Integer.parseInt(Hongbao.getConfig("login.password.md5.time"));
		return md5Time;
	}

	/**
	 * 密码验证方法
	 */
	public static boolean verifyPassword(String inputStr, String MD5Code) {
		return encryPassword(inputStr).equals(MD5Code);
	}

	/**
	 * 多次加密时的密码验证方法
	 */
	public static boolean verifyPassword(String inputStr, String MD5Code,
			int times) {
		return getMD5LowerCase(inputStr, times).equals(MD5Code);
	}
}
