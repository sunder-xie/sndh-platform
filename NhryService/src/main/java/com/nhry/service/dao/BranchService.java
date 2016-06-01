package com.nhry.service.dao;

import java.util.List;

import com.nhry.domain.TMdBranch;

public interface BranchService {
	int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    int updateBranchByNo(TMdBranch record);
    
    public List<TMdBranch> findBranchListByOrg(String salesOrg);
}
