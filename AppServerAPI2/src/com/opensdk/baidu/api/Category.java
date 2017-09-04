package com.opensdk.baidu.api;

public class Category {
	private String shop_id;//商户ID
	private String nambai_shop_ide;//百度商户ID,与shop_id二选一
	private String name;//分类名称
	private int rank;//排序;降序;
	private int must;//设置必选分类:1必选;0解除必选

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getNambai_shop_ide() {
		return nambai_shop_ide;
	}

	public void setNambai_shop_ide(String nambai_shop_ide) {
		this.nambai_shop_ide = nambai_shop_ide;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getMust() {
		return must;
	}

	public void setMust(int must) {
		this.must = must;
	}
}
