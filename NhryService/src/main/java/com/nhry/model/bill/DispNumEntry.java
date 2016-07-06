package com.nhry.model.bill;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/7/4.
 */
public class DispNumEntry {
    private String num;
    private BigDecimal dispRate;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public BigDecimal getDispRate() {
        return dispRate;
    }

    public void setDispRate(BigDecimal dispRate) {
        this.dispRate = dispRate;
    }
}
