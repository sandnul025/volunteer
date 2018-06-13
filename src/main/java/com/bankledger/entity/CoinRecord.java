package com.bankledger.entity;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 下午3:06:46
 */
public class CoinRecord {

	
	private String blockAddrFrom;
	
	private String blockAddrTo;
	
	private String coinNum;
	
	private String projectId;
	
	private String projectName;
	
	private String organize;
	
	private String address;
	
	private String created;
	
	private String txId;
	
	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	public String getTxId() {
		return txId;
	}
	
	public String getBlockAddrFrom() {
		return blockAddrFrom;
	}

	public void setBlockAddrFrom(String blockAddrFrom) {
		this.blockAddrFrom = blockAddrFrom;
	}

	public String getBlockAddrTo() {
		return blockAddrTo;
	}

	public void setBlockAddrTo(String blockAddrTo) {
		this.blockAddrTo = blockAddrTo;
	}

	public String getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(String coinNum) {
		this.coinNum = coinNum;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
}
