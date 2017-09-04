package com.opensdk.baidu.api;

public class Shop {
	private String shopId;
	private String name;
	private String baidu_shop_id;

	public String getBaidu_shop_id() {
		return baidu_shop_id;
	}

	public void setBaidu_shop_id(String baidu_shop_id) {
		this.baidu_shop_id = baidu_shop_id;
	}

	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
