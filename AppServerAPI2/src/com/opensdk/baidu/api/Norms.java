package com.opensdk.baidu.api;

public class Norms {
	private String selfid;//第三方规格ID
	private String stock;//每日库存
	private String is_select;//设置默认属性（1. 设为默认， 0 ，一般属性）
	private String value;//规格名称值
	private String price;//价格,单位:分

	public String getSelfid() {
		return selfid;
	}

	public void setSelfid(String selfid) {
		this.selfid = selfid;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getIs_select() {
		return is_select;
	}

	public void setIs_select(String is_select) {
		this.is_select = is_select;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
