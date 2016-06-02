package com.nhry.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.TMdBranchEmpMapper;
import com.nhry.domain.TMdBranchEmp;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.pojo.query.EmpQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.dao.BranchEmpService;
import com.nhry.utils.Date;

public class BranchEmpServiceImpl extends BaseService implements BranchEmpService {
	
	private TMdBranchEmpMapper branchEmpMapper;

	@Override
	public int deleteBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		TMdBranchEmp emp = this.selectBranchEmpByNo(empNo);
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		TMdBranchEmp record = new TMdBranchEmp();
		record.setDelFlag("Y");
		record.setEmpNo(empNo);
		record.setLastModified(new Date());
		record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
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
		record.setCreateBy(userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());
		record.setDelFlag("N");
		return branchEmpMapper.addBranchEmp(record);
	}

	@Override
	public TMdBranchEmp selectBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		return branchEmpMapper.selectBranchEmpByNo(empNo);
	}

	@Override
	public int uptBranchEmpByNo(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		TMdBranchEmp emp = this.selectBranchEmpByNo(record.getEmpNo());
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		record.setLastModified(new Date());
		record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
		return branchEmpMapper.uptBranchEmpByNo(record);
	}

	public void setBranchEmpMapper(TMdBranchEmpMapper branchEmpMapper) {
		this.branchEmpMapper = branchEmpMapper;
	}

	@Override
	public PageInfo searchBranchEmp(EmpQueryModel smodel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return branchEmpMapper.searchBranchEmp(smodel);
	}
}
