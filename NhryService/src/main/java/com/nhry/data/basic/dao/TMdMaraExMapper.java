package com.nhry.data.basic.dao;

import java.util.Map;

import com.nhry.data.basic.domain.TMdMaraEx;

public interface TMdMaraExMapper {

    int uptProductExByCode(TMdMaraEx record);
    
    int addMaraEx(TMdMaraEx record);
    
    TMdMaraEx findProductExByCode(Map<String,String> attrs);
}