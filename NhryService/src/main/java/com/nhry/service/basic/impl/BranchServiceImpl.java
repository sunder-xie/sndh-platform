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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		TSysUser user = userSessionService.getCurrentUser();
		branchModel.setSalesOrg(user.getSalesOrg());
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
		if("01".equals(type)){
			List<TMdBranch> branchList = branchMapper.findBranchListByOrgAndAuto(user.getSalesOrg());
			list.setBranchList(branchList);
		}
		if("02".equals(type)){
			List<TMdDealer> dealerList = dealerMapper.findDealersBySalesOrg(user.getSalesOrg());
			list.setDealerList(dealerList);
		}
		return list;
	}

	@Override
	public List<TMdBranch> getBranchByCodeOrName(String branch) {
		TSysUser user = userSessionService.getCurrentUser();
		Map<String,String>  map = new HashMap<String,String>();
		map.put("branchNo",branch);
		map.put("branchName",branch);
		map.put("salesOrg",user.getSalesOrg());

		return branchMapper.getBranchByCodeOrName(map);
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

	@Override
	public List<TMdBranch> findBranchByDno(String dealerNo) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser().getSalesOrg());
		attrs.put("dealerNo",dealerNo);
		return this.branchMapper.findBranchByDno(attrs);
	}
}
