package com.nhry.data.dao;

import com.nhry.domain.TMdBranchScopeKey;

public interface TMdBranchScopeMapper {
    int deleteByPrimaryKey(TMdBranchScopeKey key);

    int insert(TMdBranchScopeKey record);

    int insertSelective(TMdBranchScopeKey record);
}