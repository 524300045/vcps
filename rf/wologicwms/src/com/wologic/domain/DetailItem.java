package com.wologic.domain;

public class DetailItem {

	private String rukuno;
	
	private String productno;
	
	private Double productqty;
	
	private  String sectionid;
	
	private String sizeid;
	
	
	private String barcode;
	
	private String itemtype;

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRukuno() {
		return rukuno;
	}

	public void setRukuno(String rukuno) {
		this.rukuno = rukuno;
	}

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public Double getProductqty() {
		return productqty;
	}

	public void setProductqty(Double productqty) {
		this.productqty = productqty;
	}

	public String getSectionid() {
		return sectionid;
	}

	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}

	public String getSizeid() {
		return sizeid;
	}

	public void setSizeid(String sizeid) {
		this.sizeid = sizeid;
	}
}
