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
	private static String apiURL = "http://test.nhry-dev.com/xiexiaojun/api/v1/order/create";
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


	public static void main(String[] args) {
		APIHttpClient ac = new APIHttpClient(apiURL);
		String param = "{"+
	   "\"order\": {"+
		"\"orderNo\": \"00160700000430\","+
		"\"orderType\": null,"+
		"\"orderDate\": null,"+
		"\"endDate\": null,"+
		"\"paymentmethod\": \"20\","+
		"\"preorderSource\": \"20\","+
		"\"onlineorderNo\": \"PT00160700000428\","+
		"\"branchNo\": \"001\","+
		"\"branchTel\": \"13245444444\","+
		"\"branchName\": \"奶站1\","+
		"\"customerTel\": null,"+
		"\"milkmemberNo\": \"125\","+
		"\"memberNo\": \"125\","+
		"\"paymentStat\": \"10\","+
		"\"milkboxStat\": \"20\","+
		"\"initAmt\": null,"+
		"\"curAmt\": null,"+
		"\"dispLineNo\": null,"+
		"\"empNo\": null,"+
		"\"empName\": null,"+
		"\"adressNo\": null,"+
		"\"createrNo\": null,"+
		"\"createrBy\": null,"+
		"\"preorderStat\": \"20\","+
		"\"memoTxt\": \"客服备注\","+
		"\"payMethod\": null,"+
		"\"solicitNo\": null,"+
		"\"solicitDate\": null,"+
		"\"retDate\": null,"+
		"\"retReason\": null,"+
		"\"stopDateStart\": null,"+
		"\"stopDateEnd\": null,"+
		"\"stopDateStartStr\": null,"+
		"\"stopDateEndStr\": null,"+
		"\"solicitBy\": null,"+
		"\"stopReason\": null,"+
		"\"deliveryType\": \"20\","+
		"\"solicitorNo\": null"+
	"},"+
	"\"entries\": [{"+
		"\"orderNo\": \"00160700000428\","+
		"\"itemNo\": \"1\","+
		"\"orderDate\": null,"+
		"\"refItemNo\": \"1\","+
		"\"matnr\": \"000000000050000006\","+
		"\"matnrTxt\": \"新希望(华西)玻璃瓶24小时鲜牛奶200ml\","+
		"\"unit\": \"瓶\","+
		"\"qty\": \"1\","+
		"\"salesPrice\": \"5\","+
		"\"itemamount\": \"150\","+
		"\"ruleType\": \"10\","+
		"\"dispdays\": null,"+
		"\"gapDays\": \"0\","+
		"\"ruleText\": null,"+
		"\"reachTimeType\": \"下午\","+
		"\"startDispDateStr\": \"2016-07-30T00:00:00.000+0800\","+
		"\"endDispDateStr\": \"2016-08-29T00:00:00.000+0800\","+
		"\"promotion\": null,"+
		"\"promdays\": null,"+
		"\"status\": \"10\","+
		"\"buyqty\": null,"+
		"\"giftqty\": null	}],"+
	"\"address\": {"+
		"\"addressId\": null,"+
		"\"recvName\": \"1552\","+
		"\"province\": \"370000\","+
		"\"city\": \"370200\","+
		"\"county\": \"370212\","+
		"\"street\": null,"+
		"\"mp\": \"2255\","+
		"\"tel\": \"4554\","+
		"\"zip\": null,"+
		"\"addressTxt\": \"中鼎国际工程有限责任公司\","+
		"\"residentialArea\": \"0005\","+
		"\"vipCustNo\": null,"+
		"\"isDefault\": \"N\"}}";
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
