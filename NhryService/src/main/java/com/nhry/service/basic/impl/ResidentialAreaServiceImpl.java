package com.nhry.service.basic.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.data.basic.dao.TMdBranchScopeMapper;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.data.basic.domain.TMdBranchScopeKey;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.model.basic.BranchAreaSearch;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.pojo.AreaSearchModel;
import com.nhry.service.basic.pojo.BranchScopeModel;
import com.nhry.service.basic.pojo.ResidentialAreaModel;
import com.nhry.utils.PrimaryKeyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/6/3.
 */
public class ResidentialAreaServiceImpl implements ResidentialAreaService {

    private TMdResidentialAreaMapper tMdResidentialAreaMapper;
    private TMdBranchScopeMapper tMdBranchScopeMapper;
    private UserSessionService userSessionService;
    private TSysUserRoleMapper urMapper;


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
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole =urMapper.getUserRoleByLoginName(user.getLoginName());
        if("10004".equals(userRole.getId())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站内勤 无权进行奶站配送区域分配！");
        }

        String message = "";
        try{
            //删除 奶站和配送区域的 关系 并将配送区域设为未分配
            List<TMdBranchScopeKey> oldList =  tMdBranchScopeMapper.getBranchScopeByBranchNo(bModel.getBranchNo());
            if(oldList!=null && oldList.size()>0){
                //并删除奶站下的所有区域原关系
                tMdBranchScopeMapper.deleteAllAreaByBranchNo(bModel.getBranchNo());
            }
            //更新奶站 和 配送区域关系
            List<String> newList = bModel.getResidentialAreaIds();
            if(newList!=null && newList.size()>0){
                for (String id : newList){
                        TMdBranchScopeKey scopeKey = new  TMdBranchScopeKey();
                        scopeKey.setBranchNo(bModel.getBranchNo());
                        scopeKey.setResidentialAreaId(id);
                        tMdBranchScopeMapper.addBranchScope(scopeKey);
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
    public List<TMdResidentialArea> searchAreaBySalesOrg(AreaSearchModel aModel) {
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole = urMapper.getUserRoleByLoginName(user.getLoginName());
        Map<String,String> map = new HashMap<String,String>();
        aModel.setSalesOrg(user.getSalesOrg());
        //奶站内勤，只看该奶站下的
        if("10004".equals(userRole.getId())){
            aModel.setBranchNo(user.getBranchNo());
        }
        if(!StringUtils.isEmpty(aModel.getContent())){
        	aModel.setContent(aModel.getContent().trim().replace(" ", "%"));
        }
        return tMdResidentialAreaMapper.searchAreaBySalesOrg(aModel);
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
        TSysUser user = userSessionService.getCurrentUser();
        residentialAreaModel.setSalesOrg(user.getSalesOrg());


        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(residentialAreaModel.getPageNum()) || StringUtils.isEmpty(residentialAreaModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tMdResidentialAreaMapper.findAreaListByPage(residentialAreaModel);
    }

    @Override
    public int addResidentialArea(TMdResidentialArea tMdResidentialArea) {
        TMdResidentialArea area = tMdResidentialAreaMapper.getAreaByAreaName(tMdResidentialArea.getResidentialAreaTxt());
        if(area!=null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该小区名称已存在！");
        }
        TSysUser user = userSessionService.getCurrentUser();
        tMdResidentialArea.setSalesOrg(user.getSalesOrg());
        tMdResidentialArea.setId(PrimaryKeyUtils.generateUuidKey());
        tMdResidentialArea.setStatus("10");
        tMdResidentialArea.setCreateAt(new Date());
        tMdResidentialArea.setCreateBy(user.getLoginName());
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

    public void setUrMapper(TSysUserRoleMapper urMapper) {
        this.urMapper = urMapper;
    }
}
