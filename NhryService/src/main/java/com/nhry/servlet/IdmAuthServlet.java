package com.nhry.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.ladp.LadpService;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.APIHttpClient;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.json.JackJson;

public class IdmAuthServlet extends HttpServlet {
	private UserService userService;
	private UserSessionService userSessionService;
	
	public void init(ServletConfig config) throws ServletException {
//		   SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
//		         config.getServletContext());
		String[] xmls = new String[]{ "classpath:beans/spring-context.xml","classpath:beans/dataSource.xml","classpath:beans/*-bean.xml"  };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        userSessionService = (UserSessionService)context.getBean("userSessionService");
        userService = (UserService)context.getBean("userService");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取临时令牌
		try {
			String code = request.getParameter("code");
			if (!StringUtils.isEmpty(code)) {
				Map<String,Object> attrs = new HashMap<String,Object>();
				attrs.put("client_id", EnvContant.getSystemConst("client_id"));
				attrs.put("client_secret", EnvContant.getSystemConst("client_secret"));
				attrs.put("grant_type", EnvContant.getSystemConst("grant_type"));
				attrs.put("redirect_uri", EnvContant.getSystemConst("redirect_uri"));
				attrs.put("code", code);
				String access_token = HttpUtils.request(EnvContant.getSystemConst("auth_token"), attrs);
				System.out.println("-----access_token-------"+access_token);
				if(!StringUtils.isEmpty(access_token)){
					attrs.clear();
					String token = access_token.split("=")[1].split("&")[0];
					attrs.put("access_token", token);
					String userObject = HttpUtils.request(EnvContant.getSystemConst("auth_profile"), attrs);
					//{"id":"ex_crmsongnaiyuan","attributes":[{"uid":"ex_crmsongnaiyuan"}]}
					JSONObject userJson = new JSONObject(userObject);
					if(userJson.has("id") && !StringUtils.isEmpty(userJson.getString("id"))){
						TSysUser user = new TSysUser();
						user.setLoginName(userJson.getString("id"));
						TSysUser loginuser = userService.login(user);
						CookieUtil.setCookie(request, response, UserSessionService.accessKey, access_token);
						CookieUtil.setCookie(request, response, UserSessionService.uname, loginuser.getLoginName());
						userSessionService.cacheUserSession(user.getLoginName(), access_token, loginuser,request);
						sendRedirectToHomePage(request, response, token);
					}else{
						sendRedirectToLogin(response);
					}
				}
			} else {
				sendRedirectToLogin(response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendRedirectToLogin(response);
		}
	}
	
	public void sendRedirectToLogin(HttpServletResponse response){
		//跳转到登录页面
		try {
			response.sendRedirect(EnvContant.getSystemConst("authurl")
					+ "?client_id=" + EnvContant.getSystemConst("client_id")
					+ "&redirect_uri="
					+ EnvContant.getSystemConst("redirect_uri")
					+ "&response_type="
					+ EnvContant.getSystemConst("response_type"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendRedirectToHomePage(HttpServletRequest request, HttpServletResponse response,String token){
		//跳转到登录页面
		try {
			response.setHeader("dh_token", token);
			response.sendRedirect(EnvContant.getSystemConst("front_home_page"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
