package com.nhry.data.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdBranchEmp;
import com.nhry.domain.model.SearchModel;

public interface TMdBranchEmpMapper {
    int deleteBranchEmp(TMdBranchEmp record);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(SearchModel smodel);
}