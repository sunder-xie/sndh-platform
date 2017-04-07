package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdMaraStudEx;
import com.nhry.data.stud.domain.TMdMaraStudExKey;

public interface TMdMaraStudExMapper {
    int deleteByPrimaryKey(TMdMaraStudExKey key);

    int insert(TMdMaraStudEx record);

    int insertSelective(TMdMaraStudEx record);

    TMdMaraStudEx selectByPrimaryKey(TMdMaraStudExKey key);

    int updateByPrimaryKeySelective(TMdMaraStudEx record);

    int updateByPrimaryKey(TMdMaraStudEx record);
}