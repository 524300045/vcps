package com.wologic.response;

import com.wologic.domainnew.PackageAllDetail;


public class PackageAllDetailResponse implements java.io.Serializable  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    private String code;
    
    private String message;
    
    private PackageAllDetail result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PackageAllDetail getResult() {
		return result;
	}

	public void setResult(PackageAllDetail result) {
		this.result = result;
	}
}
