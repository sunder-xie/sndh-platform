package com.nhry.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.nhry.data.dao.TMdBranchEmpMapper;
import com.nhry.domain.TMdBranchEmp;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.BranchEmpService;

public class BranchEmpServiceImpl extends BaseService implements
		BranchEmpService {
	
	private TMdBranchEmpMapper branchEmpMapper;

	@Override
	public int deleteBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		TMdBranchEmp emp = this.selectBranchEmpByNo(empNo);
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		return branchEmpMapper.deleteBranchEmpByNo(empNo);
	}

	@Override
	public int addBranchEmp(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getBranchNo()) || StringUtils.isEmpty(record.getEmpName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "员工编号、员工姓名不能为空！");
		}
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
		TMdBranchEmp emp = this.selectBranchEmpByNo(record.getBranchNo());
		if(emp == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该员工编号对应的员工信息不存在!");
		}
		return branchEmpMapper.uptBranchEmpByNo(record);
	}

	public void setBranchEmpMapper(TMdBranchEmpMapper branchEmpMapper) {
		this.branchEmpMapper = branchEmpMapper;
	}

}
