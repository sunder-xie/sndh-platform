package com.nhry.service.stud.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.stud.dao.TMdClassMapper;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.model.stud.ClassQueryModel;
import com.nhry.service.stud.dao.ClassService;

/**
 * @author zhaoxijun
 * @data 2017年4月10日
 */
public class ClassServiceImpl implements ClassService {

	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private TMdClassMapper classMapper;
	
	@Override
	public TMdClass findClassByClassCode(String classCode) {
		return classMapper.selectByClassCode(classCode);
	}

	@Override
	public List<TMdClass> findClassListBySalesOrg(String salesOrg) {
		if(StringUtils.isBlank(salesOrg)){
			salesOrg = this.userSessionService.getCurrentUser().getSalesOrg();
		}
		if(StringUtils.isBlank(salesOrg)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "销售组织编码必传");
		}
		return classMapper.findClassListBySalesOrg(salesOrg);
	}

	@Override
	public PageInfo<TMdClass> findClassPage(ClassQueryModel queryModel) {
		return classMapper.findClassPage(queryModel);
	}

	@Override
	public int addClass(TMdClass mdClass) {
		//TODO
		return classMapper.insertClass(mdClass);
	}

	@Override
	public int updClass(TMdClass mdClass) {
		//TODO
		return classMapper.updateTMdClass(mdClass);
	}

	@Override
	public int delClass(String classCode) {
		// TODO 
		return classMapper.deleteByClassCode(classCode);
	}

}
