package com.wologic.domain;

public class BarCode {

	private String productno;
	
	private String pkgbarcode;
	
	private String productname;
	
	
	private Double price;
	
	private String stock;
	
	private String pinpai;
	
	private String unit;
	
	private String chandi;
	
	/**
	 * ¹æ¸ñ
	 */
	private String guige;
	
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPinpai() {
		return pinpai;
	}

	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChandi() {
		return chandi;
	}

	public void setChandi(String chandi) {
		this.chandi = chandi;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getPkgbarcode() {
		return pkgbarcode;
	}

	public void setPkgbarcode(String pkgbarcode) {
		this.pkgbarcode = pkgbarcode;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	public Double getPkgrate() {
		return pkgrate;
	}

	public void setPkgrate(Double pkgrate) {
		this.pkgrate = pkgrate;
	}

	private String pkgname;
	
	private Double pkgrate;
}
