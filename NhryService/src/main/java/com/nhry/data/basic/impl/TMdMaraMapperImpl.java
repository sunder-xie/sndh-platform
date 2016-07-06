package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import com.nhry.model.basic.ProductQueryModel;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

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
    public TMdMara selectProductByCode(Map<String,String> attrs) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.selectOne("selectProductByCode", attrs);
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
    public List<TMdMara> selectProductAndExListByCode(Map<String,String> attrs) {
        return this.sqlSessionTemplate.selectList("selectProductAndExListByCode", attrs);
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
    public TMdMara selectProductAndExByCode(Map<String,String> attrs) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.selectOne("selectProductAndExByCode", attrs);
    }

    @Override
    public int pubProductByCode(Map<String,String> attrs) {
        // TODO Auto-generated method stub
        return this.sqlSessionTemplate.update("pubProductByCode", attrs);
    }

	@Override
	public List<TMdMara> getDealerMaras(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("getDealerMaras", attrs);
	}

	@Override
	public List<TMdMara> getCompMaras(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("getCompMaras", attrs);
	}

	@Override
	public List<TMdMara> findMarasBySalesCodeAndOrg(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findMarasBySalesCodeAndOrg", attrs);
	}
}
