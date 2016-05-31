package com.nhry.data.dao;

import java.util.List;

import com.nhry.domain.TMdBranch;

public interface TMdBranchMapper {
    int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    int updateBranchByNo(TMdBranch record);
    
    public List<TMdBranch> findBranchListByOrg(String salesOrg);
}