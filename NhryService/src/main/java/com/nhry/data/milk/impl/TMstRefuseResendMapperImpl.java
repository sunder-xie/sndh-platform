package com.nhry.data.milk.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TMstRefuseResendMapper;
import com.nhry.data.milktrans.domain.TMstRefuseResend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/10/18.
 */
public class TMstRefuseResendMapperImpl implements TMstRefuseResendMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public int addTMstRefuseResend(TMstRefuseResend resend) {
        return sqlSessionTemplate.insert("addTMstRefuseResend",resend);
    }
    @Override
    public int uptRefuseResend(TMstRefuseResend resend){
        return sqlSessionTemplate.update("uptRefuseResend",resend);
    }

    @Override
    public int delRefuseResendByDispAndMatnr(TMstRefuseResend resend) {
        return sqlSessionTemplate.delete("delRefuseResendByDispAndMatnr",resend);
    }

    @Override
    public List<TMstRefuseResend> findNoUsedRefuseResend(String branchNo) {

        return sqlSessionTemplate.selectList("findNoUsedRefuseResend",branchNo);
    }

    @Override
    public List<TMstRefuseResend> queryRefuseResendByMatnr(String matnr,String branchNo) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("matnr",matnr);
        map.put("branchNo",branchNo);
        return sqlSessionTemplate.selectList("queryRefuseResendByMatnr",map);
    }

    @Override
    public TMstRefuseResend selectRefuseResendByNo(String resendOrderNo) {
        return sqlSessionTemplate.selectOne("selectRefuseResendByNo",resendOrderNo);
    }
}
