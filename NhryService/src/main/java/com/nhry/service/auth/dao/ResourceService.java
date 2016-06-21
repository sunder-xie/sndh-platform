package com.nhry.service.auth.dao;

import com.nhry.data.auth.domain.TSysResource;
import com.nhry.data.auth.domain.TSysRoleResource;

public interface ResourceService {
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
     * 根据资源编码查看资源详情
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
     * 添加角色资源关系
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
}