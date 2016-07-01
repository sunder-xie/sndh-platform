package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.basic.pojo.ProductInfoExModel;

import  java.util.*;
public interface ProductService {
	/**
	 * 根据产品编号获取产品信息
	 * @param productCode
	 * @return
	 */
    TMdMara selectProductByCode(String productCode);
    
    /**
     * 修改产品信息
     * @param record
     * @return
     */
    int uptProductByCode(TMdMara record);
    
    /**
     * 更新产品的扩展信息
     * @param matnr
     * @param maraEx
     * @return
     */
    int uptProductExByCode(String matnr,TMdMaraEx maraEx);
    
    /**
     * 根据产品编号发布相应的产品
     * @param code
     * @return
     */
    int pubProductByCode(String code);
    
    /**
     * 根据分类、产品状态、(产品名称、产品简称、产品编号)查询产品列表信息
     * @param smodel
     * @return
     */
    public PageInfo searchProducts(ProductQueryModel smodel);
     
    /**
     * 根据产品编号查询产品及其扩展信息
     * @param matnr
     * @return
     */
    TMdMara selectProductAndExByCode(String matnr);
    
    /**
     * 根据产品编号模糊查询产品信息列表
     * @param productCode
     * @return
     */
    List<TMdMara> selectProductAndExListByCode(String productCode);
    
    /**
     * 获取奶站可销售的产品清单
     * @param branchNo
     * @return
     */
    List<TMdMara> getBranchSaleMaras(String branchNo);
}
