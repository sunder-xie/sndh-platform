package com.nhry.data.bill.dao;

import com.nhry.data.bill.domain.TMdDispRateItem;

import java.util.List;

/**
 * Created by gongjk on 2016/7/4.
 */
public interface TMdDispRateItemMapper {

    List<TMdDispRateItem> getDispRateNumBySalOrg(String saleOrg);

    int delDispRateNumBySaleOrg(String saleOrg);

    int addDispRateItem(TMdDispRateItem item);
}
