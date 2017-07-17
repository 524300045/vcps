package com.wologic.domainnew;

import java.math.BigDecimal;
import java.util.Date;

public class OutBound {

	 /** ���л���ʶ */
    private static final long serialVersionUID = 1L;
    
    /** ���� */
    private Long id; 
    /** �������񵥺� */
    private String outboundTaskCode; 
    /** ״̬ */
    private Integer status; 
    /** �Ƿ��ӡ */
    private Integer isPrint; 
    /** ��ӡʱ�� */
    private Date printTime; 
    /** ��ӡ�� */
    private String printMan; 
    /** �Ƿ�ּ���� */
    private Integer isSorting; 
    /** �ּ����ʱ�� */
    private Date finishSortingTime; 
    /** �Ƿ������ */
    private Integer isDelivery; 
    /** ����ʱ�� */
    private Date deliveryTime; 
    /** ������ */
    private String deliveryMan; 
    /** ��·���� */
    private String lineCode; 
    /** �������� */
    private String vehicleCode; 
    /** �ɹ����� */
    private String orderNo; 
    /** �������� */
    private Integer orderType; 
    /** ������Դ */
    private Integer orderSource; 
    /** ҵ������ */
    private Integer businessType; 
    /** �ֿ���� */
    private String warehouseCode; 
    /** ���б��� */
    private String regionCode; 
    /** �ŵ���� */
    private String storedCode; 
    
    /** �ŵ���� */
    private String storedName; 
    
    /**
     * ��ϸ������ɵ�����
     */
    private Integer finishNum;
    
    /**
     * ��ϸ���ܵ�����
     */
    private Integer totalNum;
    
    public Integer getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	/** ԭ���ݱ�� */
    private String originOrderNo; 
    /** �ͻ����� */
    private String customerCode; 
    /** ҵ��Ա */
    private String salesman; 
    /** �������� */
    private String paymentClause; 
    /** ���� */
    private Integer currency; 
    /** ���� */
    private BigDecimal exchangeRate; 
    /** ��ע */
    private String remark; 
    /** �������� */
    private Date orderDate; 
    /** �������� */
    private Date deliveryDate; 
    /** ����ʱ�� */
    private Date operateTime; 
    /** ����ʱ�� */
    private Date createTime; 
    /** ������ */
    private String createUser; 
    /** ����ʱ�� */
    private Date updateTime; 
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
    
    public String getOutboundTaskCode(){
        return outboundTaskCode;
    }
        
    public void setOutboundTaskCode(String outboundTaskCode) {
        this.outboundTaskCode = outboundTaskCode;
    }
    
    public Integer getStatus(){
        return status;
    }
        
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getIsPrint(){
        return isPrint;
    }
        
    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
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
    
    public Integer getIsSorting(){
        return isSorting;
    }
        
    public void setIsSorting(Integer isSorting) {
        this.isSorting = isSorting;
    }
    
    public Date getFinishSortingTime(){
        return finishSortingTime;
    }
        
    public void setFinishSortingTime(Date finishSortingTime) {
        this.finishSortingTime = finishSortingTime;
    }
    
    public Integer getIsDelivery(){
        return isDelivery;
    }
        
    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }
    
    public Date getDeliveryTime(){
        return deliveryTime;
    }
        
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    public String getDeliveryMan(){
        return deliveryMan;
    }
        
    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
    
    public String getLineCode(){
        return lineCode;
    }
        
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
    
    public String getVehicleCode(){
        return vehicleCode;
    }
        
    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }
    
    public String getOrderNo(){
        return orderNo;
    }
        
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public Integer getOrderType(){
        return orderType;
    }
        
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    
    public Integer getOrderSource(){
        return orderSource;
    }
        
    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }
    
    public Integer getBusinessType(){
        return businessType;
    }
        
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
    
    public String getWarehouseCode(){
        return warehouseCode;
    }
        
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    
    public String getRegionCode(){
        return regionCode;
    }
        
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    public String getStoredCode(){
        return storedCode;
    }
        
    public void setStoredCode(String storedCode) {
        this.storedCode = storedCode;
    }
    
    public String getOriginOrderNo(){
        return originOrderNo;
    }
        
    public void setOriginOrderNo(String originOrderNo) {
        this.originOrderNo = originOrderNo;
    }
    
    public String getCustomerCode(){
        return customerCode;
    }
        
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getSalesman(){
        return salesman;
    }
        
    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }
    
    public String getPaymentClause(){
        return paymentClause;
    }
        
    public void setPaymentClause(String paymentClause) {
        this.paymentClause = paymentClause;
    }
    
    public Integer getCurrency(){
        return currency;
    }
        
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
    
    public BigDecimal getExchangeRate(){
        return exchangeRate;
    }
        
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getRemark(){
        return remark;
    }
        
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Date getOrderDate(){
        return orderDate;
    }
        
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Date getDeliveryDate(){
        return deliveryDate;
    }
        
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public Date getOperateTime(){
        return operateTime;
    }
        
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
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
