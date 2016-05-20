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
 * 系统变量工具类
 * @author Administrator
 */
public class SysContant {
    private static final String SYS_CONFIG="sys/sys-config.xml";
    static Logger log=Logger.getLogger(SysContant.class); 
	private static  Map<String, String> configs=new HashMap<String, String>() ;
	 
	static{
		try{
			SAXReader read=new SAXReader();
           //获取根下面的 config,xml文件 进行加载读取
    		InputStream inputstream= SysContant.class.getClassLoader().getResourceAsStream(SYS_CONFIG);
			 List<Element> eleconfigs= read.read(inputstream).getRootElement().elements("config");
			 for(Element ele:eleconfigs){
				 	String key=ele.attributeValue("key");
				 	String value=ele.attributeValue("value");
				 	configs.put(key, value);
			 }
		}catch(Exception ex){
			log.error(ex);
			ex.printStackTrace();
		}
	}
	
	public static String getSystemConst(String key){
	  String val = configs.get(key);
	  if(val != null){
		  return val.trim();
	  }
	  return null;
	}
	
	public static void main(String[] args){
		System.out.println(getSystemConst("sys_yetai_type"));
	}

}
