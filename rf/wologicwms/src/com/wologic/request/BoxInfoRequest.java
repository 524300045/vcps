package com.wologic.request;

import java.util.Date;

public class BoxInfoRequest  {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** 客户编码 */
    private String customerCode; 
    /** 客户名称 */
    private String customerName; 
    /** 仓库编码 */
    private String warehouseCode; 
    /** 仓库名称 */
    private String warehouseName; 
    /** 门店编码 */
    private String storedCode; 
    /** 门店名称 */
    private String storedName; 
    /** 箱号 */
    private String boxCode; 
    /** 打印时间 */
    private Date printTime; 
    /** 打印人 */
    private String printMan; 
    /** 创建时间 */
    private Date createTime; 
    /** 创建人 */
    private String createUser; 
    /** 更新时间 */
    private Date updateTime; 
    /** 更新人 */
    private String updateUser; 
    /** 是否有效 */
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
    
    public Date getPrintTime(){
        return printTime;
    }
        
    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }
    
    public String getPrintMan(){
        return printMan;
    }
        
    public void setPrintMan(String printMan) {
        this.printMan = printMan;
    }
    
    public Date getCreateTime(){
        return createTime;
    }
        
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getCreateUser(){
        return createUser;
    }
        
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public Date getUpdateTime(){
        return updateTime;
    }
        
    public void setUpdateTime(Date updateTime) {
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
