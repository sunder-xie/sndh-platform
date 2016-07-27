package com.nhry.common.auth;

import java.io.IOException;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.exception.MessageCode;
import com.nhry.model.sys.ResponseModel;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.EnvContant;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter {
	private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);
	private static  List<String> whiteUriList =null;
	private static  List<String> whiteHostList =null;
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;
	@Autowired
	private UserSessionService userSessionService;
	
	static{
		whiteUriList = new ArrayList<String>();
		whiteUriList.add("/api/v1/user/login");
	}
	
	static{
		whiteHostList = new ArrayList<String>();
		whiteHostList.add("127.0.0.1");
		whiteHostList.add("localhost");
		whiteHostList.add("test.nhry-dev.com");
	}
	
	@Context   
    private HttpServletRequest servletRequest;  
    @Context  
    private HttpServletResponse servletResponse;  
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// TODO Auto-generated method stub
		String uri = request.getAbsolutePath().getPath();
		String host = request.getAbsolutePath().getHost();
		if("product".equals(SysContant.getSystemConst("app_mode"))){
			if(isExsitUri(uri)){
				return request;
			}
//			String ak = CookieUtil.getCookieValue(servletRequest, UserSessionService.accessKey);
			String ak =request.getHeaderValue("dh_token");
			System.out.println("--------ak-------"+ak);
			String userName = CookieUtil.getCookieValue(servletRequest, UserSessionService.uname);
			//未登录
			if(StringUtils.isEmpty(ak) || StringUtils.isEmpty(userName)){
				if(!whiteUriList.contains(uri)){
					Response response = formatData(MessageCode.SESSION_EXPIRE, SysContant.getSystemConst(MessageCode.SESSION_EXPIRE), EnvContant.getIdmLoginPage(), Status.UNAUTHORIZED);
		            throw new WebApplicationException(response); 
				}
			}else	if(!MessageCode.NORMAL.equals(userSessionService.checkIdentity(ak, userName,request,servletRequest))){
				Response response = formatData(MessageCode.SESSION_EXPIRE, SysContant.getSystemConst(MessageCode.SESSION_EXPIRE), EnvContant.getIdmLoginPage(), Status.UNAUTHORIZED);
	            throw new WebApplicationException(response); 
			}
		}
		return request;
	}
	
	protected Response formatData(String type, Object msg, Object data,Status statusCode) {
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");  
		response.setHeader("Access-Control-Expose-Headers", "Content-Type"); 
		response.setHeader("Access-Control-Allow-Origin","*");
		
		ResponseModel rsmodel = new ResponseModel();
		rsmodel.setType(type);
		rsmodel.setMsg(msg);
		rsmodel.setData(data==null ? "" : data);
		return Response.ok(rsmodel,MediaType.APPLICATION_JSON).status(statusCode).build();
	}
	
	private boolean isExsitUri(String uri){
		for(String u : whiteUriList){
			if(uri.contains(u)){
				return true;
			}
		}
		return false;
	}
}
