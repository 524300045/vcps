package com.wologic.domain;

import java.util.Date;

public class WorkItem {

	/**
	 * 单号
	 */
	private String itemno;
	
	/**
	 * 单据类型
	 */
	private  String itemtype;
	
	/**
	 * 状态
	 */
	private String itemstate ;
	
	/**
	 * 总的数量
	 */
	private Double totalNum;
	
	/**
	 * 总的条数
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
	 * 时间
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
