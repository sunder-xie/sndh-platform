package com.nhry.common.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.exception.MessageCode;
import com.nhry.utils.EnvContant;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.container.ContainerRequest;

public class IdmAuthFilter extends AuthFilter {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected UserSessionService userSessionService;
	
    public IdmAuthFilter(HttpServletRequest request,HttpServletResponse response,UserSessionService userSessionService){
    	this.request=request;
    	this.response = response;
    	this.userSessionService = userSessionService;
    }
    
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// TODO Auto-generated method stub
		String uri = request.getAbsolutePath().getPath();
		String ak =request.getHeaderValue("dh-token");
		String flag =request.getHeaderValue("nh-flag");
		String host = request.getHeaderValue("Host");
		//未登录
		if(StringUtils.isEmpty(ak)){
			if(!whiteUriList.contains(uri)){
				Response response = formatData(MessageCode.SESSION_EXPIRE, SysContant.getSystemConst(MessageCode.SESSION_EXPIRE), 
						StringUtils.isEmpty(flag) ? EnvContant.getIdmLoginPage(null) : EnvContant.getIdmLoginPage(host), Status.UNAUTHORIZED);
	            throw new WebApplicationException(response); 
			}
		}else	if(!MessageCode.NORMAL.equals(userSessionService.checkIdentity(ak,this.request,IDM_AUTH))){
			Response response = formatData(MessageCode.SESSION_EXPIRE, SysContant.getSystemConst(MessageCode.SESSION_EXPIRE), 
					StringUtils.isEmpty(flag) ? EnvContant.getIdmLoginPage(null) : EnvContant.getIdmLoginPage(host), Status.UNAUTHORIZED);
            throw new WebApplicationException(response); 
		}
		return request;
	}
}
