package com.nhry.service.basic.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;

public interface ProductService {

    TMdMara selectProductByCode(String productCode);

    int uptProductExByCode(TMdMaraEx record);
    
    public PageInfo searchProducts(ProductQueryModel smodel);
    
    ProductInfoExModel selectProductAndExByCode(String matnr);
}
