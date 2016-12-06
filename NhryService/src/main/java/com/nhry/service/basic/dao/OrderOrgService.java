package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.orderorg.domain.TMdOrderOrg;
import com.nhry.model.basic.OrderOrgModel;

/**
 * Created by huaguan on 2016/12/1.
 * 机构接口
 */
public interface OrderOrgService {
    /**
     * 根据机构ID删除机构信息
     * @param id
     * @return int
     */
    int deleteOrderOrgByPrimaryKey(String id);

    /**
     * 根据试题插入机构信息
     * @param record
     * @return
     */
    int insertOrderOrg(TMdOrderOrg record);

    /**
     * 根据机构实体（非主键允许为空）插入机构信息
     * @param record
     * @return
     */
    int insertOrderOrgSelective(OrderOrgModel record);

    /**
     * 根据机构ID查询机构信息
     * @param id
     * @return
     */
    TMdOrderOrg selectOrderOrgByPrimaryKey(String id);

    /**
     * 根据机构实体更新机构信息（非主键允许为空）
     * @param record
     * @return
     */
    int updateOrderOrgByPrimaryKeySelective(OrderOrgModel record);

    /**
     * 根据机构实体更新机构信息
     * @param record
     * @return
     */
    int updateOrderOrgByPrimaryKey(OrderOrgModel record);

    /**
     * 根据销售组织查询机构列表，带分页
     * @param
     * @return
     */
    PageInfo findTMdOrderOrgList(OrderOrgModel record);
}
