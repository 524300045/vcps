package com.wologic.domainnew;

public class BoxInfo {
	  private static final long serialVersionUID = 1L;
	    
	    /** id */
	    private Long id; 
	    /** �ͻ����� */
	    private String customerCode; 
	    /** �ͻ����� */
	    private String customerName; 
	    /** �ֿ���� */
	    private String warehouseCode; 
	    /** �ֿ����� */
	    private String warehouseName; 
	    /** �ŵ���� */
	    private String storedCode; 
	    /** �ŵ����� */
	    private String storedName; 
	    /** ��� */
	    private String boxCode; 
	    /** ��ӡʱ�� */
	    private String printTime; 
	    /** ��ӡ�� */
	    private String printMan; 
	    /** ����ʱ�� */
	    private String createTime; 
	    /** ������ */
	    private String createUser; 
	    /** ����ʱ�� */
	    private String updateTime; 
	    /** ������ */
	    private String updateUser; 
	    /** �Ƿ���Ч */
	    private Integer yn; 
	    
	    public Long getId(){
	        return id;
	    }
	        
	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public String getCustomerCode(){
	        return customerCode;
	    }
	        
	    public void setCustomerCode(String customerCode) {
	        this.customerCode = customerCode;
	    }
	    
	    public String getCustomerName(){
	        return customerName;
	    }
	        
	    public void setCustomerName(String customerName) {
	        this.customerName = customerName;
	    }
	    
	    public String getWarehouseCode(){
	        return warehouseCode;
	    }
	        
	    public void setWarehouseCode(String warehouseCode) {
	        this.warehouseCode = warehouseCode;
	    }
	    
	    public String getWarehouseName(){
	        return warehouseName;
	    }
	        
	    public void setWarehouseName(String warehouseName) {
	        this.warehouseName = warehouseName;
	    }
	    
	    public String getStoredCode(){
	        return storedCode;
	    }
	        
	    public void setStoredCode(String storedCode) {
	        this.storedCode = storedCode;
	    }
	    
	    public String getStoredName(){
	        return storedName;
	    }
	        
	    public void setStoredName(String storedName) {
	        this.storedName = storedName;
	    }
	    
	    public String getBoxCode(){
	        return boxCode;
	    }
	        
	    public void setBoxCode(String boxCode) {
	        this.boxCode = boxCode;
	    }
	    
	    public String getPrintTime(){
	        return printTime;
	    }
	        
	    public void setPrintTime(String printTime) {
	        this.printTime = printTime;
	    }
	    
	    public String getPrintMan(){
	        return printMan;
	    }
	        
	    public void setPrintMan(String printMan) {
	        this.printMan = printMan;
	    }
	    
	    public String getCreateTime(){
	        return createTime;
	    }
	        
	    public void setCreateTime(String createTime) {
	        this.createTime = createTime;
	    }
	    
	    public String getCreateUser(){
	        return createUser;
	    }
	        
	    public void setCreateUser(String createUser) {
	        this.createUser = createUser;
	    }
	    
	    public String getUpdateTime(){
	        return updateTime;
	    }
	        
	    public void setUpdateTime(String updateTime) {
	        this.updateTime = updateTime;
	    }
	    
	    public String getUpdateUser(){
	        return updateUser;
	    }
	        
	    public void setUpdateUser(String updateUser) {
	        this.updateUser = updateUser;
	    }
	    
	    public Integer getYn(){
	        return yn;
	    }
	        
	    public void setYn(Integer yn) {
	        this.yn = yn;
	    }
}
