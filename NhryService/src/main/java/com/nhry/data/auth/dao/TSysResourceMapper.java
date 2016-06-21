package com.nhry.data.auth.dao;

import java.util.List;

import com.nhry.data.auth.domain.TSysResource;
import com.nhry.data.auth.domain.TSysRoleResource;

public interface TSysResourceMapper {
	/**
	 * 根据资源编码删除资源
	 * @param resCode
	 * @return
	 */
    int deleteResByCode(String resCode);
    
    /**
     * 添加资源
     * @param record
     * @return
     */
    int addRes(TSysResource record);
    
    /**
     * 根据资源编码查看资源明细
     * @param resCode
     * @return
     */
    TSysResource selectResByCode(String resCode);
    
    /**
     * 修改资源
     * @param record
     * @return
     */
    int updateResByCode(TSysResource record);
    
    /**
     * 添加资源角色关系
     * @param record
     * @return
     */
    int addRoleRes(TSysRoleResource record);
    
    /**
     * 根据角色id和资源编码删除角色资源关系
     * @param roleId
     * @param resCode
     * @return
     */
    int deleteRoleRes(TSysRoleResource record);
    
    /**
     * 根据角色id和资源编码查询角色资源关系
     * @param record
     * @return
     */
    TSysRoleResource findRoleResByRc(TSysRoleResource record);
    
    /**
     * 根据角色id，查找角色资源关系列表
     * @param rid
     * @return
     */
    List<TSysRoleResource> findRoleResByRid(String rid);
    
    /**
     * 根据资源编码查询角色资源关系列表
     * @param resCode
     * @return
     */
    List<TSysRoleResource> findRoleResByResCode(String resCode);

    /**
     * 根据用户编码查询资源信息
     * @param userId
     * @return
     */
    List<TSysResource> findRecoureByUserId(String userId);
}