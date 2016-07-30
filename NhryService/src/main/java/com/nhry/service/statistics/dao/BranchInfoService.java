package com.nhry.service.statistics.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.statistics.BranchInfoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/18/2016.
 */
public interface BranchInfoService {
    PageInfo branchDayInfo(BranchInfoModel model);
    PageInfo findOrderRatio(BranchInfoModel model);
    PageInfo findBranchMonthReport(BranchInfoModel model);
    PageInfo findReqOrder(BranchInfoModel model);
    List<Map<String,String>> findReqOrderOutput(BranchInfoModel model);
    List<Map<String,String>> branchDayOutput(BranchInfoModel model);
}
