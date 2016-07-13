package com.nhry.service.basic.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchScopeMapper;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.data.basic.domain.TMdBranchScopeKey;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.model.basic.BranchAreaSearch;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.pojo.BranchScopeModel;
import com.nhry.service.basic.pojo.ResidentialAreaModel;
import com.nhry.utils.PrimaryKeyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public class ResidentialAreaServiceImpl implements ResidentialAreaService {

    private TMdResidentialAreaMapper tMdResidentialAreaMapper;
    private TMdBranchScopeMapper tMdBranchScopeMapper;
    private UserSessionService userSessionService;

    @Override
    public PageInfo searchAreaByBranchNo(BranchAreaSearch bSearch) {
        if(StringUtils.isEmpty(bSearch.getPageNum()) || StringUtils.isEmpty(bSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        if(StringUtils.isEmpty(bSearch.getBranchNo())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站编号不能为空！");
        }
        return tMdResidentialAreaMapper.searchAreaByBranchNo(bSearch);
    }

    /**
     * 配送区域关联奶站
     * @param bModel
     * @return
     */
    @Override
    public int areaRelBranch(BranchScopeModel bModel) {
        String message = "";
        try{
            //删除 奶站和配送区域的 关系 并将配送区域设为未分配
            List<TMdBranchScopeKey> oldList =  tMdBranchScopeMapper.getBranchScopeByBranchNo(bModel.getBranchNo());
            if(oldList!=null && oldList.size()>0){
                for (TMdBranchScopeKey scope : oldList){
                    //更新小区状态为未分配
                    tMdResidentialAreaMapper.updateStatusToUnDistById(scope.getResidentialAreaId());
                }
                //并删除原关系
                tMdBranchScopeMapper.deleteAllAreaByBranchNo(bModel.getBranchNo());
            }
            //更新奶站 和 配送区域关系
            List<String> newList = bModel.getResidentialAreaIds();
            if(newList!=null && newList.size()>0){
                for (String id : newList){
                    TMdResidentialArea area  = tMdResidentialAreaMapper.getAreaById(id);
                    if("30".equals(area.getStatus())){
                        message = "配送区域"+id+"已经分配！";
                        throw new ServiceException(MessageCode.LOGIC_ERROR,"配送区域"+id+"已经分配！");
                    }else{
                        TMdBranchScopeKey scopeKey = new  TMdBranchScopeKey();
                        scopeKey.setBranchNo(bModel.getBranchNo());
                        scopeKey.setResidentialAreaId(id);
                        tMdBranchScopeMapper.addBranchScope(scopeKey);
                        tMdResidentialAreaMapper.updateStatusToDistedById(id);
                    }
                }
              }
            return 1;
        }catch (Exception e){
            if(message !=""){
                throw new ServiceException(MessageCode.LOGIC_ERROR,message);
            }else{
                throw new ServiceException(MessageCode.LOGIC_ERROR,"配送失败！");
            }

        }
    }

    @Override
    public List<TMdResidentialArea> getUnDistAreas() {
        TSysUser user = userSessionService.getCurrentUser();
        String salesOrg = user.getSalesOrg();
        return tMdResidentialAreaMapper.getUnDistAreas(salesOrg);
    }

    @Override
    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo) {
        return tMdResidentialAreaMapper.getAreaByBranchNo(branchNo);
    }

    @Override
    public TMdResidentialArea selectById(String id) {
        return tMdResidentialAreaMapper.selectById(id);
    }

    @Override
    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel) {
        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(residentialAreaModel.getPageNum()) || StringUtils.isEmpty(residentialAreaModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tMdResidentialAreaMapper.findAreaListByPage(residentialAreaModel);
    }

    @Override
    public int addResidentialArea(TMdResidentialArea tMdResidentialArea) {
        TSysUser user = userSessionService.getCurrentUser();
        tMdResidentialArea.setSalesOrg(user.getSalesOrg());
        tMdResidentialArea.setId(PrimaryKeyUtils.generateUuidKey());
        tMdResidentialArea.setStatus("10");
        return tMdResidentialAreaMapper.addResidentialArea(tMdResidentialArea);
    }

    @Override
    public int uptResidentialArea(TMdResidentialArea tMdResidentialArea) {
        return tMdResidentialAreaMapper.uptResidentialArea(tMdResidentialArea);
    }

    @Override
    public int deleteAreaById(String id) {
        return tMdResidentialAreaMapper.deleteAreaById(id);
    }


    public void settMdResidentialAreaMapper(TMdResidentialAreaMapper tMdResidentialAreaMapper) {
        this.tMdResidentialAreaMapper = tMdResidentialAreaMapper;
    }

    public TMdResidentialAreaMapper gettMdResidentialAreaMapper() {
        return tMdResidentialAreaMapper;
    }


    public void settMdBranchScopeMapper(TMdBranchScopeMapper tMdBranchScopeMapper) {
        this.tMdBranchScopeMapper = tMdBranchScopeMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }
}
