package com.nhry.service.statistics.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.statistics.dao.BranchInfoMapper;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.statistics.ExtendBranchInfoModel;
import com.nhry.service.statistics.dao.BranchInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.method.P;
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
    private UserSessionService userSessionService;
    public void setBranchInfoMapper(BranchInfoMapper branchInfoMapper) {
        this.branchInfoMapper = branchInfoMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Override
    public PageInfo branchDayInfo(BranchInfoModel model) {

        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.branchDayInfo(model);
    }

    @Override
    public PageInfo findOrderRatio(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findOrderRatio(model);
    }

    @Override
    public PageInfo findBranchMonthReport(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findBranchMonthReport(model);
    }

    @Override
    public PageInfo findReqOrder(BranchInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findReqOrder(model);
    }
    @Override
   public List<Map<String,String>> findReqOrderOutput(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findReqOrderOutput(model);
    }

    @Override
    public List<Map<String,String>> branchDayOutput(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.branchDayOutput(model);
    }

    @Override
    public List<Map<String,String>> findBranchMonthReportOutput(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findBranchMonthReportOutput(model);
    }

    @Override
    public List<Map<String,String>> findOrderRatioOutput(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findOrderRatioOutput(model);
    }
    @Override
    public PageInfo findChangeplanStatReport(ExtendBranchInfoModel model){

        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.findChangeplanStatReport(model);
    }
    @Override
    public PageInfo returnBoxStatReport(ExtendBranchInfoModel model){
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.returnBoxStatReport(model);
    }
    @Override
    public PageInfo mstDispNumStat(ExtendBranchInfoModel model){
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.mstDispNumStat(model);
    }
    @Override
    public PageInfo branchMstDispNumStat(ExtendBranchInfoModel model){
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.branchMstDispNumStat(model);
    }
    @Override
    public PageInfo dayMstDispNumStat(ExtendBranchInfoModel model){
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.dayMstDispNumStat(model);
    }
    @Override
    public List<Map<String,String>> branchDayRepo(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.branchDayRepo(model);
    }
    @Override
    public Map<String,String> branchDayQty(BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }else if(StringUtils.isEmpty(model.getDealerId()) && StringUtils.isNotEmpty(user.getDealerId())){
            model.setDealerId(user.getDealerId());
        }
        if(StringUtils.isNotEmpty(user.getSalesOrg())){
            model.setSalesOrg(user.getSalesOrg());
        }
        return branchInfoMapper.branchDayQty(model);
    }

    @Override
    public List<Map<String, String>> exportOrderByModel(BranchInfoModel model) {
        return branchInfoMapper.exportOrderByModel(model);
    }
}
