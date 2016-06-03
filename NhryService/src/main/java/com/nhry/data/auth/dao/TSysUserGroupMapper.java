package com.nhry.data.auth.dao;

import com.nhry.data.auth.domain.TSysUserGroup;

public interface TSysUserGroupMapper {
    int deleteUserGroupById(String id);

    int addUserGroup(TSysUserGroup record);

    TSysUserGroup findUserGroupById(String id);

    int updateUserGroupById(TSysUserGroup record);
}