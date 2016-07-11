package com.nhry.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.utils.CookieUtil;

public class ApiServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String[] xmls = new String[]{ "classpath:beans/spring-context.xml","classpath:beans/dataSource.xml","classpath:beans/*-bean.xml"  };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        UserSessionService userSessionService = (UserSessionService) context.getBean("userSessionService");
		TSysUser user = new TSysUser();
		user.setLoginName("swagger");
		user.setDisplayName("swagger");
		String accesskey = userSessionService.generateKey();
		CookieUtil.setCookie(request, response, UserSessionService.accessKey, accesskey);
		CookieUtil.setCookie(request, response, UserSessionService.uname, user.getLoginName());
		userSessionService.cacheUserSession(user.getLoginName(), accesskey, user,request);
		userSessionService.cacheUserSession("swagger", "swaggerThread", user,request);
		if(StringUtils.isEmpty(request.getContextPath())){
			response.sendRedirect("/xiexiaojun/swagger/index.html");
		}else{
			response.sendRedirect(request.getContextPath()+"/swagger/index.html");
		}
	}
}
