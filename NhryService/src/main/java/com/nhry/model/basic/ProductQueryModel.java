package com.nhry.model.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProductSearchModel", description = "商品信息列表查询对象")
public class ProductQueryModel extends BaseQueryModel implements Serializable {
	@ApiModelProperty(value="brandLib",notes="商品品牌")
	private String brandLib;
	private String catalog;
	private String status;
	public String getBrandLib()
	{
		return brandLib;
	}
	public void setBrandLib(String brandLib)
	{
		this.brandLib = brandLib;
	}
	public String getCatalog()
	{
		return catalog;
	}
	public void setCatalog(String catalog)
	{
		this.catalog = catalog;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
