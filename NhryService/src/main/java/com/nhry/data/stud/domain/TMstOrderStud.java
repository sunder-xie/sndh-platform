package com.nhry.data.stud.domain;

import java.util.Date;
import java.util.List;


/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class TMstOrderStud {
    private String orderId;

    private Date orderDate;

    private String schoolCode;
    
    private String schoolName;//

    private String orderStatus;

    private Date createAt;

    private String createBy;

    private String createByTxt;

    private Date lastModified;

    private String lastModifiedBy;

    private String lastModifiedByTxt;

    private String remark;
    
    private String salesOrg;//销售组织
    
    private String orderDateStr;//订单日期（目标日期）
    
    private String takeOrderDateStr;//取数的订单日期
    
    private List<TMstOrderStudItem> list10;//学生奶
    
    private List<TMstOrderStudItem> list20;//老师奶
    
    private List<TMstOrderStudLoss> list30;//损耗
    
    private String list10Sum;
    
    private String list20Sum;
    
    private String list30Sum;
    
    private String matnr;//
    

    public String getTakeOrderDateStr() {
		return takeOrderDateStr;
	}

	public void setTakeOrderDateStr(String takeOrderDateStr) {
		this.takeOrderDateStr = takeOrderDateStr;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getList10Sum() {
		return list10Sum;
	}

	public void setList10Sum(String list10Sum) {
		this.list10Sum = list10Sum;
	}

	public String getList20Sum() {
		return list20Sum;
	}

	public void setList20Sum(String list20Sum) {
		this.list20Sum = list20Sum;
	}

	public String getList30Sum() {
		return list30Sum;
	}

	public void setList30Sum(String list30Sum) {
		this.list30Sum = list30Sum;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public List<TMstOrderStudItem> getList10() {
		return list10;
	}

	public void setList10(List<TMstOrderStudItem> list10) {
		this.list10 = list10;
	}

	public List<TMstOrderStudItem> getList20() {
		return list20;
	}

	public void setList20(List<TMstOrderStudItem> list20) {
		this.list20 = list20;
	}

	public List<TMstOrderStudLoss> getList30() {
		return list30;
	}

	public void setList30(List<TMstOrderStudLoss> list30) {
		this.list30 = list30;
	}

	public String getOrderDateStr() {
		return orderDateStr;
	}

	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode == null ? null : schoolCode.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateByTxt() {
        return createByTxt;
    }

    public void setCreateByTxt(String createByTxt) {
        this.createByTxt = createByTxt == null ? null : createByTxt.trim();
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy == null ? null : lastModifiedBy.trim();
    }

    public String getLastModifiedByTxt() {
        return lastModifiedByTxt;
    }

    public void setLastModifiedByTxt(String lastModifiedByTxt) {
        this.lastModifiedByTxt = lastModifiedByTxt == null ? null : lastModifiedByTxt.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}