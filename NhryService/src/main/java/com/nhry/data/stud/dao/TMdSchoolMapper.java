package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdSchool;

public interface TMdSchoolMapper {
    int deleteByPrimaryKey(String schoolCode);

    int insert(TMdSchool record);

    int insertSelective(TMdSchool record);

    TMdSchool selectByPrimaryKey(String schoolCode);

    int updateByPrimaryKeySelective(TMdSchool record);

    int updateByPrimaryKey(TMdSchool record);
}