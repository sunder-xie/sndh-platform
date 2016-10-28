package com.nhry.data.basic.domain;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/10/24.
 */
public class DailOrderLogEnum implements Serializable {
    public final static  String REACH_TIME = "修改配送时间";
    public final static  String UPT_MATNR = "修改产品";
    public final static  String UPT_QTY = "修改数量";
    public final static  String UPT_MATNR_QTY = "修改产品和数量";
    public final static  String STATUS = "状态";
}