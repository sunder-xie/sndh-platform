package com.nhry.data.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdMara;
import com.nhry.pojo.query.ProductInfoExModel;
import com.nhry.pojo.query.ProductQueryModel;

public interface TMdMaraMapper {


    TMdMara selectProductByCode(String matnr);
    
    ProductInfoExModel selectProductAndExByCode(String matnr);

    int uptProductByCode(TMdMara record);
    
    int uptPubProductByCode();

    PageInfo searchProducts(ProductQueryModel smodel);
    
}