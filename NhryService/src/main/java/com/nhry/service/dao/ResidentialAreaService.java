package com.nhry.service.dao;

import com.nhry.domain.TMdResidentialArea;

import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public interface ResidentialAreaService {
    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo);

    public TMdResidentialArea selectById(String id);
}
