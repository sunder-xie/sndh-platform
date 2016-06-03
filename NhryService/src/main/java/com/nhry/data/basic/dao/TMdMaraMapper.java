package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.model.basic.ProductQueryModel;

public interface TMdMaraMapper {


    TMdMara selectProductByCode(String matnr);

    int uptProductByCode(TMdMara record);

    PageInfo searchProducts(ProductQueryModel smodel);
    
}