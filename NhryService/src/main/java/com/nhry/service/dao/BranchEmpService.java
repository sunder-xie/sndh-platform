package com.nhry.service.dao;

import com.nhry.domain.TMdBranchEmp;

public interface BranchEmpService {
	int deleteBranchEmpByNo(String empNo);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
}
