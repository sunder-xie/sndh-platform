package com.nhry.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

/**
 * 环境变量工具类
 * 
 * @author Administrator
 */
public class EnvContant {
	private static final String APP_PROD_CONFIG = "env/app-prod.xml";
	private static final String APP_DEV_CONFIG = "env/app-dev.xml";
	static Logger log = Logger.getLogger(EnvContant.class);
	private static Map<String, String> configs = new HashMap<String, String>();

	static {
		try {
			SAXReader read = new SAXReader();
			String appEnvConfig = APP_DEV_CONFIG;
			if ("produce".equals(SysContant.getSystemConst("app_mode"))) {
				appEnvConfig = APP_PROD_CONFIG;
			}
			// 获取根下面的 config,xml文件 进行加载读取
			InputStream inputstream = EnvContant.class.getClassLoader()
					.getResourceAsStream(appEnvConfig);
			List<Element> eleconfigs = read.read(inputstream).getRootElement()
					.elements("config");
			for (Element ele : eleconfigs) {
				String key = ele.attributeValue("key");
				String value = ele.attributeValue("value");
				configs.put(key, value);
			}
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
		}
	}

	public static String getSystemConst(String key) {
		String val = configs.get(key);
		if (val != null) {
			return val.trim();
		}
		return null;
	}

	public static String getIdmLoginPage() {
		return getSystemConst("authurl") + "?client_id="
				+ getSystemConst("client_id") + "&redirect_uri="
				+ getSystemConst("redirect_uri") + "&response_type="
				+ getSystemConst("response_type");
	}

	public static void main(String[] args) {
		System.out.println(getSystemConst("app_mode"));
	}

}
