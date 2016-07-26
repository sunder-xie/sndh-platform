package com.nhry.data.auth.dao;

import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.model.auth.UserRoleModel;

import java.util.List;

public interface TSysUserRoleMapper {
    int deleteUserRoleByLoginName(TSysUserRole key);

    int addUserRole(TSysUserRole record);

    int updateUserRoleByLoginName(TSysUserRole record);
    
    public TSysUserRole findUserRoleRelations(TSysUserRole record);
    
    public int deleteUserRoles(UserRoleModel urmodel);
    
    public List<TSysUserRole> findUserRoleByRid(String rid);


    TSysUserRole getUserRoleByLoginName(String loginName);
}