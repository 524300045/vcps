package com.wologic.domainnew;

import java.util.List;

public class User {


    private long id ;

    private String name ;

    private String cnName ;

    private String email ;

    private String companyName ;

    private String companyCode ;
    
    private List<Menu> menuDtos ;

	public List<Menu> getMenuDtos() {
		return menuDtos;
	}

	public void setMenuDtos(List<Menu> menuDtos) {
		this.menuDtos = menuDtos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
