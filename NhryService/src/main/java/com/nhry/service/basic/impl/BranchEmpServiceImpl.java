package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.model.basic.BranchEmpSearchModel;
import com.nhry.model.basic.BranchSalesOrgModel;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchEmpService;
import com.nhry.service.basic.pojo.BranchEmpModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BranchEmpServiceImpl extends BaseService implements BranchEmpService {
	
	private TMdBranchEmpMapper branchEmpMapper;
	private TMdBranchMapper tMdBranchMapper;
	private UserSessionService userSessionService;
	private TSysUserRoleMapper userRoleMapper;



	@Override
	public int deleteBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		TMdBranchEmp emp = this.selectBranchEmpByNo(empNo);
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		TMdBranchEmp record = new TMdBranchEmp();
		record.setStatus("0");
		record.setEmpNo(empNo);
		record.setLastModified(new Date());
		/*record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());*/

		return branchEmpMapper.deleteBranchEmp(record);
	}

	@Override
	public int addBranchEmp(TMdBranchEmp record) {
		if(StringUtils.isEmpty(record.getEmpNo()) || StringUtils.isEmpty(record.getEmpName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "员工编号、员工姓名不能为空！");
		}
		TMdBranchEmp emp = selectBranchEmpByNo(record.getEmpNo());
		if(emp != null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号已存在，请重新填写！");
		}
		record.setCreateAt(new Date());
//		record.setCreateBy(userSessionService.getCurrentUser().getLoginName());
//		record.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());
		record.setStatus("1");
		return branchEmpMapper.addBranchEmp(record);
	}

	@Override
	public TMdBranchEmp selectBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		return branchEmpMapper.selectBranchEmpByNo(empNo);
	}


	@Override
	public BranchEmpModel empDetailInfo(String empNo) {
		return branchEmpMapper.empDetailInfo(empNo);
	}

	@Override
	public List<TMdBranch> getComPanyAllBranch() {
		TSysUser user = userSessionService.getCurrentUser();
		String salesOrg = user.getSalesOrg();
		String branchNo = user.getBranchNo();
		if(StringUtils.isBlank(salesOrg)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "登录用户所在销售组织不存在!");
		}
		if(StringUtils.isNoneBlank(branchNo)){
			List<TMdBranch> branchlist = new ArrayList<TMdBranch>();
			TMdBranch branch = tMdBranchMapper.selectBranchByNo(branchNo);
			branchlist.add(branch);
			return branchlist ;
		}else{
			BranchSalesOrgModel bModel = new BranchSalesOrgModel();
			bModel.setSalesOrg(salesOrg);
			return tMdBranchMapper.findBranchListByOrg(bModel);
		}

	}

	@Override
	public List<TMdBranchEmp> getAllEmpByBranchNo(String branchNo) {
		if(StringUtils.isBlank(branchNo)){
			branchNo = userSessionService.getCurrentUser().getBranchNo();
		}
		String salesOrg = userSessionService.getCurrentUser().getSalesOrg();
		return branchEmpMapper.getAllEmpByBranchNo(branchNo,salesOrg);
	}

	@Override
	public List<TMdBranchEmp> getAllEmpBySalesOrg() {
		TSysUser user = userSessionService.getCurrentUser();

		return branchEmpMapper.getAllEmpBySalesOrg(user.getSalesOrg());
	}

	@Override
	public List<TMdBranchEmp> getAllBranchEmpByNo(BranchEmpSearchModel bModel) {
		TSysUser user = userSessionService.getCurrentUser();
		TSysUserRole userRole = userRoleMapper.getUserRoleByLoginName(user.getLoginName());
		bModel.setSalesOrg(user.getSalesOrg());
		if("10004".equals(userRole.getId())){
			bModel.setBranchNo(user.getBranchNo());
		}
		if("10005".equals(userRole.getId())){
			bModel.setDealerNo(user.getDealerId());
		}

		return branchEmpMapper.getAllBranchEmpByNo(bModel);
	}

	@Override
	public int uptBranchEmpByNo(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		TMdBranchEmp emp = this.selectBranchEmpByNo(record.getEmpNo());
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		record.setLastModified(new Date());
		/*record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());*/
		return branchEmpMapper.uptBranchEmpByNo(record);
	}

	public void setBranchEmpMapper(TMdBranchEmpMapper branchEmpMapper) {
		this.branchEmpMapper = branchEmpMapper;
	}

	public void settMdBranchMapper(TMdBranchMapper tMdBranchMapper) {
		this.tMdBranchMapper = tMdBranchMapper;
	}

	public void setUserRoleMapper(TSysUserRoleMapper userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
	}

	@Override
	public void setUserSessionService(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}

	@Override
	public PageInfo searchBranchEmp(EmpQueryModel smodel) {
		TSysUser user = userSessionService.getCurrentUser();
		smodel.setSalesOrg(user.getSalesOrg());

		if(StringUtils.isBlank(smodel.getBranchNo())){
			TSysUserRole userRole = userRoleMapper.getUserRoleByLoginName(user.getLoginName());
			if("10003".equals(userRole.getId())){
				smodel.setBranchNo("");
			}else{
				smodel.setBranchNo(user.getBranchNo());
			}
		}


		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return branchEmpMapper.searchBranchEmp(smodel);
	}


}
