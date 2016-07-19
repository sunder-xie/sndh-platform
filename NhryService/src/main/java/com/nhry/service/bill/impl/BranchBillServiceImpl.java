package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.data.bill.dao.BranchBillMapper;
import com.nhry.model.bill.CustBranchBillSearch;
import com.nhry.model.bill.EmpBranchBillDetailSearch;
import com.nhry.model.bill.EmpBranchBillSearch;
import com.nhry.service.bill.dao.BranchBillService;

/**
 * Created by gongjk on 2016/6/27.
 */
public class BranchBillServiceImpl implements BranchBillService {
    private BranchBillMapper branchBillMapper;
    private UserSessionService userSessionService;
    private TSysUserRoleMapper tSysUserRoleMapper;

    public void setBranchBillMapper(BranchBillMapper branchBillMapper) {
        this.branchBillMapper = branchBillMapper;
    }

    public void settSysUserRoleMapper(TSysUserRoleMapper tSysUserRoleMapper) {
        this.tSysUserRoleMapper = tSysUserRoleMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }


    @Override
    public PageInfo EmpBranchBill(EmpBranchBillSearch eSearch) {
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole = tSysUserRoleMapper.getUserRoleByLoginName(user.getLoginName());
        if("1004".equals(userRole.getId())){
            eSearch.setBranchNo(user.getBranchNo());
        }

        PageInfo data = branchBillMapper.branchBillEmpSearch(eSearch);
        return data;
    }

    @Override
    public PageInfo getEmpBranchBillDetail(EmpBranchBillDetailSearch bsearch) {
        TSysUser user = userSessionService.getCurrentUser();
        bsearch.setSalesOrg(user.getSalesOrg());
        TSysUserRole userRole = tSysUserRoleMapper.getUserRoleByLoginName(user.getLoginName());
        if("1004".equals(userRole.getId())){
            bsearch.setBranchNo(user.getBranchNo());
        }
        return branchBillMapper.branchBillEmpItemSearch(bsearch);
    }

    @Override
    public PageInfo CustomerBranchBill(CustBranchBillSearch bsearch) {
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole = tSysUserRoleMapper.getUserRoleByLoginName(user.getLoginName());
        if("1004".equals(userRole.getId())){
            bsearch.setBranchNo(user.getBranchNo());
        }
        return branchBillMapper.CustomerBranchBill(bsearch);
    }
}
