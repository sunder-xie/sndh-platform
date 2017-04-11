package com.nhry.data.stud.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.model.stud.SchoolQueryModel;

public interface TMdSchoolMapper {
	
	
	PageInfo<TMdSchool>  serchSchoolList(SchoolQueryModel model);
	
	int updateByTMdSchool(TMdSchool tMdSchool);
	
	List<TMdSchool>  findSchoolList(SchoolQueryModel model);

	TMdSchool selectByPrimaryKey(SchoolQueryModel model);

}