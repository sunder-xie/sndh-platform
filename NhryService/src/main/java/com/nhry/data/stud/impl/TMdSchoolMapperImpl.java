package com.nhry.data.stud.impl;

import java.util.List;

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
	
	private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
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
	public int deleteByPrimaryKey(String schoolCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TMdSchool record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TMdSchool record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TMdSchool selectByPrimaryKey(String schoolCode) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int updateByPrimaryKey(TMdSchool record) {
		// TODO Auto-generated method stub
		return 0;
	}




	

}
