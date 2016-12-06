package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.orderorg.dao.TMdOrderOrgMapper;
import com.nhry.data.orderorg.domain.TMdOrderOrg;
import com.nhry.model.basic.OrderOrgModel;
import com.nhry.service.basic.dao.OrderOrgService;
import com.nhry.utils.CodeGeneratorUtil;
import com.nhry.utils.date.Date;

import java.text.SimpleDateFormat;

/**
 * Created by huaguan on 2016/12/1.
 */
public class OrderOrgServiceImpl implements OrderOrgService {
    private TMdOrderOrgMapper orderOrgMapper;
    private UserSessionService userSessionService;

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    public void setOrderOrgMapper(TMdOrderOrgMapper orderOrgMapper) {
        this.orderOrgMapper = orderOrgMapper;
    }

    @Override
    public int deleteOrderOrgByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertOrderOrg(TMdOrderOrg orderOrg) {
        TSysUser user = userSessionService.getCurrentUser();
        if(orderOrg!=null){
            orderOrg.setId(CodeGeneratorUtil.getCode());
            orderOrg.setSalesOrg(user.getSalesOrg());
            orderOrg.setCreateAt(new Date());
            orderOrg.setCreateBy(user.getLoginName());
            orderOrg.setCreateByTxt(user.getDisplayName());
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"新增机构信息错误，为空不能插入");
        }
        return orderOrgMapper.insertOrderOrg(orderOrg);
    }

    @Override
    public int insertOrderOrgSelective(OrderOrgModel record) {
        return 0;
    }

    @Override
    public TMdOrderOrg selectOrderOrgByPrimaryKey(String id) {
        return orderOrgMapper.selectOrderOrgByPrimaryKey(id);
    }

    @Override
    public int updateOrderOrgByPrimaryKeySelective(OrderOrgModel record) {
        TSysUser user = userSessionService.getCurrentUser();
        TMdOrderOrg orderOrg = new TMdOrderOrg();
        if(record!=null){
            orderOrg.setId(record.getId());
            if(record.getTel()!=null){orderOrg.setTel(record.getTel());}
            if(record.getOrgName()!=null){orderOrg.setOrgName(record.getOrgName());}
            if(record.getMp()!=null){orderOrg.setMp(record.getMp());}
            if(record.getContact()!=null){orderOrg.setContact(record.getContact());}
            if(record.getAddress()!=null){orderOrg.setTel(record.getAddress());}
            if(record.getOrgCode()!=null){orderOrg.setOrgCode(record.getOrgCode());}
            orderOrg.setLastModified(new Date());
            orderOrg.setLastModifiedBy(user.getLoginName());
            orderOrg.setLastModifiedByTxt(user.getDisplayName());
        }
        return orderOrgMapper.updateOrderOrgByPrimaryKeySelective(orderOrg);
    }

    @Override
    public int updateOrderOrgByPrimaryKey(OrderOrgModel record) {
        return 0;
    }

    @Override
    public PageInfo findTMdOrderOrgList(OrderOrgModel record) {
        TSysUser user = userSessionService.getCurrentUser();
        record.setSalesOrg(user.getSalesOrg());
        return orderOrgMapper.findTMdOrderOrgList(record);
    }
}
