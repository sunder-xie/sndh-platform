package com.nhry.common.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.nhry.common.exception.MessageCode;
import com.nhry.model.sys.ResponseModel;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.EnvContant;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Component
public class AuthFilter implements ContainerRequestFilter {
	private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);
	protected static  List<String> whiteUriList =null;
	protected static  List<String> whiteHostList =null;
	protected static final String IDM_AUTH="idm_auth";
	protected static final String DH_AUTH="dh_auth";
	protected static final String HTTP_AUTH="http_auth";
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;
	@Autowired
	protected UserSessionService userSessionService;
	
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

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// TODO Auto-generated method stub
		String uri = request.getAbsolutePath().getPath();
		if("product".equals(SysContant.getSystemConst("app_mode"))){
			if(isExsitUri(uri)){
				//白名单过滤
				return request;
			}
			String idm_auth =request.getHeaderValue("dh-token");
			String dh_ak = CookieUtil.getCookieValue(this.request, UserSessionService.accessKey);
			String http_auth = request.getHeaderValue("Authorization");
			if(!StringUtils.isEmpty(idm_auth)){
				//idm认证
				return new IdmAuthFilter(this.request,this.response,this.userSessionService).filter(request);
			}else if(!StringUtils.isEmpty(http_auth)){
				//http basic认证
				return new HttpBasicFilter(this.request,this.response,this.userSessionService).filter(request);
			}else if(!StringUtils.isEmpty(dh_ak)){
				//订户系统原来的登录方式
				return new DhAuthFilter(this.request,this.response,this.userSessionService).filter(request);
			}else{
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
	
	private boolean isExsitUri(String uri){
		for(String u : whiteUriList){
			if(uri.contains(u)){
				return true;
			}
		}
		return false;
	}
	
	
}
