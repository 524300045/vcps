package com.wologic.domain;

public class Product {

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	private String productno;
	
	private String productname;
	
	/**
	 * 产地
	 */
	private String proinfo10 ;
	
	/**
	 * 规格
	 */
	private String proinfo9;
	
	public String getProinfo10() {
		return proinfo10;
	}

	public void setProinfo10(String proinfo10) {
		this.proinfo10 = proinfo10;
	}

	public String getProinfo9() {
		return proinfo9;
	}

	public void setProinfo9(String proinfo9) {
		this.proinfo9 = proinfo9;
	}

	/**
	 * 价格
	 */
	private String proinfo11;

	public String getProinfo11() {
		return proinfo11;
	}

	public void setProinfo11(String proinfo11) {
		this.proinfo11 = proinfo11;
	}
	
	/**
	 * 库存
	 */
	private String proinfo8;
	
	public String getProinfo8() {
		return proinfo8;
	}

	public void setProinfo8(String proinfo8) {
		this.proinfo8 = proinfo8;
	}

	public String getProinfo7() {
		return proinfo7;
	}

	public void setProinfo7(String proinfo7) {
		this.proinfo7 = proinfo7;
	}

	/**
	 * 品牌
	 */
	private String proinfo7;
	
}
