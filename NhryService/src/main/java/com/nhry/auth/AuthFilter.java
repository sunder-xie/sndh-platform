package com.nhry.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter {
	private static final String userKey="_userKey";
	
	@Context   
    private HttpServletRequest servletRequest;  
    @Context  
    private HttpServletResponse servletResponse;  
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// TODO Auto-generated method stub
//		String uk = CookieUtil.getCookieValue(servletRequest, userKey);
//		if(StringUtils.isEmpty(uk)){//UNAUTHORIZED
//			Response response = Response.ok(throwMsg(MessageCode.UNAUTHORIZED,SysContant.getSystemConst(MessageCode.UNAUTHORIZED),null)).status(Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).build();  
//            throw new WebApplicationException(response); 
//		}
		return null;
	}
	
	public JSONObject throwMsg(String type,Object msg,Object data){
		JSONObject json = new JSONObject();
		try {
			json.put("type", type);
			json.put("msg", msg);
			json.put("data", data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
