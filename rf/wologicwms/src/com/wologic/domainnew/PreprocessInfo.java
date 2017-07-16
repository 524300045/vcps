package com.wologic.domainnew;

import java.math.BigDecimal;

public class PreprocessInfo {

	/** ���л���ʶ */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** Ԥ�ӹ�����(С������) */
    private String preprocessCode; 
    /** ״̬ */
    private Integer status; 
    /** ��Ӧ�̱��� */
    private String partnerCode; 
    /** ��Ӧ������ */
    private String partnerName; 
    /** ��Ʒ���� */
    private String skuCode; 
    /** ��Ʒ���� */
    private String goodsName; 
    /** ����� */
    private BigDecimal packWeight; 
    /** ���棨������ */
    private BigDecimal modelNum; 
    /** �Ƽ۵�λ������� */
    private String goodsUnit; 
    /** ����λ�������䡢ƿ�� */
    private String physicsUnit; 
    /** ������ */
    private String operateUser; 
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
    
    public Long getId(){
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPreprocessCode(){
        return preprocessCode;
    }
        
    public void setPreprocessCode(String preprocessCode) {
        this.preprocessCode = preprocessCode;
    }
    
    public Integer getStatus(){
        return status;
    }
        
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getPartnerCode(){
        return partnerCode;
    }
        
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    
    public String getPartnerName(){
        return partnerName;
    }
        
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    
    public String getSkuCode(){
        return skuCode;
    }
        
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    
    public String getGoodsName(){
        return goodsName;
    }
        
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    public BigDecimal getPackWeight(){
        return packWeight;
    }
        
    public void setPackWeight(BigDecimal packWeight) {
        this.packWeight = packWeight;
    }
    
    public BigDecimal getModelNum(){
        return modelNum;
    }
        
    public void setModelNum(BigDecimal modelNum) {
        this.modelNum = modelNum;
    }
    
    public String getGoodsUnit(){
        return goodsUnit;
    }
        
    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }
    
    public String getPhysicsUnit(){
        return physicsUnit;
    }
        
    public void setPhysicsUnit(String physicsUnit) {
        this.physicsUnit = physicsUnit;
    }
    
    public String getOperateUser(){
        return operateUser;
    }
        
    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
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
