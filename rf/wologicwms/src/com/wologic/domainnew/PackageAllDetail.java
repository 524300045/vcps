package com.wologic.domainnew;

import java.math.BigDecimal;
import java.util.Date;

public class PackageAllDetail implements java.io.Serializable  {

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
    private String operateTime; 
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
    
    /** ���棨������ */
    private BigDecimal modelNum; 
    /** �Ƽ۵�λ������� */
    private String goodsUnit; 
    /** ����λ�������䡢ƿ�� */
    private String physicsUnit; 
    
    /**
     * �ŵ����
     */
    private String storeProcess;
    
    /**
     * �ܽ���
     */
    private String totalProcess;
    
    public String getStoreProcess() {
		return storeProcess;
	}

	public void setStoreProcess(String storeProcess) {
		this.storeProcess = storeProcess;
	}

	public String getTotalProcess() {
		return totalProcess;
	}

	public void setTotalProcess(String totalProcess) {
		this.totalProcess = totalProcess;
	}

	public BigDecimal getModelNum() {
		return modelNum;
	}

	public void setModelNum(BigDecimal modelNum) {
		this.modelNum = modelNum;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getPhysicsUnit() {
		return physicsUnit;
	}

	public void setPhysicsUnit(String physicsUnit) {
		this.physicsUnit = physicsUnit;
	}

	/** ��Ʒ���� */
    private String goodsName; 
    
    public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/** �ŵ���� */
    private String storedCode; 
    /** �ŵ����� */
    private String storedName; 
    
    public String getStoredCode() {
		return storedCode;
	}

	public void setStoredCode(String storedCode) {
		this.storedCode = storedCode;
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
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
    
    public String getOperateTime(){
        return operateTime;
    }
        
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
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
