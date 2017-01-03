package com.nhry.service.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmRedateByTrade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cbz on 12/27/2016.
 */
public interface RedateByTradeService {
    /**
     * 订单完结时，记录奶站促销的返利金额
     * @param orderNo
     * @param branchNo
     * @param salesOrg
     * @param promNo
     * @param matnr
     * @param redate
     * @return
     */
    void insert(String orderNo,String branchNo,String salesOrg,String promNo,String matnr,BigDecimal redate);

    /**
     * 查询未发送的数据，一次10条
     * @return
     */
    List<TSsmRedateByTrade> findNoSendRedateByTrade();

    void update(TSsmRedateByTrade ssmRedateByTrade);
}
