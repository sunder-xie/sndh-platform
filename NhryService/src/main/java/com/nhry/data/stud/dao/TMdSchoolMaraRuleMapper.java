package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdSchoolMaraRule;

public interface TMdSchoolMaraRuleMapper {
    int deleteByPrimaryKey(String mid);

    int insertSelective(TMdSchoolMaraRule record);

    TMdSchoolMaraRule selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMdSchoolMaraRule record);

    int updateByPrimaryKey(TMdSchoolMaraRule record);
}