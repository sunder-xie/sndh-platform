package com.nhry.common.auth;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.utils.Base64Util;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.SysContant;
import com.nhry.utils.date.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UserSessionService {
	private static final Logger LOGGER = Logger.getLogger(UserSessionService.class);
	public static final String accessKey="accessKey";
	public static final String uname="uname";
	private static final ThreadLocal<TSysUser> accessKeyThread = new ThreadLocal<TSysUser>();
	private RedisTemplate objectRedisTemplate;
	private static Map<String,String> authsMap = null;

	static{
		authsMap = new HashMap<String,String>();
		authsMap.put("ec", "Ab1234@Ec");
		authsMap.put("nt", "Ac1234@Nt");
		authsMap.put("app", "Ad1234@App");
	}

	public String checkIdentity(String accessKey,HttpServletRequest servletRequest,String authflag){
		if(AuthFilter.IDM_AUTH.equals(authflag)){
			return checkIdmAuth(accessKey, servletRequest);
		}else if(AuthFilter.DH_AUTH.equals(authflag)){
			TSysUser user = (TSysUser)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_user_key"),accessKey);
			accessKeyThread.set(user);
			return MessageCode.NORMAL;
		}else if(AuthFilter.HTTP_AUTH.equals(authflag)){
			TSysUser sysuser = (TSysUser)servletRequest.getSession().getAttribute("dh-auth");
			if(sysuser != null){
				accessKeyThread.set(sysuser);
			}else{
				//http basic璁よ瘉
				return checkHeaderAuth(servletRequest);
			}
		}
		return MessageCode.NORMAL;
	}

	/**
	 * idm auth2.0 鐢ㄦ埛璁よ瘉
	 * @param accessKey
	 * @param servletRequest
	 * @return
	 */
	private String checkIdmAuth(String accessKey,HttpServletRequest servletRequest){
		try {
			if("-1".equals(accessKey)){
				return MessageCode.SESSION_EXPIRE;
			}
			Map attrs = new HashMap(2);
			attrs.put("access_token", accessKey);
			String userObject = HttpUtils.request(EnvContant.getSystemConst("auth_profile"), attrs);
			JSONObject userJson = new JSONObject(userObject);
			if(userJson.has("id") && !StringUtils.isEmpty(userJson.getString("id"))){
				TSysUser user = (TSysUser)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_user_key"), userJson.getString("id"));
				accessKeyThread.set(user);
				return MessageCode.NORMAL;
			}else{
				return MessageCode.SESSION_EXPIRE;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MessageCode.SESSION_EXPIRE;
	}

	/**
	 * 妫€鏌ttp basic
	 * @param servletRequest
	 * @return
	 */
	private String checkHeaderAuth(HttpServletRequest servletRequest)  {
		String auth = servletRequest.getHeader("Authorization");
		if ((auth != null) && (auth.length() > 6)) {
			auth = auth.substring(6, auth.length());
			String decodedAuth = getFromBASE64(auth);
			if(StringUtils.isEmpty(decodedAuth) || decodedAuth.split(":").length < 2){
				return MessageCode.UNAUTHORIZED;
			}
			String[] auths = decodedAuth.split(":");
			if(auths != null && auths.length == 2){
				if(!(authsMap.get(auths[0]) != null && authsMap.get(auths[0]).equals(auths[1]))){
					return MessageCode.UNAUTHORIZED;
				}
				//鏋勫缓铏氭嫙鐢ㄦ埛
				TSysUser sysuser = new TSysUser();
				sysuser.setLoginName(auths[0]);
				sysuser.setDisplayName(auths[0]);
				accessKeyThread.set(sysuser);
				return MessageCode.NORMAL;
			}
		}
		return MessageCode.UNAUTHORIZED;
	}

	private String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 缂撳瓨鐢ㄦ埛淇℃伅
	 * @param uName
	 * @param accessKey
	 * @param user
	 * @param request
	 */
	public void cacheUserSession(String uName,String accessKey,TSysUser user,HttpServletRequest request){
		//瑙ｅ瘑涔嬪悗鍐嶆斁鍏ョ紦瀛�
		//缂撳瓨鐢ㄦ埛瀵硅薄,濡傛灉瀵逛簬鐨刱ey鐢ㄦ埛宸茬粡瀛樺湪锛屽垯鏇存柊锛屽惁鍒欐柊鍔
		objectRedisTemplate.opsForHash().put(SysContant.getSystemConst("app_user_key"), user.getLoginName(), user);
	}

	/**
	 * 鑾峰彇褰撳墠鐢ㄦ埛
	 * @return
	 */
	public TSysUser getCurrentUser(){
		if(!"product".equals(SysContant.getSystemConst("app_mode"))){
			//娴嬭瘯鏃朵娇鐢�
			TSysUser user = new TSysUser();
			Date date =  new Date();
			user.setLoginName("88888888");
			user.setDisplayName("娴嬭瘯鐢ㄦ埛");
			user.setBranchNo("0300005942");
//			user.setDealerId("");
			user.setSalesOrg("4111");
//			user.setSalesOrg("4100");
			user.setLastModified(date);
			return user;
		}

		TSysUser user = accessKeyThread.get();
//		AccessKey ak = (AccessKey)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_access_key"), accessKey);
//		if(ak == null){
//			//鍙嶅簭鍒楀寲澶辫触
//			LOGGER.warn("aceesskey涓嶅瓨鍦ㄦ垨鑰呭弽搴忓垪鍖栧け璐�!");
//			return null;
//		}
//		TSysUser user = (TSysUser)objectRedisTemplate.opsForHash().get(SysContant.getSystemConst("app_user_key"), ak.getUname());
		return user;
	}

	/**
	 * 鐢熸垚涓€涓猭ey
	 * @return
	 */
	public String generateKey(){
		return Base64Util.encodeStr(java.util.UUID.randomUUID().toString().replace("-", ""));
	}

	public void setObjectRedisTemplate(RedisTemplate objectRedisTemplate) {
		this.objectRedisTemplate = objectRedisTemplate;
	}
}
