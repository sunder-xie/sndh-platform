package com.nhry.data.stud.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMdMaraStudMapper;
import com.nhry.data.stud.domain.TMdMaraStud;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class TMdMaraStudExMapperImpl implements TMdMaraStudMapper {

	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<TMdMaraStud> findAllListBySalesOrg(String salesOrg) {
		return sqlSessionTemplate.selectList("findAllListBySalesOrg", salesOrg);
	}


}
