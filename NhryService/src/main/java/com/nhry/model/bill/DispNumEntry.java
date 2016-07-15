package com.nhry.model.bill;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/7/4.
 */
public class DispNumEntry {
    private String startValue;
    private String endValue;
    private BigDecimal rate;
    public String getStartValue() {
        return startValue;
    }
    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }
    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
