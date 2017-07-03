package com.wologic.domain;

import java.util.Date;

public class WBaseInfo {

	private String ClientId;
	
	private String VersionId;
	
	private String infoId;
	
	private String infoname;
	
	private String IsShow;
	
	private Date updatetime;
	
	private String base1;
	
	private String base2;
	
	private String base3;

	public String getClientId() {
		return ClientId;
	}

	public void setClientId(String clientId) {
		ClientId = clientId;
	}

	public String getVersionId() {
		return VersionId;
	}

	public void setVersionId(String versionId) {
		VersionId = versionId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoname() {
		return infoname;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}

	public String getIsShow() {
		return IsShow;
	}

	public void setIsShow(String isShow) {
		IsShow = isShow;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getBase1() {
		return base1;
	}

	public void setBase1(String base1) {
		this.base1 = base1;
	}

	public String getBase2() {
		return base2;
	}

	public void setBase2(String base2) {
		this.base2 = base2;
	}

	public String getBase3() {
		return base3;
	}

	public void setBase3(String base3) {
		this.base3 = base3;
	}
}
