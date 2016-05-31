package com.nhry.data.dao;

import com.nhry.domain.TMdBranchEmp;

public interface TMdBranchEmpMapper {
    int deleteBranchEmpByNo(String empNo);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
}