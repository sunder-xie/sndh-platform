package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import  java.util.*;
public interface ProductService {

    TMdMara selectProductByCode(String productCode);

    int uptProductByCode(TMdMara record);
    
    int uptProductExByCode(TMdMaraEx maraEx);
    
    int pubProductByCode(String code);
    
    public PageInfo searchProducts(ProductQueryModel smodel);
    
    TMdMara selectProductAndExByCode(String matnr);

    List<TMdMara> selectProductAndExListByCode(String productCode);
}
