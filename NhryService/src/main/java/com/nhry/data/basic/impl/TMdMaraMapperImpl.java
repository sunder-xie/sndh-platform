package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.model.basic.ProductQueryModel;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class TMdMaraMapperImpl implements TMdMaraMapper {

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    private SqlSessionFactory sqlSessionFactory;
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public TMdMara selectProductByCode(String productCode) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.selectOne("selectProductByCode", productCode);
    }

    @Override
    public int uptProductByCode(TMdMara record) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.update("uptProductByCode", record);
    }


    @Override
    public PageInfo searchProducts(ProductQueryModel smodel) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectListByPages("searchProducts", smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
    }

    @Override
    public List<TMdMara> selectProductAndExListByCode(String productCode) {
        return this.sqlSessionTemplate.selectList("selectProductAndExListByCode", productCode);
    }

    @Override
    public int addProduct(TMdMara tMdMara) {
        return sqlSessionTemplate.insert("addProduct",tMdMara);
    }

    @Override
    public int updateProduct(TMdMara tMdMara) {
        return sqlSessionTemplate.update("updateProduct",tMdMara);
    }

    @Override
    public int isProduct(String id) {
        return sqlSessionTemplate.selectOne("isProduct",id);
    }

    @Override
    public TMdMara selectProductAndExByCode(String productCode) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.selectOne("selectProductAndExByCode", productCode);
    }

    @Override
    public int pubProductByCode(String code) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.update("pubProductByCode", code);
    }
}
