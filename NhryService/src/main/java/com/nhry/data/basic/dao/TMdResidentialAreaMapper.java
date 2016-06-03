package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdResidentialArea;

import java.util.List;

public interface TMdResidentialAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMdResidentialArea record);

    int insertSelective(TMdResidentialArea record);

   public  TMdResidentialArea selectById(String id);

    int updateByPrimaryKeySelective(TMdResidentialArea record);

    int updateByPrimaryKey(TMdResidentialArea record);

    public  List<TMdResidentialArea> getAreaByBranchNo(String branchNo);
}