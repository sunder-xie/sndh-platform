package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TMstInsideSalOrder;

/**
 * Created by gongjk on 2016/6/30.
 */
public interface TMstInsideSalOrderMapper {
   public TMstInsideSalOrder getInSalOrderByDispOrderNo(String dispOrderNo);

   int insertInsideSalOrder(TMstInsideSalOrder sOrder);
}
