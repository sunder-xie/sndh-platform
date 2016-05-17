package com.nhry.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;

import com.nhry.domain.User;

public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();;

	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * 转换为普通JavaBean：readValue(json,Student.class)
	 * 转换为List:readValue(json,List.class
	 * ).但是如果我们想把json转换为特定类型的List，比如List<Student>，就不能直接进行转换了。
	 * 因为readValue(json,List
	 * .class)返回其实是List<Map>类型，你不能指定readValue()的第二个参数是List<Student
	 * >.class，所以不能直接转换。
	 * 我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList
	 * ();方法把得到的数组转换为特定类型的List。 转换为Map：readValue(json,Map.class) 我们使用泛型，得到的也是泛型
	 * @param content  要转换的JavaBean类型
	 * @param valueType  原始json字符串数据
	 * @return JavaBean对象
	 */
	public static <T> T toBean(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把JavaBean转换为json字符串 普通对象转换：toJson(Student) List转换：toJson(List)
	 * Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法
	 * @param bean JavaBean对象
	 * @return json字符串
	 */
	public static String toJSonString(Object bean) {
		try {
			return objectMapper.writeValueAsString(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray toJsonArr(Object bean) {
		try {
			String jsonString = toJSonString(bean);
			if (!StringUtils.isEmpty(jsonString)) {
				return new JSONArray(jsonString);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject toJson(Object bean) {
		try {
			String jsonString = toJSonString(bean);
			if (!StringUtils.isEmpty(jsonString)) {
				return new JSONObject(jsonString);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<User> list = new HashSet<User>();
		User u = new User();
		u.setUserName("张三");
		u.setId(12);
		list.add(u);
		// System.out.println(toJSon(list));
		try {
			JSONArray json = new JSONArray(toJSonString(list));
			User[] list2 = (User[]) toBean(json.toString(), User[].class);
			for (User u1 : list2) {
				System.out.println(u1.getUserName());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
