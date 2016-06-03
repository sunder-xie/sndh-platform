package com.nhry.service.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.TMdBranchMapper;
import com.nhry.domain.TMdBranch;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.pojo.query.BranchQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.dao.BranchService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
		return  branchMapper.selectBranchByNo(branchNo);
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

	@Override
	public PageInfo findBranchListByPage(BranchQueryModel branchModel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(branchModel.getPageNum()) || StringUtils.isEmpty(branchModel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return branchMapper.findBranchListByPage(branchModel);
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}

}
