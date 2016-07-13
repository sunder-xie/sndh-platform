package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.service.basic.pojo.BranchEmpModel;

import java.util.List;

public interface TMdBranchEmpMapper {
    int deleteBranchEmp(TMdBranchEmp record);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    BranchEmpModel empDetailInfo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(EmpQueryModel smodel);

    TMdBranchEmp selectBranchEmpByEmpNo(String empNo);

    List<TMdBranchEmp> getAllEmpByBranchNo(String branchNo,String salesOrg);
}