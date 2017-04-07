package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMdMaraStud;
import com.nhry.data.stud.domain.TMdMaraStudKey;

public interface TMdMaraStudMapper {
    int deleteByPrimaryKey(TMdMaraStudKey key);

    int insert(TMdMaraStud record);

    int insertSelective(TMdMaraStud record);

    TMdMaraStud selectByPrimaryKey(TMdMaraStudKey key);

    int updateByPrimaryKeySelective(TMdMaraStud record);

    int updateByPrimaryKey(TMdMaraStud record);
}