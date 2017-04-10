package com.nhry.service.stud.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.dao.TMdSchoolMapper;
import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.model.stud.SchoolQueryModel;
import com.nhry.service.stud.dao.SchoolService;

/**
*
*@author dai
*/

public class SchoolServiceImpl implements SchoolService {
	
	@Autowired
	TMdSchoolMapper tMdSchoolMapper;

	@Override
	public PageInfo<TMdSchool> serchSchoolList(SchoolQueryModel model) {
		return tMdSchoolMapper.serchSchoolList(model);
	}

	@Override
	public List<TMdSchool> findSchoolList(SchoolQueryModel model) {
		return tMdSchoolMapper.findSchoolList(model);
	}
	
	@Override
	public int updateSchool(TMdSchool tMdSchool) {
		return tMdSchoolMapper.updateByTMdSchool(tMdSchool);
	}

}
