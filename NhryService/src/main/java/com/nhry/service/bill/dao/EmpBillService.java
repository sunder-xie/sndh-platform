package com.nhry.service.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gongjk on 2016/7/1.
 */
public interface EmpBillService {
    public PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch);

    public List<EmpAccoDispFeeByProduct> empAccoDispFeeByProduct(EmpDispDetialInfoSearch eSearch);

    PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch);

    public BigDecimal CalculateEmpTransRateByNum(String empNo,int dispNum);

    public BigDecimal CalculateEmpTransFee(EmpDispDetialInfoSearch eSearch,String type);

    PageInfo empSalaryRep(EmpDispDetialInfoSearch eSearch);

    int uptEmpDispRate(SaleOrgDispRateModel sModel);

   public SaleOrgDispRateModel getSalesOrgDispRate();
}
