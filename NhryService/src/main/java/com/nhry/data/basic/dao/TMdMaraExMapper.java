package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdMaraEx;

public interface TMdMaraExMapper {

    int uptProductExByCode(TMdMaraEx record);
    
    int addMaraEx(TMdMaraEx record);
}