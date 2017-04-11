package com.nhry.data.stud.dao;

import java.util.List;

import com.nhry.data.stud.domain.TMdMaraStud;

public interface TMdMaraStudMapper {
	List<TMdMaraStud> findAllListBySalesOrg(String salesOrg);
}