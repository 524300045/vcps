package com.wologic.domain;

import java.util.Date;

public class ItemData {

	/**
	 * ����
	 */
	private String itemno ;
	
	/**
	 * ��Ʒ����
	 */
	private String productno ;
	
	/**
	 * ��Ʒ����
	 */
	private String productname;
	
	/**
	 * ����
	 */
	private String pkgbarcode;
	
	/**
	 * ��λ
	 */
	private String Info1;
	
/*
 * 0δ��� 1�����
 */
	private String Info2;
	

	public String getInfo2() {
		return Info2;
	}

	public void setInfo2(String info2) {
		Info2 = info2;
	}

	public String getInfo1() {
		return Info1;
	}

	public void setInfo1(String info1) {
		Info1 = info1;
	}

	public Double getWorkamount() {
		return workamount;
	}

	public void setWorkamount(Double workamount) {
		this.workamount = workamount;
	}

	private Double pkgrate;
	
	private String price;
	
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPkgbarcode() {
		return pkgbarcode;
	}

	public void setPkgbarcode(String pkgbarcode) {
		this.pkgbarcode = pkgbarcode;
	}

	public Double getPkgrate() {
		return pkgrate;
	}

	public void setPkgrate(Double pkgrate) {
		this.pkgrate = pkgrate;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	/**
	 * ��װ���� ����
	 */
	private String pkgname;
	
	
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * �ƻ�����
	 */
	private Double planamount ;
	
	/**
	 * �������
	 */
	private Double completeamount ;
	
	/**
	 * ����������
	 */
	private Double workamount ;
	
	/**
	 * �û�ID
	 */
	private String userid ;
	
	/**
	 * �豸ID 
	 */
	private String deviceid ;
	
	/**
	 * ״̬
	 */
	private String state ;
	
	/*
	 * ʱ��
	 */
	private Date worktime;

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public Double getPlanamount() {
		return planamount;
	}

	public void setPlanamount(Double planamount) {
		this.planamount = planamount;
	}

	public Double getCompleteamount() {
		return completeamount;
	}

	public void setCompleteamount(Double completeamount) {
		this.completeamount = completeamount;
	}

	public Double getWork_amount() {
		return workamount;
	}

	public void setWork_amount(Double work_amount) {
		this.workamount = work_amount;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}
}
