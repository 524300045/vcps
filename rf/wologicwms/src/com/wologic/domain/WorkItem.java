package com.wologic.domain;

import java.util.Date;

public class WorkItem {

	/**
	 * ����
	 */
	private String itemno;
	
	/**
	 * ��������
	 */
	private  String itemtype;
	
	/**
	 * ״̬
	 */
	private String itemstate ;
	
	/**
	 * �ܵ�����
	 */
	private Double totalNum;
	
	/**
	 * �ܵ�����
	 */
	private Integer totalCount;
	
	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * ʱ��
	 */
	private Date worktime;

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getItemstate() {
		return itemstate;
	}

	public void setItemstate(String itemstate) {
		this.itemstate = itemstate;
	}

	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}
}
