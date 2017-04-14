package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.domain.TMdMaraStud;
import com.nhry.model.stud.OrderStudQueryModel;

public interface TMdMaraStudMapper {
	List<TMdMaraStud> findAllListBySalesOrg(String salesOrg);

	List<TMdMaraStud> findAllListBySalesOrgNotIn(Map<String, Object> selectMaraMap);

	PageInfo findMaraStudAllPage(OrderStudQueryModel queryModel);

	int updateInfo(TMdMaraStud tMdMaraStud);
}