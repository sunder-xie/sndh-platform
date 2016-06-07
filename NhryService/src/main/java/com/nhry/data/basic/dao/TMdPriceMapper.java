package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.model.basic.PriceQueryModel;

public interface TMdPriceMapper {
	 int addNewPriceGroup(TMdPrice record);

	 int disablePriceGroup(TMdPrice record);

	 int updatePriceGroup(TMdPrice record);

	 PageInfo searchPriceGroups(PriceQueryModel smodel);

	 TMdPrice selectPriceGroupByCode(Integer id);

}