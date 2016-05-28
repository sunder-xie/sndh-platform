package com.nhry.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import com.nhry.common.CommonService;
import com.nhry.common.model.AccessKey;
import com.nhry.utils.Date;
import com.nhry.utils.ObjectSerializeUtil;
import com.nhry.utils.RedisUtil;
import com.nhry.utils.SysContant;

public class SessionManager extends CommonService {
	private RedisTemplate objectRedisTemplate;
	private static final Logger LOGGER = Logger.getLogger(SessionManager.class);
    private static List<String> tempKey = new ArrayList<String>();
	private static LinkedBlockingQueue<AccessKey> sessions = new LinkedBlockingQueue<AccessKey>();

	/**
	 * 缓存accesskey数据进入队列中
	 * @param ack
	 * @param uname
	 * @param ip
	 * @param time
	 * @param uri
	 */
	public static void addSessionsCache(String ack, String uname, String ip,
			Date time, String uri) {
		sessions.add(new AccessKey(ack, uname, null, time, ip, 1, uri));
	}

	/**
	 * 维护redis session信息
	 */
	public void handleRedisSession() {
		while (true) {
			 if(sessions.size() > 0){
				 AccessKey _ak = sessions.poll();
					
					Map<String, String> accessMap = RedisUtil.getRu().hgetall(SysContant.getSystemConst("app_access_key"));
					if (accessMap == null || accessMap.get(_ak.getAck()) == null) {
						// access不存在
						LOGGER.error("当前访问的aceesskey不存在!");
					}

					AccessKey ak = (AccessKey) ObjectSerializeUtil.getObjFromStr(accessMap.get(_ak.getAck()));
					if (ak == null) {
						// 反序列化失败
						LOGGER.error("aceesskey反序列化失败!");
					}
					ak.setLastUri(_ak.getLastUri());
					ak.setVisitCount(ak.getVisitCount() + _ak.getVisitCount());
					ak.setVisitEndTime(_ak.getVisitEndTime());
					accessMap.put(_ak.getAck(), ObjectSerializeUtil.getStrFromObj(ak));
					
//					RedisUtil.getRu().hdel(SysContant.getSystemConst("app_access_key"),_ak.getAck());
					
					RedisUtil.getRu().hmset(SysContant.getSystemConst("app_access_key"), accessMap);

					_ak = null;
			 }
		}
	}
	
	/**
	 * 清理过时session信息
	 */
	public void cleanRedisSession() {
		if(tempKey.size() > 0){
			tempKey.clear();
		}
		System.out.println("---------启动检查session过期操作--------------");
		List<AccessKey> accessKeys = objectRedisTemplate.opsForHash().values(SysContant.getSystemConst("app_access_key"));
		Date date = new Date();
		for(AccessKey ak : accessKeys){
			if(ak != null){
				if (ak.getVisitEndTime() == null) {
					tempKey.add(ak.getAck());
				} else if (date.addHours(-Integer.parseInt(SysContant.getSystemConst("session_duration"))).getTime() > ak.getVisitEndTime().getTime()) {
					// 如果AccessKey最后一次的访问时间，已经超过session_duration小时数,则移除该Accesskey
					tempKey.add(ak.getAck());
				}
			}else{
				LOGGER.error("缓存的AccessKey对象反序列化出来出现null值，请核查！"+SysContant.getSystemConst("app_access_key"));
			}
		}
		if(tempKey.size() > 0){
			objectRedisTemplate.opsForHash().delete(SysContant.getSystemConst("app_access_key"), tempKey.toArray(new String[tempKey.size()]));
			System.out.println("--------------成功清除-----"+tempKey.size()+"个过期session");
		}
	}

	@Override
	public void onContainerStartedExecute() {
		// TODO Auto-generated method stub
//		startNewThreadExecute("handleRedisSession");
	}

	@Override
	public void startNewThreadExecute(String... threadNames) {
		// TODO Auto-generated method stub
//		super.startNewThreadExecute(threadNames);
	}

	@Override
	public void executeInThread(String threadName) {
		// TODO Auto-generated method stub
//		this.handleRedisSession();
	}

	public void setObjectRedisTemplate(RedisTemplate objectRedisTemplate) {
		this.objectRedisTemplate = objectRedisTemplate;
	}
}
