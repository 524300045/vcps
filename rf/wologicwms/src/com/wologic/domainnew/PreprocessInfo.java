package com.wologic.domainnew;

import java.math.BigDecimal;

public class PreprocessInfo {

	/** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** 预加工单号(小包裹号) */
    private String preprocessCode; 
    /** 状态 */
    private Integer status; 
    /** 供应商编码 */
    private String partnerCode; 
    /** 供应商名称 */
    private String partnerName; 
    /** 商品编码 */
    private String skuCode; 
    /** 商品名称 */
    private String goodsName; 
    /** 打包量 */
    private BigDecimal packWeight; 
    /** 包规（数量） */
    private BigDecimal modelNum; 
    /** 计价单位（斤、两） */
    private String goodsUnit; 
    /** 物理单位（包、箱、瓶） */
    private String physicsUnit; 
    /** 创建人 */
    private String operateUser; 
    /** 创建时间 */
    private String operateTime; 
    /** 创建时间 */
    private String createTime; 
    /** 创建人 */
    private String createUser; 
    /** 更新时间 */
    private String updateTime; 
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
