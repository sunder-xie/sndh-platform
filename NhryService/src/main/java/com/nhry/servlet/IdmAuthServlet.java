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
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.ladp.LadpService;
import com.nhry.data.auth.domain.TSysAccesskey;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.service.auth.dao.TSysAccesskeyService;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.APIHttpClient;
import com.nhry.utils.Base64Util;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.date.Date;
import com.nhry.utils.json.JackJson;
@Component
public class IdmAuthServlet extends HttpServlet {
	@Autowired
	private UserService userService;
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private TSysAccesskeyService isysAkService;
	
	public void init(ServletConfig config) throws ServletException {
		 SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
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
			String ip = request.getParameter("id");
			System.out.println("---------code-----------"+code);
			if (!StringUtils.isEmpty(code)) {
				Map<String,Object> attrs = new HashMap<String,Object>();
				attrs.put("client_id", EnvContant.getSystemConst("client_id"));
				attrs.put("client_secret", EnvContant.getSystemConst("client_secret"));
				attrs.put("grant_type", EnvContant.getSystemConst("grant_type"));
				attrs.put("redirect_uri", EnvContant.getSystemConst("redirect_uri"));
				attrs.put("code", code);
				String access_token = HttpUtils.request(EnvContant.getSystemConst("auth_token"), attrs);
				System.out.println("---------access_token-----------"+access_token);
				if(!StringUtils.isEmpty(access_token)){
					attrs.clear();
					String token = access_token.split("=")[1].split("&")[0];
					attrs.put("access_token", token);
					String userObject = HttpUtils.request(EnvContant.getSystemConst("auth_profile"), attrs);
					System.out.println("---------userObject-----------"+userObject);
					JSONObject userJson = new JSONObject(userObject);
					if(userJson.has("id") && !StringUtils.isEmpty(userJson.getString("id"))){
						TSysUser user = new TSysUser();
						user.setLoginName(userJson.getString("id"));
						TSysUser loginuser = userService.login(user);
						System.out.println("---------loginuser-----------"+loginuser.getLoginName());
						if(loginuser == null){
							System.out.println("---------找不到登录人-----------");
							sendRedirectToLogin(response);
							return;
						}
						TSysAccesskey ak = new TSysAccesskey();
						ak.setAccesskey(token);
						ak.setLoginname(user.getLoginName());
						ak.setType("10"); //10 : idm auth2.0
						ak.setVisitFirstTime(new Date());
						ak.setVisitLastTime(new Date());
						isysAkService.updateIsysAccessKey(ak);
						sendRedirectToHomePage(request, response, token,ip);
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
	
	public void sendRedirectToHomePage(HttpServletRequest request, HttpServletResponse response,String token,String ip){
		//跳转到登录页面
		try {
			if(StringUtils.isEmpty(ip)){
				response.setHeader("appkey", token);
				response.sendRedirect(EnvContant.getSystemConst("front_home_page")+"?appkey="+token);
			}else{
				response.setHeader("appkey", token);
				response.sendRedirect("http://"+Base64Util.decodeStr(ip)+EnvContant.getSystemConst("front_short_url")+"?appkey="+token);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
