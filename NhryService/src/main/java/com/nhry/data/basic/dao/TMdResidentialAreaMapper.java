package com.nhry.data.basic.dao;


import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.model.basic.BranchAreaSearch;
import com.nhry.service.basic.pojo.ResidentialAreaModel;

import java.util.List;

public interface TMdResidentialAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMdResidentialArea record);

    int insertSelective(TMdResidentialArea record);

    public  TMdResidentialArea selectById(String id);

    int updateByPrimaryKeySelective(TMdResidentialArea record);

    int updateByPrimaryKey(TMdResidentialArea record);

    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo) ;

    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel);

    public  int addResidentialArea(TMdResidentialArea tMdResidentialArea);

    int uptResidentialArea(TMdResidentialArea tMdResidentialArea);

    int deleteAreaById(String id);

    PageInfo searchAreaByBranchNo(BranchAreaSearch bSearch);

    int updateStatusToUnDistById(String residentialAreaId);

    TMdResidentialArea getAreaById(String id);

    int updateStatusToDistedById(String id);

    List<TMdResidentialArea> getUnDistAreas();
}