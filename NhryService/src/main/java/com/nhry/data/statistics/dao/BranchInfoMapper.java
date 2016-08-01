package com.nhry.data.statistics.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.statistics.BranchInfoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/18/2016.
 */
public interface BranchInfoMapper {
    PageInfo branchDayInfo(BranchInfoModel model);
    Map<String,String> getBranchs(BranchInfoModel model);
    List<Map<String,String>> branchDayInfoList(BranchInfoModel model);
    PageInfo findOrderRatio(BranchInfoModel model);
    PageInfo findBranchMonthReport(BranchInfoModel model);
    PageInfo findReqOrder(BranchInfoModel model);
    List<Map<String,String>> findReqOrderOutput(BranchInfoModel model);
    List<Map<String,String>> branchDayOutput(BranchInfoModel model);
    List<Map<String,String>> findBranchMonthReportOutput(BranchInfoModel model);
    List<Map<String,String>> findOrderRatioOutput(BranchInfoModel model);
}
