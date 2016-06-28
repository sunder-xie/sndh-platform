package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.data.bill.dao.BranchBillMapper;
import com.nhry.model.bill.BranchBillSearch;
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
    public PageInfo branchBill(BranchBillSearch bsearch) {
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole = tSysUserRoleMapper.getUserRoleByLoginName(user.getLoginName());
        //部门内勤
        if("1003".equals(userRole.getId())){
            return branchBillMapper.branchBillSearch(bsearch);
        }
        //奶站内勤
        if("1001".equals(userRole.getId())){
            if("customer".equals(bsearch.getDimenType())){
                return branchBillMapper.branchBillSearch(bsearch);
            }else{
                return branchBillMapper.branchBillEmpSearch(bsearch);
            }

        }
        return null;
    }
}
