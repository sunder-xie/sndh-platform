package com.nhry.data.basic.dao;

import java.util.List;

import com.nhry.data.basic.domain.TMaraPriceRel;

public interface TMaraPriceRelMapper {
	/**
	 * 删除价格组关联商品信息
	 * @param relNo
	 * @return
	 */
    int delMaraPriceByNo(String relNo);
    
    /**
     * 添加价格组关联商品信息
     * @param record
     * @return
     */
    int addMaraPrice(TMaraPriceRel record);
    
    /**
     * 根据编号查询价格组关联商品信息
     * @param relNo
     * @return
     */
    TMaraPriceRel findMaraPriceByNo(String relNo);
    
    /**
     * 修改价格组关联商品信息
     * @param record
     * @return
     */
    int uptMaraPricerel(TMaraPriceRel record);
    
    /**
     * 根据价格编号id查询价格组关联商品信息
     * @param id
     * @return
     */
    List<TMaraPriceRel> findMaraPricesById(String id);
    
}