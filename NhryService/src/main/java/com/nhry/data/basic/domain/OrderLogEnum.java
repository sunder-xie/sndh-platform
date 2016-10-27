package com.nhry.data.basic.domain;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/10/24.
 */
public class OrderLogEnum implements Serializable {
    public final static  String CREATE_ORDER = "创建订单";
    public final static  String MEMO_TXT = "备注";
    public final static  String ADD_PRODUCT = "新增产品";
    public final static  String CHANGE_PRODUCT = "变更品种";
    public final static  String CHANGE_SUM = "变更数量";
    public final static  String CHANGE_RULE = "变更配送规律";
    public final static  String STOP_ORDER = "停订";
    public final static  String DEL_ORDER = "删除";
    public final static  String CHANGE_EMP = "送奶员";
    public final static  String CTN_ORDER = "续订";
    public final static  String STATUS = "状态";
}
