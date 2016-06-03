package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchQueryModel;

import java.util.List;

public interface BranchService {
	int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    int updateBranchByNo(TMdBranch record);
    
    public List<TMdBranch> findBranchListByOrg(String salesOrg);


    public PageInfo findBranchListByPage(BranchQueryModel branchModel);
}
