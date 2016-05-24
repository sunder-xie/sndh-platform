package com.nhry.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

public class ObjectSerializeUtil {
	/** 
     * @param serStr 
     * @throws UnsupportedEncodingException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @描述 —— 将字符串反序列化成对象 
     */  
    public static Object getObjFromStr(String serStr){  
        Object result=null;
		try {
			String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");  
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(  
			        redStr.getBytes("ISO-8859-1"));  
			ObjectInputStream objectInputStream = new ObjectInputStream(  
			        byteArrayInputStream);  
			result = objectInputStream.readObject();  
			objectInputStream.close();  
			byteArrayInputStream.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
        return result;  
    }  
  
    /** 
     * @return 
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     * @描述 —— 将对象序列化成字符串 
     */  
    public static String getStrFromObj(Object obj) { 
    	String serStr = null;
        try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(  
			        byteArrayOutputStream);  
			objectOutputStream.writeObject(obj);  
			serStr = byteArrayOutputStream.toString("ISO-8859-1");  
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
  
			objectOutputStream.close();  
			byteArrayOutputStream.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return serStr;  
    }  
}
