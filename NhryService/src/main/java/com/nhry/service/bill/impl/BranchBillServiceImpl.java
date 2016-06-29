package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.bill.dao.BranchBillMapper;
import com.nhry.model.bill.BranchBillEmpItemModel;
import com.nhry.model.bill.BranchBillEmpModel;
import com.nhry.model.bill.BranchBillSearch;
import com.nhry.service.bill.dao.BranchBillService;

import java.util.List;

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
       /* TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole = tSysUserRoleMapper.getUserRoleByLoginName(user.getLoginName());
        //部门内勤 查看所有奶站的结算报表
        if("1003".equals(userRole.getId())){
            bsearch.setBranchNo(null);
        }*/

        if("customer".equals(bsearch.getDimenType())){
            return branchBillMapper.branchBillSearch(bsearch);
        }else{
            PageInfo date = branchBillMapper.branchBillEmpSearch(bsearch);
            List<BranchBillEmpModel> result =  date.getList();
            for(BranchBillEmpModel empModel : result) {
                bsearch.setEmpNo(empModel.getEmpNo());
                List<BranchBillEmpItemModel> entry = branchBillMapper.branchBillEmpItemSearch(bsearch);
                empModel.setEntries(entry);
            }
            return date;
        }
    }
}
