package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchQueryModel;

import java.util.List;

public interface TMdBranchMapper {
    int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    public List<TMdBranch> findBranchListByOrg(String salesOrg);


    public PageInfo findBranchListByPage(BranchQueryModel branchModel);

    int updateBranch(TMdBranch tMdBranch);
}