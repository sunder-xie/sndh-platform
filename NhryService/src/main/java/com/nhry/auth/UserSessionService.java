package com.nhry.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nhry.cache.jedis.util.JedisPoolManager;
import com.nhry.domain.TSysUser;
import com.nhry.exception.MessageCode;
import com.nhry.utils.Base64Util;
import com.nhry.utils.ObjectSerializeUtil;
import com.nhry.utils.RedisUtil;
import com.nhry.utils.SysContant;

public class UserSessionService {
	public static final String accessKey="accessKey";
	public static final String uname="uname";
	private static final ThreadLocal<String> accessKeyThread = new ThreadLocal<String>();
	
	public static String checkIdentity(String accessKey,String uname){
		Map<String,String> userMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_user_key"));
		String userName = userMap.get(accessKey);
		if(StringUtils.isEmpty(userName)){
			return MessageCode.UNAUTHORIZED;
		}
		return MessageCode.NORMAL;
	}
	
	public static void cacheUserSession(String uName,String accessKey,TSysUser user){
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put(uName, ObjectSerializeUtil.getStrFromObj( user));
		userMap.put(accessKey, uName);
		RedisUtil.getRu().hmset(SysContant.getSystemConst("app_user_key"), userMap);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public TSysUser getCurrentUser(){

		return null;
	}
	
	/**
	 * 生成一个key
	 * @return
	 */
	public static String generateKey(){
		return Base64Util.encodeStr(java.util.UUID.randomUUID().toString().replace("-", ""));
	}
}
