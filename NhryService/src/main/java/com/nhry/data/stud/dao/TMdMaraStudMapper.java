package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.nhry.data.stud.domain.TMdMaraStud;

public interface TMdMaraStudMapper {
	List<TMdMaraStud> findAllListBySalesOrg(String salesOrg);

	List<TMdMaraStud> findAllListBySalesOrgNotIn(Map<String, Object> selectMaraMap);
}