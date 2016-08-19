package com.nhry.service.statistics.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.statistics.ExtendBranchInfoModel;

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
    List<Map<String,String>> findBranchMonthReportOutput(BranchInfoModel model);
    List<Map<String,String>> findOrderRatioOutput(BranchInfoModel model);
    PageInfo findChangeplanStatReport(ExtendBranchInfoModel model);
    PageInfo returnBoxStatReport(ExtendBranchInfoModel model);
    PageInfo mstDispNumStat(ExtendBranchInfoModel model);
    PageInfo branchMstDispNumStat(ExtendBranchInfoModel model);
    PageInfo dayMstDispNumStat(ExtendBranchInfoModel model);
    List<Map<String,String>> branchDayRepo(BranchInfoModel model);
    Map<String,String> branchDayQty(BranchInfoModel model);
}
