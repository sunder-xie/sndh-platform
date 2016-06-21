package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.service.basic.pojo.BranchEmpModel;

public interface BranchEmpService {
	int deleteBranchEmpByNo(String empNo);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(EmpQueryModel smodel);

    BranchEmpModel empDetailInfo(String empNo);
}
