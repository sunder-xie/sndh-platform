package com.nhry.data.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.bill.BranchBillEmpItemModel;
import com.nhry.model.bill.BranchBillSearch;

import java.util.List;

/**
 * Created by gongjk on 2016/6/27.
 */
public interface BranchBillMapper {
    public PageInfo branchBillSearch(BranchBillSearch bsearch);

    public PageInfo branchBillEmpSearch(BranchBillSearch bsearch);

    List<BranchBillEmpItemModel> branchBillEmpItemSearch(BranchBillSearch bsearch);
}
