package com.nhry.service.impl;

import java.util.List;

import com.nhry.data.dao.TMdBranchMapper;
import com.nhry.domain.TMdBranch;
import com.nhry.service.BaseService;
import com.nhry.service.dao.BranchService;

public class BranchServiceImpl extends BaseService implements BranchService {
     private TMdBranchMapper branchMapper;
	@Override
	public int deleteBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addBranch(TMdBranch record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TMdBranch selectBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateBranchByNo(TMdBranch record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TMdBranch> findBranchListByOrg(String salesOrg) {
		// TODO Auto-generated method stub
		return branchMapper.findBranchListByOrg(salesOrg);
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}

}
