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
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

public class APIHttpClient {

	// 接口地址
	private static String apiURL = "http://127.0.0.1:8082/NhryService/api/v1/order/backorder";
	private Log logger = LogFactory.getLog(this.getClass());
	private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
	private HttpClient httpClient = null;
	private HttpPost method = null;
	private long startTime = 0L;
	private long endTime = 0L;
	private int status = 0;

	/**
	 * 接口地址
	 *
	 * @param url
	 */
	public APIHttpClient(String url) {
		if (url != null) {
			this.apiURL = url;
		}
		if (apiURL != null) {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(apiURL);
			String auth = "ec" + ":" + "Ab1234@Ec";  //用户名、密码
			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(encodedAuth);
			method.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}
	}

	/**
	 * 调用 API
	 * @param parameters
	 * @return
	 */
	public String post(String parameters) {
		String body = null;
		if (method != null & parameters != null && !"".equals(parameters.trim())) {
			try {
				// 建立一个NameValuePair数组，用于存储欲传送的参数
//				method.addHeader("Content-type","plain/text; charset=utf-8");
//				method.setHeader("Accept", "plain/text");
				method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
				startTime = System.currentTimeMillis();
				HttpResponse response = httpClient.execute(method);

				endTime = System.currentTimeMillis();
				int statusCode = response.getStatusLine().getStatusCode();

				logger.info("statusCode:" + statusCode);
				logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Method failed:" + response.getStatusLine());
					status = statusCode;
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

	public static void main(String[] args) {
		APIHttpClient ac = new APIHttpClient(apiURL);
		String param = "{\"orderNo\":\"1468304451857\",\"reason\":\"不想要了\"}";
		System.out.println(ac.post(param));
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
