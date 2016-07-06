package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdMaraUnit;
import com.nhry.data.basic.domain.TMdMaraUnitKey;

public interface TMdMaraUnitMapper {
    int deleteByPrimaryKey(TMdMaraUnitKey key);

    int insert(TMdMaraUnit record);

    int insertSelective(TMdMaraUnit record);

    TMdMaraUnit selectByPrimaryKey(TMdMaraUnitKey key);

    int updateByPrimaryKeySelective(TMdMaraUnit record);

    int updateByPrimaryKey(TMdMaraUnit record);
}