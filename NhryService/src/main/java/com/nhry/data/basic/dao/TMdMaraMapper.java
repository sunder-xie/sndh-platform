package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.basic.pojo.ProductInfoExModel;

import java.util.List;

public interface TMdMaraMapper {
	
	/**
	 * 根据产品编号查询产品
	 * @param matnr
	 * @return
	 */
    TMdMara selectProductByCode(String matnr);
    
    /**
     * 根据产品编号查询产品与产品扩展信息
     * @param matnr
     * @return
     */
    TMdMara selectProductAndExByCode(String matnr);
    
    /**
     * 修改产品信息
     * @param record
     * @return
     */
    int uptProductByCode(TMdMara record);
    
    /**
     * 发布产品
     * @param code
     * @return
     */
    int pubProductByCode(String code);
    
    /**
     * 根据产品分类、状态
     * @param smodel
     * @return
     */
    PageInfo searchProducts(ProductQueryModel smodel);
    
    /**
     * 根据产品编号查询产品与产品扩展信息
     * @param productCode
     * @return
     */
    List<TMdMara> selectProductAndExListByCode(String productCode);
    
    /**
     * 添加产品信息
     * @param tMdMara
     * @return
     */
    int addProduct(TMdMara tMdMara);
    
    /**
     * 修改产品信息
     * @param tMdMara
     * @return
     */
    int updateProduct(TMdMara tMdMara);

    int isProduct(String id);
}