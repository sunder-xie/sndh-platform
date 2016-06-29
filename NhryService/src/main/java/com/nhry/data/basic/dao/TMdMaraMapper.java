package com.nhry.data.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.basic.pojo.ProductInfoExModel;

import java.util.List;

public interface TMdMaraMapper {


    TMdMara selectProductByCode(String matnr);

    TMdMara selectProductAndExByCode(String matnr);

    int uptProductByCode(TMdMara record);

    int pubProductByCode(String code);

    PageInfo searchProducts(ProductQueryModel smodel);

    List<TMdMara> selectProductAndExListByCode(String productCode);

    int addProduct(TMdMara tMdMara);

    int updateProduct(TMdMara tMdMara);

    int isProduct(String id);
}