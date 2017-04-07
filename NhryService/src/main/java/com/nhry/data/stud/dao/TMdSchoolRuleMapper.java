package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdSchoolRule;

public interface TMdSchoolRuleMapper {
    int deleteByPrimaryKey(String mid);

    int insert(TMdSchoolRule record);

    int insertSelective(TMdSchoolRule record);

    TMdSchoolRule selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMdSchoolRule record);

    int updateByPrimaryKey(TMdSchoolRule record);
}