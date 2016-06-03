package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdBranchScopeKey;

public interface TMdBranchScopeMapper {
    int deleteByPrimaryKey(TMdBranchScopeKey key);

    int insert(TMdBranchScopeKey record);

    int insertSelective(TMdBranchScopeKey record);
}