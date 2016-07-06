package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TMdDealerMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.model.basic.BranchOrDealerList;
import com.nhry.model.basic.BranchQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchService;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BranchServiceImpl extends BaseService implements BranchService {
     private TMdBranchMapper branchMapper;
	private TMdDealerMapper dealerMapper;
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

	@Override
	public BranchOrDealerList findResultByType(String type) {
		TSysUser user = userSessionService.getCurrentUser();
		BranchOrDealerList list = new BranchOrDealerList();
		if("自营".equals(type)){
			List<TMdBranch> branchList = branchMapper.findBranchListByOrg(user.getSalesOrg());
			list.setBranchList(branchList);
		}
		if("外包".equals(type)){
			List<TMdDealer> dealerList = dealerMapper.findDealersBySalesOrg(user.getSalesOrg());
		}
		return list;
	}


	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}

	@Override
	public void setUserSessionService(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}

	public void setDealerMapper(TMdDealerMapper dealerMapper) {
		this.dealerMapper = dealerMapper;
	}
}
