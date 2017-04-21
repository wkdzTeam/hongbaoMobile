package com.hongbao.mobile.modules.sys.utils;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.hongbao.mobile.common.security.Cryptos;
import com.hongbao.mobile.common.security.Digests;
import com.hongbao.mobile.common.utils.Encodes;
import com.hongbao.mobile.common.utils.JedisUtils;

/**
 * 权限工具类
 * @ClassName: PermissionsUtils   
 * @Description: TODO  
 */
public class PermissionsUtils {
	
	/**
     * HMAC算法
     */
    private static final String HMAC_ALGORITHM = "SHA-1";
	
	
    /**
     * 制作签名
     * @Title makeSignature
     * @Description 
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public static String makeSignature(String timestamp, String nonce,String token){
        String[] arr = new String[]{token, timestamp, nonce};
        // 将 token, timestamp, nonce 三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance(HMAC_ALGORITHM);
            // 将三个参数字符串拼接成一个字符串进行 shal 加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        content = null;
        return tmpStr;
    }
     
    /**
     * 将字节数组转换为十六进制字符串
     * @Title byteToStr
     * @Description 
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest) {
        // TODO Auto-generated method stub
        String strDigest = "";
        for(int i = 0; i < digest.length; i++){
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }
    
    /**
     * 将字节转换为十六进制字符串
     * @Title byteToHexStr
     * @Description 
     * @param b
     * @return
     */
    private static String byteToHexStr(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    
    /**
     * 生成用户id key
     * @Title makeUserIdKey
     * @Description 
     * @param userId
     */
    public static String makeUserIdKey(String userId,String nonce) {
    	String userIdKey = "";
    	String keyParam = userId+nonce;
    	try {
        	userIdKey = Cryptos.aesEncrypt(keyParam, Encodes.encodeHex(Digests.md5(new ByteArrayInputStream(JedisUtils.KEY_PREFIX.getBytes("utf-8")))));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return userIdKey;
    }
    
    /**
     * 验证用户id key
     * @Title validateUserIdKey
     * @Description 
     * @param userId
     * @param key
     * @return
     */
    public static boolean validateUserIdKey(String userId,String key,String nonce) {
    	boolean flag = false;
    	
    	//判断时间是否合法
    	Long now = System.currentTimeMillis();
    	Long time = new Long(nonce);
    	if((now-time)/1000<60) {
        	if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(key)) {
        		String userIdKey = makeUserIdKey(userId,nonce);
        		if(!userIdKey.equals("")) {
            		flag = userIdKey.equals(key);
        		}
        	}
    	}
    	
    	return flag;
    }
}
