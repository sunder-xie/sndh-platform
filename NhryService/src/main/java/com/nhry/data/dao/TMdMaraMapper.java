package com.nhry.data.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdMara;
import com.nhry.pojo.query.ProductQueryModel;

public interface TMdMaraMapper {


    TMdMara selectProductByCode(String matnr);

    int uptProductByCode(TMdMara record);

    PageInfo searchProducts(ProductQueryModel smodel);
    
}