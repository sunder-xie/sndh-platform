package com.nhry.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nhry.cache.jedis.util.JedisPoolManager;
import com.nhry.common.model.AccessKey;
import com.nhry.domain.TSysUser;
import com.nhry.exception.ExceptionMapperSupport;
import com.nhry.exception.MessageCode;
import com.nhry.utils.Base64Util;
import com.nhry.utils.ObjectSerializeUtil;
import com.nhry.utils.RedisUtil;
import com.nhry.utils.SysContant;

public class UserSessionService {
	private static final Logger LOGGER = Logger.getLogger(UserSessionService.class);
	public static final String accessKey="accessKey";
	public static final String uname="uname";
	private static final ThreadLocal<String> accessKeyThread = new ThreadLocal<String>();
	
	public static String checkIdentity(String accessKey,String uname){
		Map<String, String> accessMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_access_key"));
		String ak = Base64Util.decodeStr(accessKey);
		if(accessMap == null || accessMap.get(ak)==null){
			 //access不存在
			LOGGER.warn("当前访问的aceesskey不存在!");
			 return null;
		}
		//url date ip accessKey
		accessKeyThread.set(ak);
		return MessageCode.NORMAL;
	}
	
	/**
	 * 缓存用户信息
	 * @param uName
	 * @param accessKey
	 * @param user
	 * @param request
	 */
	public static void cacheUserSession(String uName,String accessKey,TSysUser user,HttpServletRequest request){
		//解密之后再放入缓存
		accessKey = Base64Util.decodeStr(accessKey);
		//缓存用户对象
		Map<String, String> usersMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_user_key"));
		if(usersMap == null){
			usersMap = new HashMap<String,String>();
		}
		usersMap.put(uName, ObjectSerializeUtil.getStrFromObj( user));
		RedisUtil.getRu().hmset(SysContant.getSystemConst("app_user_key"), usersMap);
		
		Map<String, String> accessMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_access_key"));
		if(accessMap == null){
			 accessMap = new HashMap<String,String>();
		}
		AccessKey ak = new AccessKey();
		ak.setAccessIp(request.getRemoteHost());
		ak.setVisitFirstTime(new Date());
		ak.setUname(uName);
		ak.setAck(accessKey);
		ak.setVisitCount(1);
		accessMap.put(accessKey, ObjectSerializeUtil.getStrFromObj(ak));
		//缓存accesskey对象
		RedisUtil.getRu().hmset(SysContant.getSystemConst("app_access_key"), accessMap);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static TSysUser getCurrentUser(){
		if(!"product".equals(SysContant.getSystemConst("app_mode"))){
			//测试时使用
			TSysUser user = new TSysUser();
			user.setLoginName("test");
			return user;
		}
		
		String accessKey = accessKeyThread.get();
		Map<String, String> accessMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_access_key"));
		if(accessMap == null || accessMap.get(accessKey)==null){
			 //access不存在
			LOGGER.warn("当前访问的aceesskey不存在!");
			 return null;
		}
		
		AccessKey users = (AccessKey)ObjectSerializeUtil.getObjFromStr(accessMap.get(accessKey));
		if(users == null){
			//反序列化失败
			LOGGER.warn("aceesskey反序列化失败!");
			return null;
		}
	    Map<String, String> usersMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_user_key"));
	    TSysUser user = (TSysUser)ObjectSerializeUtil.getObjFromStr(usersMap.get(users.getUname()));
		return user;
	}
	
	/**
	 * 生成一个key
	 * @return
	 */
	public static String generateKey(){
		return Base64Util.encodeStr(java.util.UUID.randomUUID().toString().replace("-", ""));
	}
}
