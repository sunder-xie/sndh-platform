package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMstOrderStudLoss;

public interface TMstOrderStudLossMapper {
    int deleteByPrimaryKey(String mid);

    int insertSelective(TMstOrderStudLoss record);

    TMstOrderStudLoss selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMstOrderStudLoss record);

    int updateByPrimaryKey(TMstOrderStudLoss record);
}