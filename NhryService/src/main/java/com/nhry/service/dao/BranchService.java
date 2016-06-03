package com.nhry.service.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdBranch;
import com.nhry.pojo.query.BranchQueryModel;

import java.util.List;

public interface BranchService {
	int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    int updateBranchByNo(TMdBranch record);
    
    public List<TMdBranch> findBranchListByOrg(String salesOrg);


    public PageInfo findBranchListByPage(BranchQueryModel branchModel);
}
