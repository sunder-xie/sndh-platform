package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchOrDealerList;
import com.nhry.model.basic.BranchQueryModel;

import java.util.List;

public interface BranchService {
	int deleteBranchByNo(String branchNo);

    int addBranch(TMdBranch record);

    TMdBranch selectBranchByNo(String branchNo);

    int updateBranch(TMdBranch record);
    
    public List<TMdBranch> findBranchListByOrg();


    public PageInfo findBranchListByPage(BranchQueryModel branchModel);

   public BranchOrDealerList findResultByType(String type);

    List<TMdBranch> getBranchByCodeOrName(String branch);
    
    
    /**
     * 根据经销商编号、当前销售组织查询对应的奶站
     * @param dealerNo
     * @return
     */
    List<TMdBranch> findBranchByDno(String dealerNo);

    TMdBranch getCustBranchInfo();
}
