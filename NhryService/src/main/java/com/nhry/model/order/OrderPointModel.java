package com.nhry.model.order;

/**
 * Created by cbz on 8/17/2016.
 */
public class OrderPointModel {
    String orderNo;
    String itemNo;
    String yfresh;
    String ygrowth;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getYfresh() {
        return yfresh;
    }

    public void setYfresh(String yfresh) {
        this.yfresh = yfresh;
    }

    public String getYgrowth() {
        return ygrowth;
    }

    public void setYgrowth(String ygrowth) {
        this.ygrowth = ygrowth;
    }
}
