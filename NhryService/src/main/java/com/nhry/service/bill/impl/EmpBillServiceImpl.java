package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.bill.dao.EmpBillMapper;
import com.nhry.model.bill.EmpAccountSearch;
import com.nhry.model.bill.EmpDispDetialInfoSearch;
import com.nhry.service.bill.dao.EmpBillService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gongjk on 2016/7/1.
 */
public class EmpBillServiceImpl implements EmpBillService {
    private EmpBillMapper empBillMapper;
    public void setEmpBillMapper(EmpBillMapper empBillMapper) {
        this.empBillMapper = empBillMapper;
    }

    @Override
    public PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch) {
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return  empBillMapper.empDispDetialInfo(eSearch);
    }

    @Override
    public PageInfo empAccountReceAmount(EmpAccountSearch eSearch) {
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return  empBillMapper.empAccountReceAmount(eSearch);
    }
}
