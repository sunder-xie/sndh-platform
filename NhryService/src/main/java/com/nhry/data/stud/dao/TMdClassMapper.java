package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdClass;

public interface TMdClassMapper {
    int deleteByPrimaryKey(String classCode);

    int insert(TMdClass record);

    int insertSelective(TMdClass record);

    TMdClass selectByPrimaryKey(String classCode);

    int updateByPrimaryKeySelective(TMdClass record);

    int updateByPrimaryKey(TMdClass record);
}