package com.nhry.service.basic.impl;

import com.nhry.data.basic.dao.TMdBranchScopeMapper;
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

    @Override
    public int deleteAreaByBranchNo(String branchNo,String residentNoStr) {
        System.out.println(residentNoStr);
        List<String> list = new ArrayList<String>();
        if(StringUtils.isNotBlank(residentNoStr)){
            list = Arrays.asList(residentNoStr.split(","));
        }
        return tMdBranchScopeMapper.deleteAreaByBranchNo(branchNo,list);
    }

    public void settMdBranchScopeMapper(TMdBranchScopeMapper tMdBranchScopeMapper) {
        this.tMdBranchScopeMapper = tMdBranchScopeMapper;
    }


}
