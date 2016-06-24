package com.nhry.model.bill;

import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.order.domain.TPlanOrderItem;

import java.util.List;

/**
 * Created by gongjk on 2016/6/23.
 */
public class CustomerBillOrder {
    private TMstRecvBill bill;
    List<TPlanOrderItem> entrys;

    public TMstRecvBill getBill() {
        return bill;
    }

    public void setBill(TMstRecvBill bill) {
        this.bill = bill;
    }

    public List<TPlanOrderItem> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<TPlanOrderItem> entrys) {
        this.entrys = entrys;
    }
}
