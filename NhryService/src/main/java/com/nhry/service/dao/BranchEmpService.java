package com.nhry.service.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdBranchEmp;
import com.nhry.domain.model.SearchModel;

public interface BranchEmpService {
	int deleteBranchEmpByNo(String empNo);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(SearchModel smodel);
}
