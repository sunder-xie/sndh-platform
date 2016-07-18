package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmSalOrder;

import java.util.Map;

/**
 * Created by gongjk on 2016/7/16.
 */
public interface TSsmSalOrderMapper {
    int addsalOrder(TSsmSalOrder order);

    int uptVouCherNoByOrderNo(Map map);
}
