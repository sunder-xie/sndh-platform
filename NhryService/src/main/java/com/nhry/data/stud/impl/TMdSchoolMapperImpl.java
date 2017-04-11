package com.nhry.data.stud.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMdSchoolMapper;
import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.model.stud.SchoolQueryModel;

/**
*
*@author dai
*/
public class TMdSchoolMapperImpl implements TMdSchoolMapper {
	
	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;

    
	@Override
	public PageInfo<TMdSchool> serchSchoolList(SchoolQueryModel model) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("serchSchoolList", model, Integer.parseInt(model.getPageNum()), Integer.parseInt(model.getPageSize()));
	}
	

	@Override
	public int updateByTMdSchool(TMdSchool tMdSchool) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateByTMdSchool", tMdSchool);
	}
	
	@Override
	public List<TMdSchool> findSchoolList(SchoolQueryModel model) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("serchSchoolList", model);
	}

	@Override
	public TMdSchool selectByPrimaryKey(SchoolQueryModel model) {
		return sqlSessionTemplate.selectOne("selectOne", model);
	}


	
	
	



	

}
