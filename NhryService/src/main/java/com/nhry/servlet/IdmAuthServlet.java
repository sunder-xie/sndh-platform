package com.nhry.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.nhry.utils.APIHttpClient;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;

public class IdmAuthServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取临时令牌
		String code = request.getParameter("code");
		if (!StringUtils.isEmpty(code)) {
			Map<String,Object> attrs = new HashMap<String,Object>();
			attrs.put("client_id", EnvContant.getSystemConst("client_secret"));
			attrs.put("client_secret", EnvContant.getSystemConst("client_id"));
			attrs.put("grant_type", EnvContant.getSystemConst("grant_type"));
			attrs.put("redirect_uri", EnvContant.getSystemConst("redirect_uri"));
			attrs.put("code", code);
			String access_token = HttpUtils.request(EnvContant.getSystemConst("authurl"), attrs);
			System.out.println("-----access_token-------"+access_token);
			if(!StringUtils.isEmpty(access_token)){
				attrs.clear();
				attrs.put("access_token", access_token);
				String msg = HttpUtils.request(EnvContant.getSystemConst("authurl"), attrs);
				System.out.println("-----msg-------"+msg);
			}
		} else {
			//跳转到登录页面
			response.sendRedirect(EnvContant.getSystemConst("authurl")
					+ "?client_id=" + EnvContant.getSystemConst("client_id")
					+ "&redirect_uri="
					+ EnvContant.getSystemConst("redirect_uri")
					+ "&response_type="
					+ EnvContant.getSystemConst("response_type"));
		}
	}

}
