package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdOperationLogMapper;
import com.nhry.model.basic.CustOperationQueryModel;
import com.nhry.service.basic.dao.OperationLogService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gongjk on 2016/10/24.
 */
public class OperationLogServiceImpl implements OperationLogService {
    private TMdOperationLogMapper operationLogMapper;

    public void setOperationLogMapper(TMdOperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    @Override
    public PageInfo getCustOperationLog( CustOperationQueryModel cModel) {
        if(StringUtils.isEmpty(cModel.getPageNum()) || StringUtils.isEmpty(cModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }

        return operationLogMapper.getCustOperationLog(cModel);
    }
}
