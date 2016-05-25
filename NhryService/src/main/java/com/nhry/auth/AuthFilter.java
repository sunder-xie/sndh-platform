package com.nhry.auth;

import java.util.ArrayList;
import java.util.List;

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

import com.nhry.domain.ResponseModel;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter {
	private static  List<String> whiteList =null;
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;
	
	static{
		whiteList = new ArrayList<String>();
		whiteList.add("/NhryService/rest/user/login");
	}
	
	@Context   
    private HttpServletRequest servletRequest;  
    @Context  
    private HttpServletResponse servletResponse;  
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// TODO Auto-generated method stub
		String uri = request.getAbsolutePath().getPath();
		if("product".equals(SysContant.getSystemConst("app_mode"))){
			if(whiteList.contains(uri)){
				return request;
			}
			String ak = CookieUtil.getCookieValue(servletRequest, UserSessionService.accessKey);
			String userName = CookieUtil.getCookieValue(servletRequest, UserSessionService.uname);
			//未登录
			if(StringUtils.isEmpty(ak) || StringUtils.isEmpty(userName)){
				if(!whiteList.contains(uri)){
					Response response = formatData(MessageCode.UNAUTHORIZED, SysContant.getSystemConst(MessageCode.UNAUTHORIZED), null, Status.UNAUTHORIZED);
		            throw new WebApplicationException(response); 
				}
			}else	if(!MessageCode.NORMAL.equals(UserSessionService.checkIdentity(ak, userName))){
				Response response = formatData(MessageCode.UNAUTHORIZED, SysContant.getSystemConst(MessageCode.UNAUTHORIZED), null, Status.UNAUTHORIZED);
	            throw new WebApplicationException(response); 
			}
		}
		return request;
	}
	
	protected Response formatData(String type, Object msg, Object data,Status statusCode) {
//		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//		response.setHeader("Access-Control-Allow-Credentials", "true");  
//		response.setHeader("Access-Control-Expose-Headers", "Content-Type"); 
//		response.setHeader("Access-Control-Allow-Origin","*");
		
		ResponseModel rsmodel = new ResponseModel();
		rsmodel.setType(type);
		rsmodel.setMsg(msg);
		rsmodel.setData(data==null ? "" : data);
		return Response.ok(rsmodel,MediaType.APPLICATION_JSON).status(statusCode).build();
	}
}
