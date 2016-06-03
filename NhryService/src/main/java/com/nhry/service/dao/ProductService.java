package com.nhry.service.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdMara;
import com.nhry.domain.TMdMaraEx;
import com.nhry.pojo.query.ProductInfoExModel;
import com.nhry.pojo.query.ProductQueryModel;

public interface ProductService {

    TMdMara selectProductByCode(String productCode);

    int uptProductExByCode(TMdMaraEx record);
    
    public PageInfo searchProducts(ProductQueryModel smodel);
    
    ProductInfoExModel selectProductAndExByCode(String matnr);
}
