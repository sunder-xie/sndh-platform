package com.nhry.data.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.EmpAccountSearch;
import com.nhry.model.bill.EmpDispDetialInfoSearch;

/**
 * Created by gongjk on 2016/7/1.
 */
public interface EmpBillMapper {
    PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch);

    PageInfo empAccountReceAmount(EmpAccountSearch eSearch);
}
