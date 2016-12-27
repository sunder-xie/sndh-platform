package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TSsmMilkmanAmtInitMapper;
import com.nhry.data.milktrans.domain.TSsmMilkmanAmtInit;
import com.nhry.data.milktrans.domain.TSsmMilkmanAmtInitKey;
import com.nhry.model.statistics.OutMilkModel;

import java.util.List;

/**
 * Created by huaguan on 2016/12/23.
 */
public class TSsmMilkmanAmtInitMapperImpl implements TSsmMilkmanAmtInitMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int insertAmtInit(TSsmMilkmanAmtInit record) {

        return sqlSessionTemplate.insert("insertAmtInit",record);
    }

    /**
     *
     * @return
     */
    @Override
    public List<TSsmMilkmanAmtInit> selectAllAmtInit() {
        return sqlSessionTemplate.selectList("selectAllAmtInit");
    }

    /**
     * 根据奶站查询
     * @param record
     * @return
     */
    @Override
    public List<TSsmMilkmanAmtInit> selectAmtInitByBranchNo(OutMilkModel record) {
        return sqlSessionTemplate.selectList("selectAmtInitByBranchNo",record);
    }
}
