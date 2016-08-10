package com.nhry.data.promotion.domain;

public class PromotionScopeItem extends PromotionScopeItemKey {
    private String branchNo;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo == null ? null : branchNo.trim();
    }
}