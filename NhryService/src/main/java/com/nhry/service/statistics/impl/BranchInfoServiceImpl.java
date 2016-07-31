package com.nhry.service.statistics.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.statistics.dao.BranchInfoMapper;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.service.statistics.dao.BranchInfoService;
import org.apache.commons.lang.StringUtils;
import scala.reflect.internal.Trees;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/18/2016.
 */
public class BranchInfoServiceImpl implements BranchInfoService {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private BranchInfoMapper branchInfoMapper;

    public void setBranchInfoMapper(BranchInfoMapper branchInfoMapper) {
        this.branchInfoMapper = branchInfoMapper;
    }

    @Override
    public PageInfo branchDayInfo(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }

        return branchInfoMapper.branchDayInfo(model);
    }

    @Override
    public PageInfo findOrderRatio(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }

        return branchInfoMapper.findOrderRatio(model);
    }

    @Override
    public PageInfo findBranchMonthReport(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return branchInfoMapper.findBranchMonthReport(model);
    }

    @Override
    public PageInfo findReqOrder(BranchInfoModel model) {
        return branchInfoMapper.findReqOrder(model);
    }
    @Override
   public List<Map<String,String>> findReqOrderOutput(BranchInfoModel model){
        return branchInfoMapper.findReqOrderOutput(model);
    }

    @Override
    public List<Map<String,String>> branchDayOutput(BranchInfoModel model){
        return branchInfoMapper.branchDayOutput(model);
    }

    @Override
    public List<Map<String,String>> findBranchMonthReportOutput(BranchInfoModel model){
        return branchInfoMapper.findBranchMonthReportOutput(model);
    }

    @Override
    public List<Map<String,String>> findOrderRatioOutput(BranchInfoModel model){
        return branchInfoMapper.findOrderRatioOutput(model);
    }
}
