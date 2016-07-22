package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TMdDealerMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.model.basic.BranchOrDealerList;
import com.nhry.model.basic.BranchQueryModel;
import com.nhry.model.basic.BranchSalesOrgModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.TSysMessageService;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchServiceImpl extends BaseService implements BranchService {
    private TMdBranchMapper branchMapper;
	private TMdDealerMapper dealerMapper;
	private UserSessionService userSessionService;
	private TSysUserRoleMapper urMapper;
	

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
		if(StringUtils.isEmpty(tMdBranch.getBranchNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "奶站编号不能为空!");
		}
		TMdBranch branch = this.branchMapper.selectBranchByNo(tMdBranch.getBranchNo());
		if(branch == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站编号对应的奶站信息不存在!");
		}
		return branchMapper.updateBranch(tMdBranch);
	}

	@Override
	public List<TMdBranch> findBranchListByOrg() {
		// TODO Auto-generated method stub
		TSysUser user = userSessionService.getCurrentUser();
		TSysUserRole userRole = urMapper.getUserRoleByLoginName(user.getLoginName());
		BranchSalesOrgModel bModel = new BranchSalesOrgModel();
		bModel.setSalesOrg(user.getSalesOrg());
		if("10004".equals(userRole.getId())){
			bModel.setBranchNo(user.getBranchNo());
		}else if("10005".equals(userRole.getId())){
			//经销商内勤
			bModel.setDealerNo(user.getDealerId());
		}
		return branchMapper.findBranchListByOrg(bModel);
	}

	@Override
	public PageInfo findBranchListByPage(BranchQueryModel branchModel) {
		TSysUser user = userSessionService.getCurrentUser();
		TSysUserRole userRole = urMapper.getUserRoleByLoginName(user.getLoginName());
		branchModel.setSalesOrg(user.getSalesOrg());
		//部门内勤
		if("10003".equals(userRole.getId())){
			branchModel.setRoleId("10003");
		}else if("10005".equals(userRole.getId())){
			branchModel.setRoleId("10005");
			//经销商内勤
			branchModel.setDealerNo(user.getDealerId());
		}else {
			//奶站内勤
			branchModel.setBranchNo(user.getBranchNo());

		}

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

	public void setUrMapper(TSysUserRoleMapper urMapper) {
		this.urMapper = urMapper;
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
