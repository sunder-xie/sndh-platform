package com.nhry.data.auth.dao;

import java.util.List;

import com.nhry.data.auth.domain.TSysRole;

public interface TSysRoleMapper {
    int deleteRoleByRid(String id);

    int addRole(TSysRole record);

    TSysRole findRoleByRid(String id);

    int updateRoleByRid(TSysRole record);
    
    List<TSysRole> getAllRoles();
}