package com.wologic.domainnew;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Vehicle锛氳溅杈嗚〃瀹炰綋绫�
 * 
 * @author jinsicao
 * 
 */
public class VersionInfo implements java.io.Serializable  {

    /** 搴忓垪鍖栨爣璇� */
	private static final long serialVersionUID = 1L;
	
    /** 涓婚敭id */
    private Long id; 
    /** 绯荤粺缂栫爜 */
    private String systemCode; 
    /**绯荤粺鍚嶇О */
    private String systemName; 
    /** 鐗堟湰鍙�*/
    private String versionCode; 
    
    private String url; 
    /** 杞﹁締瀹斤紙M锛� */
    private String remark; 
    
  
  
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 鏈夋晥鏍囩ず锛�1锛氭湁鏁堬紱0锛氭棤鏁堬級 */
    private Integer yn; 
    
  
}
