package com.nhry.service.basic.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMaraPriceRel;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.model.basic.PriceQueryModel;

public interface PriceService {
	/**
	 * 添加新的价格组
	 * @param record
	 * @return
	 */
	int addNewPriceGroup(TMdPrice record);
    
	/**
	 * 停用价格组
	 * @param record
	 * @return
	 */
	int disablePriceGroup(TMdPrice record);
	
	/**
	 * 修改价格组
	 * @param record
	 * @return
	 */
	int updatePriceGroup(TMdPrice record);
	
     /**
      * 根据经销商类型、状态 查询
      * @param smodel
      * @return
      */
	PageInfo searchPriceGroups(PriceQueryModel smodel);
    
	/**
	 * 根据code查询价格组
	 * @param id
	 * @return
	 */
	TMdPrice selectPriceGroupByCode(String id);
	
	/**
	 * 更新价格组关联的商品信息
	 * @param record
	 * @return
	 */
	int mergeMaraPriceRel(List<TMaraPriceRel> records);
}
