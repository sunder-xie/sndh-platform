package com.nhry.service.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdResidentialArea;
import com.nhry.pojo.query.ResidentialAreaModel;

import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public interface ResidentialAreaService {
    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo);

    public TMdResidentialArea selectById(String id);

    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel);
}
