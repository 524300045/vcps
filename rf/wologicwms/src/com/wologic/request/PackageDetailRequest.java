package com.wologic.request;

import java.math.BigDecimal;
import java.util.Date;

public class PackageDetailRequest  {

    /** ���л���ʶ */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** �������񵥺� */
    private String outboundTaskCode; 
    /** ��װ���񵥺� */
    private String packTaskCode; 
    /** ��װ������ϸID */
    private Long packTaskDetailId; 
    /** ��Ʒ���� */
    private String skuCode; 
    /** ��� */
    private String boxCode; 
    /** ������ */
    private String packageCode; 
    /** �������� */
    private BigDecimal weight; 
    /** �ӹ���Ա */
    private String processUser; 
    /** ���� */
    private String container; 
    /** �ӹ�ʱ�� */
    private Date processDate; 
    /** ����״̬ */
    private Integer status; 
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
    
    private String partnerCode;
    
    public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

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
    
    public String getPackTaskCode(){
        return packTaskCode;
    }
        
    public void setPackTaskCode(String packTaskCode) {
        this.packTaskCode = packTaskCode;
    }
    
    public Long getPackTaskDetailId(){
        return packTaskDetailId;
    }
        
    public void setPackTaskDetailId(Long packTaskDetailId) {
        this.packTaskDetailId = packTaskDetailId;
    }
    
    public String getSkuCode(){
        return skuCode;
    }
        
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    
    public String getBoxCode(){
        return boxCode;
    }
        
    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }
    
    public String getPackageCode(){
        return packageCode;
    }
        
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    
    public BigDecimal getWeight(){
        return weight;
    }
        
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public String getProcessUser(){
        return processUser;
    }
        
    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }
    
    public String getContainer(){
        return container;
    }
        
    public void setContainer(String container) {
        this.container = container;
    }
    
    public Date getProcessDate(){
        return processDate;
    }
        
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
    
    public Integer getStatus(){
        return status;
    }
        
    public void setStatus(Integer status) {
        this.status = status;
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