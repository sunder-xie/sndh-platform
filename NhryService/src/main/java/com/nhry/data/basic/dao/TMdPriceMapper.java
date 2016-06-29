package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.model.basic.PriceQueryModel;

public interface TMdPriceMapper {
	 /**
	  * 添加一个新的价格组
	  * @param record
	  * @return
	  */
	 int addNewPriceGroup(TMdPrice record);
	 
	 /**
	  * 禁用价格组
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
	  * 
	  * @param smodel
	  * @return
	  */
	 PageInfo searchPriceGroups(PriceQueryModel smodel);
	 
	 /**
	  * 根据价格组编号查询价格组明细
	  * @param id
	  * @return
	  */
	 TMdPrice selectPriceGroupById(String id);
}