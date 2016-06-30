package com.nhry.service.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.BranchBillSearch;

/**
 * Created by gongjk on 2016/6/27.
 */
public interface BranchBillService {
    PageInfo branchBill(BranchBillSearch bsearch);
}
