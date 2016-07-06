package com.nhry.data.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.EmpAccoDispFeeByProduct;
import com.nhry.model.bill.EmpDispDetialInfoSearch;

import java.util.List;

/**
 * Created by gongjk on 2016/7/1.
 */
public interface EmpBillMapper {
    PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch);

    PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch);

    PageInfo empSalaryRep(EmpDispDetialInfoSearch eSearch);

    List<EmpAccoDispFeeByProduct> empAccoDispFeeByProduct(EmpDispDetialInfoSearch eSearch);

    int empAccoDispFeeByNum(EmpDispDetialInfoSearch eSearch);

}
