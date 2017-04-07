package com.nhry.data.stud.dao;

import com.nhry.data.stud.domain.TMstOrderStudItem;

public interface TMstOrderStudItemMapper {
    int deleteByPrimaryKey(String mid);

    int insert(TMstOrderStudItem record);

    int insertSelective(TMstOrderStudItem record);

    TMstOrderStudItem selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(TMstOrderStudItem record);

    int updateByPrimaryKey(TMstOrderStudItem record);
}