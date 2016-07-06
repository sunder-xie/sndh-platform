package com.nhry.service.basic.impl;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdBranchScopeMapper;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.service.basic.dao.TMdBranchScopeService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gongjk on 2016/6/7.
 */
public class TMdBranchScopeServiceImpl implements TMdBranchScopeService {

    private TMdBranchScopeMapper tMdBranchScopeMapper;
    private TMdResidentialAreaMapper  tMdResidentialAreaMapper;



    @Override
    public int deleteAreaByBranchNo(String branchNo,String residentNoStr) {

        List<String> list = new ArrayList<String>();
        if(StringUtils.isNotBlank(residentNoStr)){
            list = Arrays.asList(residentNoStr.split(","));
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"参数中没有小区编号");
        }
        try{
            //首先删除奶站和小区的关系
            tMdBranchScopeMapper.deleteAreaByBranchNo(branchNo,list);

            //然后将小区的状态置为未分配
            if(list.size()>0){
                for(String areaId : list){
                    tMdResidentialAreaMapper.updateStatusToUnDistById(areaId);
                }
            }
        }catch (Exception e){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"删除失败");
        }

        return 1;
    }

    public void settMdBranchScopeMapper(TMdBranchScopeMapper tMdBranchScopeMapper) {
        this.tMdBranchScopeMapper = tMdBranchScopeMapper;
    }

    public void settMdResidentialAreaMapper(TMdResidentialAreaMapper tMdResidentialAreaMapper) {
        this.tMdResidentialAreaMapper = tMdResidentialAreaMapper;
    }
}
