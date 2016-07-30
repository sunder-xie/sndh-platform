package com.nhry.model.order;

import com.wordnik.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/6/21.
 */
@ApiModel(value = "UpdateManHandOrderModel", description = "更新人工分单中的所属奶站对象")
public class UpdateManHandOrderModel implements Serializable {

    private String orderNo;
    private String branchNo;
    private String salesOrg;

    public String getSalesOrg()
	{
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg)
	{
		this.salesOrg = salesOrg;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}