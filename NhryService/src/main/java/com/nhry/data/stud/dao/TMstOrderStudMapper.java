package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMstOrderStud;

public interface TMstOrderStudMapper {
    int deleteByPrimaryKey(String orderId);

    int insertSelective(TMstOrderStud record);

    TMstOrderStud selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(TMstOrderStud record);

    int updateByPrimaryKey(TMstOrderStud record);
}