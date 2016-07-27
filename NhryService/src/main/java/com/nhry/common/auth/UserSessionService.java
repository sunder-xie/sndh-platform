package com.nhry.common.auth;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.jedis.entity.ObjectRedisTemplate;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.sys.AccessKey;
import com.nhry.utils.Base64Util;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.SysContant;
import com.nhry.utils.date.Date;
import com.nhry.utils.redis.RedisUtil;
import com.sun.jersey.spi.container.ContainerRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserSessionService {
	private static final Logger LOGGER = Logger.getLogger(UserSessionService.class);
	public static final String accessKey="accessKey";
	public static final String uname="uname";
	private static final ThreadLocal<TSysUser> accessKeyThread = new ThreadLocal<TSysUser>();
	private RedisTemplate objectRedisTemplate;
	
	public String checkIdentity(String accessKey,ContainerRequest request,HttpServletRequest servletRequest){
//		AccessKey ak = (AccessKey) objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_access_key"), accessKey);
//		if(ak == null){
//			 //access不存在
//			LOGGER.warn("当前访问的aceesskey不存在!");
//			 return null;
//		}
		try {
			Map attrs = new HashMap(2);
			attrs.put("access_token", accessKey);
			String userObject = HttpUtils.request(EnvContant.getSystemConst("auth_profile"), attrs);
			System.out.println("------userObject------"+userObject);
			JSONObject userJson = new JSONObject(userObject);
			System.out.println("-------userJson.getString------"+userJson.getString("id"));
			if(userJson.has("id") && !StringUtils.isEmpty(userJson.getString("id"))){                                        
				TSysUser user = (TSysUser)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_user_key"), userJson.getString("id"));
				accessKeyThread.set(user);
				System.out.println("-------userJson.getString来了------"+userJson.getString("id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MessageCode.NORMAL;
	}
	
	/**
	 * 缓存用户信息
	 * @param uName
	 * @param accessKey
	 * @param user
	 * @param request
	 */
	public void cacheUserSession(String uName,String accessKey,TSysUser user,HttpServletRequest request){
		//解密之后再放入缓存
//		accessKey = Base64Util.decodeStr(accessKey);
		//缓存用户对象,如果对于的key用户已经存在，则更新，否则新加
		objectRedisTemplate.opsForHash().put(SysContant.getSystemConst("app_user_key"), user.getLoginName(), user);
		
		//缓存AccessKey对象
//		AccessKey ak = new AccessKey();
//		ak.setAccessIp(request.getRemoteHost());
//		ak.setVisitFirstTime(new Date());
//		ak.setUname(uName);
//		ak.setAck(accessKey);
//		ak.setVisitCount(1);
//		ak.setVisitEndTime(new Date());
//		objectRedisTemplate.opsForHash().put(SysContant.getSystemConst("app_access_key"), accessKey, ak);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public TSysUser getCurrentUser(){
		if(!"product".equals(SysContant.getSystemConst("app_mode"))){
			//测试时使用
			TSysUser user = new TSysUser();
			Date date =  new Date();
			user.setLoginName("88888888");
			user.setDisplayName("测试用户");
			user.setBranchNo("0300005942");
//			user.setDealerId("");
			user.setSalesOrg("4111");
//			user.setSalesOrg("4100");
			user.setLastModified(date);
			return user;
		}
		
		TSysUser user = accessKeyThread.get();
		System.out.println("------accessKeyThread-------"+accessKeyThread.get());
		System.out.println("------获取的用户信息-------"+user);
//		AccessKey ak = (AccessKey)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_access_key"), accessKey);
//		if(ak == null){
//			//反序列化失败
//			LOGGER.warn("aceesskey不存在或者反序列化失败!");
//			return null;
//		}
//		TSysUser user = (TSysUser)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_user_key"), ak.getUname());
		return user;
	}
	
	/**
	 * 生成一个key
	 * @return
	 */
	public String generateKey(){
		return Base64Util.encodeStr(java.util.UUID.randomUUID().toString().replace("-", ""));
	}

	public void setObjectRedisTemplate(RedisTemplate objectRedisTemplate) {
		this.objectRedisTemplate = objectRedisTemplate;
	}
}
