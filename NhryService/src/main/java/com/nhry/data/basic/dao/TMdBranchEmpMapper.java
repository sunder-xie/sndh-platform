package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.model.basic.BranchEmpSearchModel;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.model.bill.EmpSalQueryModel;
import com.nhry.service.basic.pojo.BranchEmpModel;

import java.util.List;

public interface TMdBranchEmpMapper {
    int deleteBranchEmp(TMdBranchEmp record);

    int addBranchEmp(TMdBranchEmp record);

    TMdBranchEmp selectBranchEmpByNo(String empNo);
    
    /**
     * 查询有效的员工
     * @param empNo
     * @return
     */
    TMdBranchEmp selectActiveBranchEmpByNo(String empNo);

    BranchEmpModel empDetailInfo(String empNo);

    int uptBranchEmpByNo(TMdBranchEmp record);
    
    public PageInfo searchBranchEmp(EmpQueryModel smodel);

    TMdBranchEmp selectBranchEmpByEmpNo(String empNo);

    List<TMdBranchEmp> getAllEmpBySalesOrg(String salesOrg);

    List<TMdBranchEmp> getAllEmpMilkManByBranchNo(String branchNo,String salesOrg);

    List<TMdBranchEmp> getAllBranchEmpByNo(BranchEmpSearchModel bModel);

    List<TMdBranchEmp> getAllRationalMilkManByBranchNo(EmpSalQueryModel smodel);
    
    public int uptBranchEmpByBraNo(TMdBranchEmp record);
}