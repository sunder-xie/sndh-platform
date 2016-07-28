package com.nhry.model.order;

import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.order.domain.TPreOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gongjk on 2016/7/18.
 */
public class CollectOrderModel {
    private TPreOrder order;
    private List<ProductItem> entries;
    private BigDecimal totalPrice;
    private TMdAddress address;


    public TMdAddress getAddress() {
        return address;
    }

    public void setAddress(TMdAddress address) {
        this.address = address;
    }

    public TPreOrder getOrder() {
        return order;
    }

    public void setOrder(TPreOrder order) {
        this.order = order;
    }

    public List<ProductItem> getEntries() {
        return entries;
    }

    public void setEntries(List<ProductItem> entries) {
        this.entries = entries;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
