package com.nhry.service.statistics.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.statistics.dao.DistributionInfoMapper;
import com.nhry.model.statistics.DistInfoModel;
import com.nhry.service.statistics.dao.DistributionInfoService;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cbz on 7/16/2016.
 */
public class DistributionInfoServiceImpl implements DistributionInfoService{
    private DistributionInfoMapper distributionInfoMapper;

    public void setDistributionInfoMapper(DistributionInfoMapper distributionInfoMapper) {
        this.distributionInfoMapper = distributionInfoMapper;
    }

    @Override
    public PageInfo findDistDifferInfo(DistInfoModel model) {
        if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return distributionInfoMapper.findDistDifferInfo(model);
    }
}
