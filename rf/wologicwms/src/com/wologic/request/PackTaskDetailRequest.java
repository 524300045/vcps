package com.wologic.request;

import java.math.BigDecimal;
import java.util.Date;

public class PackTaskDetailRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** 包装任务单号 */
    private String packTaskCode; 
    /** 状态 */
    private Integer status; 
    /** 商品编码 */
    private String skuCode; 
    /** 商品名称 */
    private String goodsName; 
    /** 是否生鲜 1：是 0：否 */
    private Integer isFresh; 
    /** 是否称重 1：是 0：否 */
    private Integer weighed; 
    /** 门店编码 */
    private String storedCode; 
    /** 门店名称 */
    private String storedName; 
    /** 包规（数量） */
    private BigDecimal modelNum; 
    /** 计价单位（斤、两） */
    private String goodsUnit; 
    /** 物理单位（包、箱、瓶） */
    private String physicsUnit; 
    /** 计划数量 */
    private BigDecimal planNum; 
    /** 计划上限数量 */
    private BigDecimal upPlanNum; 
    /** 计划下限数量 */
    private BigDecimal downPlanNum; 
    /** 创建时间 */
    private Date operateTime; 
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
    
    private String partnerCode ;
    
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
    
    public String getPackTaskCode(){
        return packTaskCode;
    }
        
    public void setPackTaskCode(String packTaskCode) {
        this.packTaskCode = packTaskCode;
    }
    
    public Integer getStatus(){
        return status;
    }
        
    public void setStatus(Integer status) {
        this.status = status;
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
    
    public Integer getIsFresh(){
        return isFresh;
    }
        
    public void setIsFresh(Integer isFresh) {
        this.isFresh = isFresh;
    }
    
    public Integer getWeighed(){
        return weighed;
    }
        
    public void setWeighed(Integer weighed) {
        this.weighed = weighed;
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
    
    public BigDecimal getPlanNum(){
        return planNum;
    }
        
    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }
    
    public BigDecimal getUpPlanNum(){
        return upPlanNum;
    }
        
    public void setUpPlanNum(BigDecimal upPlanNum) {
        this.upPlanNum = upPlanNum;
    }
    
    public BigDecimal getDownPlanNum(){
        return downPlanNum;
    }
        
    public void setDownPlanNum(BigDecimal downPlanNum) {
        this.downPlanNum = downPlanNum;
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
