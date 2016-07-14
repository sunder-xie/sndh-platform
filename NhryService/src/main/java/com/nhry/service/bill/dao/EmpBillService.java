package com.nhry.service.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.EmpDispDetialInfoSearch;
import com.nhry.model.bill.SaleOrgDispRateModel;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/7/1.
 */
public interface EmpBillService {
    public PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch);

    PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch);

    public BigDecimal CalculateEmpTransRateByNum(String empNo,int dispNum);

    int uptEmpDispRate(SaleOrgDispRateModel sModel);

   public SaleOrgDispRateModel getSalesOrgDispRate();

    PageInfo searchEmpSalaryRep(EmpDispDetialInfoSearch eSearch);

    int setBranchEmpSalary();
}
