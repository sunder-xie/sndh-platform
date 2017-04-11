package com.nhry.service.stud.dao;

import java.util.List;

import com.nhry.data.stud.domain.TMdMaraStud;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public interface MaraStudService {
	List<TMdMaraStud> findAllListBySalesOrg(String salesOrg);
	
	List<TMdMaraStud> findAllListBySalesOrg();
}
