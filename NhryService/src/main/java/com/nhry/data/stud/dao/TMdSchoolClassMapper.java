package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdSchoolClass;

public interface TMdSchoolClassMapper {
    int deleteByPrimaryKey(String mid);

    int insertSelective(TMdSchoolClass record);

    TMdSchoolClass selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMdSchoolClass record);

    int updateByPrimaryKey(TMdSchoolClass record);
}