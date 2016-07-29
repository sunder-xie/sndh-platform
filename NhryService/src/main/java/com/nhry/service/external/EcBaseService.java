package com.nhry.service.external;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nhry.data.basic.dao.TSysPushMessageLogMapper;
import com.nhry.data.basic.domain.TSysPushMessageLog;
import com.nhry.utils.APIHttpClient;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

public class EcBaseService {
    private TSysPushMessageLogMapper messLogMapper;
    
    /**
     * 推送消息给电商
     * @param url  http地址
     * @param reqbody  json参数体
     * @param flag        推送失败时，是否需要记录消息(以便于再次推送) true : 需要   false：不需要
     * @return
     */
    public boolean pushMessage2Ec(String url,String reqbody,boolean flag){
    	try {
    		Map<String, Object> params = new HashMap<String,Object>(2);
			params.put("data", reqbody);
        	String msg = HttpUtils.request(url, params);;
        	//成功调用
    		JSONObject json = new JSONObject(msg);
    		if(json.has("success") && json.getBoolean("success")){
    			return true;
    		}else{
    			if(!flag){
    				//不需要记录推送失败的消息
    				return false;
    			}
    			//推送不成功
    			addMessage(url, reqbody, json.has("errorMessage") ? json.getString("errorMessage") : null, json.has("errorCode") ? json.getString("errorCode") : null);
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(!flag){
				//不需要记录推送失败的消息
				return false;
			}
    		//调用不成功
    		addMessage(url, reqbody,e.getMessage() ,"SERVEREROR");
			return false;
		}
    	return false;
    }
    
    /**
     * 添加失败消息记录
     * @param url
     * @param reqbody
     * @param errorMessage
     * @param errorCode
     */
    private void addMessage(String url,String reqbody,String errorMessage,String errorCode){
    	TSysPushMessageLog mess = new TSysPushMessageLog();
		mess.setMno(PrimaryKeyUtils.generateUuidKey());
		mess.setReqType("POST");
		mess.setReqUrl(url);
		mess.setReqContent(reqbody);
		mess.setSysFlag("10"); //10 ： 电商
		mess.setCreateAt(new Date());
		if(!StringUtils.isEmpty(errorMessage)){
			if(errorMessage.length() > 200){
				mess.setErrorMessage(errorMessage);
			}else{
				mess.setErrorMessage(errorMessage);
			}
		}
		mess.setErrorCode(errorCode);
		messLogMapper.addPushMessageLog(mess);
    }
    
	public void setMessLogMapper(TSysPushMessageLogMapper messLogMapper) {
		this.messLogMapper = messLogMapper;
	}
}
