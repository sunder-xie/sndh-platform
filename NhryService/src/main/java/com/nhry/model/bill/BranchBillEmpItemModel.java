package com.nhry.model.bill;

/**
 * Created by gongjk on 2016/6/29.
 */
public class BranchBillEmpItemModel {
    private String matnr;
    private String matnrTxt;
    private int qty;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMatnrTxt() {
        return matnrTxt;
    }

    public void setMatnrTxt(String matnrTxt) {
        this.matnrTxt = matnrTxt;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
