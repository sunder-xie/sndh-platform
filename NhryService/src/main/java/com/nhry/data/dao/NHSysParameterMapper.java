package com.nhry.data.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.NHSysParameter;
import com.nhry.pojo.query.SysParamQueryModel;

public interface NHSysParameterMapper {
    int deleteSysParamByCode(String paramCode);

    int addSysParam(NHSysParameter record);

    NHSysParameter selectSysParamByCode(String paramCode);

    int uptSysParamByCode(NHSysParameter record);
    
    public PageInfo findSysParam(SysParamQueryModel param);
}