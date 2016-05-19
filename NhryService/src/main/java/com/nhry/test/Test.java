package com.nhry.test;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;
import com.nhry.service.BaseService;

public class Test extends BaseService{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] xmls = new String[]{ "classpath:spring-context.xml","classpath:dataSource.xml","classpath:*-bean.xml"  };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        UserMapper userMapper = (UserMapper) context.getBean("userMapper");
//        PageHelper.startPage(0, 2); // 核心分页代码 
//        List<User> list = userMapper.all();
//		for(User u : list){
//			System.out.println(u.getUserName());
//		}
        User u = new User();
        u.setId(124);
        u.setUserName("李四1");
        u.setComments("测试用户");
        userMapper.addUser(u);
	}
}
