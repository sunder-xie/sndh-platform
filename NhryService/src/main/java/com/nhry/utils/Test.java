package com.nhry.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;

import com.nhry.common.ladp.LadpService;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.service.external.dao.EcService;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] xmls = new String[]{ "classpath:beans/spring-context.xml","classpath:beans/dataSource.xml","classpath:beans/*-bean.xml"  };
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        TMdBranchMapper branchMapper = (TMdBranchMapper)context.getBean("branchMapper");
        TMdBranch branch = branchMapper.selectBranchByNo("0300003218");
        
        
        EcService ecservice = (EcService)context.getBean("messLogService");
        
        TaskExecutor taskExecutor = (TaskExecutor)context.getBean("taskExecutor");
        taskExecutor.execute(new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//为线程设定一个名称，以免线程之间相互影响
			  this.setName("pushBranch2EcForUpt");
			  ecservice.pushBranch2EcForUpt(branch);
			}
		});
	}

}
