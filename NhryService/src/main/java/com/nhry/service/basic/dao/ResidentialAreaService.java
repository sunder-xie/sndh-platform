package com.nhry.service.basic.dao;


import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.service.basic.pojo.ResidentialAreaModel;
import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public interface ResidentialAreaService {
    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo);

    public TMdResidentialArea selectById(String id);

    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel);

    int  addResidentialArea(ResidentialAreaModel residentialAreaModel);

}
