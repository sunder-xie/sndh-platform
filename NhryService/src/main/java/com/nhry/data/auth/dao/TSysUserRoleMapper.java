package com.nhry.data.auth.dao;

import java.util.List;

import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.model.auth.UserRoleModel;

public interface TSysUserRoleMapper {
    int deleteUserRoleByLoginName(TSysUserRole key);

    int addUserRole(TSysUserRole record);

    int updateUserRoleByLoginName(TSysUserRole record);
    
    public TSysUserRole findUserRoleRelations(TSysUserRole record);
    
    public int deleteUserRoles(UserRoleModel urmodel);
    
    public List<TSysUserRole> findUserRoleByRid(String rid);
}