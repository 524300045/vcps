package com.wologic.request;

import java.math.BigDecimal;
import java.util.Date;

public class PackageDetailRequest  {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    /** id */
    private Long id; 
    /** 出库任务单号 */
    private String outboundTaskCode; 
    /** 包装任务单号 */
    private String packTaskCode; 
    /** 包装任务明细ID */
    private Long packTaskDetailId; 
    /** 商品编码 */
    private String skuCode; 
    /** 箱号 */
    private String boxCode; 
    /** 包裹号 */
    private String packageCode; 
    /** 包裹重量 */
    private BigDecimal weight; 
    /** 加工人员 */
    private String processUser; 
    /** 容器 */
    private String container; 
    /** 加工时间 */
    private Date processDate; 
    /** 包裹状态 */
    private Integer status; 
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