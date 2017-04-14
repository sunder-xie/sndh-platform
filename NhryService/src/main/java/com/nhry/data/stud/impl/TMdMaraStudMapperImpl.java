package com.nhry.data.stud.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMdMaraStudMapper;
import com.nhry.data.stud.domain.TMdMaraStud;
import com.nhry.model.stud.OrderStudQueryModel;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class TMdMaraStudMapperImpl implements TMdMaraStudMapper {

	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<TMdMaraStud> findAllListBySalesOrg(String salesOrg) {
		return sqlSessionTemplate.selectList("findAllListBySalesOrg", salesOrg);
	}

	@Override
	public List<TMdMaraStud> findAllListBySalesOrgNotIn(Map<String, Object> selectMaraMap) {
		return sqlSessionTemplate.selectList("findAllListBySalesOrgNotIn", selectMaraMap);
	}

	@Override
	public PageInfo findMaraStudAllPage(OrderStudQueryModel queryModel) {
		return sqlSessionTemplate.selectListByPages("findMaraStudAllPage",queryModel ,Integer.parseInt(queryModel.getPageNum()), Integer.parseInt(queryModel.getPageSize()));
	}
	
	
	@Override
	public int updateInfo(TMdMaraStud tMdMaraStud) {
		return sqlSessionTemplate.update("updateInfo", tMdMaraStud);
	}
	
	


}
