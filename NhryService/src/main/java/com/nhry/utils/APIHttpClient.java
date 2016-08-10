package com.nhry.utils;

/**
 * Created by cbz on 7/13/2016.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geronimo.mail.util.Base64Encoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class APIHttpClient {

	// 接口地址
	private static String apiURL = "http://test.nhry-dev.com/xiexiaojun/api/v1/order/create";
	private Log logger = LogFactory.getLog(this.getClass());
	private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
	private HttpClient httpClient = null;
	private HttpPost method = null;
	private HttpGet getMethod = null;
	private long startTime = 0L;
	private long endTime = 0L;
	private int status = 0;

	/**
	 * 接口地址
	 * @param url
	 */
	public APIHttpClient(String url) {
		if (url != null) {
			this.apiURL = url;
		}
		if (apiURL != null) {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(apiURL);
			getMethod = new HttpGet(apiURL);
//			method.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
			getMethod.setHeader("appcode", "cisco");
			String key = PrimaryKeyUtils.generateUuidKey();
			String appkey = key.substring(key.length()-12,key.length()-1);
			getMethod.setHeader("appkey", appkey);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			getMethod.setHeader("timestamp", date);
			System.out.println("--------------------:"+"cisco" + appkey+ date);
			String encode = HttpUtils.encodePassword("cisco" + appkey+ date,"010da99b8b994b5@94094f2eae"+HttpUtils.getSixpw(date));
			System.out.println("-----------salt-------:"+"010da99b8b994b5@94094f2eae"+HttpUtils.getSixpw(date));
			System.out.println("------encode-------"+encode);
			getMethod.setHeader("dh-token", encode);
		}
	}

	/**
	 * 调用 API
	 * @param parameters
	 * @return
	 */
    public String post(String parameters) {
        String body = null;
        logger.info("parameters:" + parameters);

        if (method != null & parameters != null
                && !"".equals(parameters.trim())) {
            try {

                // 建立一个NameValuePair数组，用于存储欲传送的参数
                method.addHeader("Content-type","application/json; charset=utf-8");
                method.setHeader("Accept", "application/json");
                StringEntity se = new StringEntity(parameters, Charset.forName("UTF-8"));
                se.setContentEncoding("UTF-8");    
                se.setContentType("application/json");  
                method.setEntity(se);
                startTime = System.currentTimeMillis();
                HttpResponse response = httpClient.execute(method);

                endTime = System.currentTimeMillis();
                int statusCode = response.getStatusLine().getStatusCode();

                logger.info("statusCode:" + statusCode);
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed:" + response.getStatusLine());
                    status = 1;
                }

                // Read the response body
                body = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                // 网络错误
                status = 3;
            } finally {
                logger.info("调用接口状态：" + status);
            }
        }
        return body;
    }
    
    public String httpGet() {
    	String body = null;
    	 try {
			method.addHeader("Content-type","text/plain; charset=utf-8");
			 method.setHeader("Accept", "text/plain");
			 startTime = System.currentTimeMillis();
			 HttpResponse response = httpClient.execute(getMethod);

			 endTime = System.currentTimeMillis();
			 int statusCode = response.getStatusLine().getStatusCode();

			 logger.info("statusCode:" + statusCode);
			 logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
			 if (statusCode != HttpStatus.SC_OK) {
			     logger.error("Method failed:" + response.getStatusLine());
			     status = 1;
			 }

			 // Read the response body
			 body = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return body;
    }


	public static void main(String[] args) {
		APIHttpClient ac = new APIHttpClient("http://127.0.0.1:8082/NhryService/api/v1/dic/allTypeCodes");
		System.out.println(ac.httpGet());
	}

	/**
	 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}
}
