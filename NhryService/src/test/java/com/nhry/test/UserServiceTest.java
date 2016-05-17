package com.nhry.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;

public class UserServiceTest {
	UserMapper userMapper = null;
	@Before
	public void setUp() throws Exception {
		String[] xmls = new String[]{ "classpath:spring-context.xml","classpath:dataSource.xml" };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        userMapper = (UserMapper) context.getBean("UserMapperImpl");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		List<User> list = userMapper.selectByUserName("张");
//		for(User u : list){
//			System.out.println(u.getUserName());
//		}
	}

}
