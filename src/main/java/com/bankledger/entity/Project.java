package com.bankledger.entity;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月4日 下午10:38:48
 */
public class Project {

	
	private String projectId;
	
	private String blockAddr;
	
	public void setBlockAddr(String blockAddr) {
		this.blockAddr = blockAddr;
	}
	
	public String getBlockAddr() {
		return blockAddr;
	}
	
	private String projectType;
	
	private String projectName;
	
	private String organize;
	
	private String startTime;
	
	private String endTime;
	
	private String address;
	
	private String limitCount;
	
	private String coin;
	
	/**
	 * 1 yes 0 no
	 */
	private String limitOrganizeFlag;
	
	private String discript;
	
	private String created;
	
	
	// 0创建完成  项目创建 成功
	// 1待审核 由发布者点击提交审核  管理员进行审核
	// 2进行中
	// 3结束
	// 
	private String status;
	
	private String txId;
	
	/**
	 * @param txId the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	/**
	 * @return the txId
	 */
	public String getTxId() {
		return txId;
	}
	
	private String isCome;
	
	public void setIsCome(String isCome) {
		this.isCome = isCome;
	}
	
	public String getIsCome() {
		return isCome;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getLimitOrganizeFlag() {
		return limitOrganizeFlag;
	}

	public void setLimitOrganizeFlag(String limitOrganizeFlag) {
		this.limitOrganizeFlag = limitOrganizeFlag;
	}

	public String getDiscript() {
		return discript;
	}

	public void setDiscript(String discript) {
		this.discript = discript;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
