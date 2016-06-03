package com.nhry.data.basic.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.model.basic.EmpQueryModel;

public interface TMdBranchEmpMapper {
    int deleteBranchEmp(TMdBranchEmp record);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(EmpQueryModel smodel);
}