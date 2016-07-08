package com.nhry.service.auth.impl;


import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysResourceMapper;
import com.nhry.data.auth.domain.TSysResource;
import com.nhry.data.auth.domain.TSysRoleResource;
import com.nhry.model.auth.RoleResourceData;
import com.nhry.service.BaseService;
import com.nhry.service.auth.dao.ResourceService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class ResourceServiceImpl extends BaseService implements ResourceService {
    private TSysResourceMapper resMapper;

    public void setResMapper(TSysResourceMapper resMapper) {
        this.resMapper = resMapper;
    }

    @Override
    public int deleteResByCode(String resCode) {
        // TODO Auto-generated method stub
        TSysResource res = this.selectResByCode(resCode);
        if (res == null) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该资源编号对应的资源不存在!");
        }
        List<TSysRoleResource> list = resMapper.findRoleResByResCode(resCode);
        if (list != null && list.size() > 0) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该资源目前存在" + list.size() + "个角色资源关系,暂不能删除!");
        }
        return this.resMapper.deleteResByCode(resCode);
    }

    @Override
    public int addRes(TSysResource record) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(record.getResName())) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "资源名称不能为空!");
        }
        if (StringUtils.isEmpty(record.getParent())) {
            record.setParent("-1");
        } else {
            TSysResource res = this.selectResByCode(record.getParent());
            if (res == null) {
                throw new ServiceException(MessageCode.LOGIC_ERROR, "该资源对应的父类资源不存在,请检查你的parent属性!");
            }
        }
        record.setResCode(PrimaryKeyUtils.generateUuidKey());
        record.setCreateAt(new Date());
        record.setCreateBy(userSessionService.getCurrentUser().getLoginName());
        record.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());
        return this.resMapper.addRes(record);
    }

    @Override
    public TSysResource selectResByCode(String resCode) {
        // TODO Auto-generated method stub
        return this.resMapper.selectResByCode(resCode);
    }

    @Override
    public int updateResByCode(TSysResource record) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(record.getResCode()) || StringUtils.isEmpty(record.getResName())) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "资源编号或者资源名称不能为空!");
        }
        TSysResource res = this.selectResByCode(record.getResCode());
        if (res == null) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该资源编号对应的资源不存在!");
        }
        record.setLastModified(new Date());
        record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
        record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
        return this.resMapper.updateResByCode(record);
    }

    @Override
    public int addRoleRes(TSysRoleResource record) {
        // TODO Auto-generated method stub
        return resMapper.addRoleRes(record);
    }

    @Override
    public int deleteRoleRes(TSysRoleResource record) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getResCode())) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "角色id和资源编码resCode不能为空!");
        }
        TSysRoleResource roleRes = this.resMapper.findRoleResByRc(record);
        if (roleRes == null) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该角色资源关系不存在!");
        }
        return this.deleteRoleRes(record);
    }

    @Override
    public List<TSysResource> findRecoureByUserId(String userId) {
        return resMapper.findRecoureByUserId(userId);
    }

	@Override
	public int addRoleRes(RoleResourceData record) {
		// TODO Auto-generated method stub
		if(record != null && record.getRecords().size() > 0){
			for(TSysRoleResource recond : record.getRecords()){
				this.addRoleRes(recond);
			}
		}
		return 1;
	}
}
