package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchService;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BranchServiceImpl extends BaseService implements BranchService {
     private TMdBranchMapper branchMapper;
	private UserSessionService userSessionService;
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
	public int updateBranch(TMdBranch tMdBranch) {
		return branchMapper.updateBranch(tMdBranch);
	}

	@Override
	public List<TMdBranch> findBranchListByOrg() {
		// TODO Auto-generated method stub
		TSysUser user = userSessionService.getCurrentUser();
		return branchMapper.findBranchListByOrg(user.getSalesOrg());
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

	@Override
	public void setUserSessionService(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}
}
