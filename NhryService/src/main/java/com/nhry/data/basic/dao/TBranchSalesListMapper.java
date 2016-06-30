package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TBranchSalesList;

public interface TBranchSalesListMapper {
    int delBranchSalesByNo(String listNo);

    int addBranchSales(TBranchSalesList record);

    TBranchSalesList findBranchSalesByNo(String listNo);

    int uptBranchSales(TBranchSalesList record);
    
    int delSalesListByMatnr(String matnr);
}