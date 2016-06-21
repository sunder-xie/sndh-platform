package com.nhry.service.basic.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.pojo.ResidentialAreaModel;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public class ResidentialAreaServiceImpl implements ResidentialAreaService {

    private TMdResidentialAreaMapper tMdResidentialAreaMapper;
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
}
