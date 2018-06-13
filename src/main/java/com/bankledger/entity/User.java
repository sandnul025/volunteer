package com.bankledger.entity;

import java.math.BigDecimal;

public class User {

	/**
	 * 资产地址
	 */
	private String blockAddr;

	private String idCard;
	
	private String nickName;

	private String password;
	
	/**
	 * 2 普通用户
	 * 1 组织
	 * 0 管理员
	 */
	private String type;
	
	private BigDecimal coinCount;
	
	private String appId;
	
	private String txId;
	
	private String descript;
	
	private String sex;
	
	private String skills;
	
	private String country;
	
	private String nation;
	
	private String major;
	
	private String school;
	
	private String censusregister;
	
	private String politicallandscape;
	
	private String birthdate;
	
	private String isopen;
	
	/**
	 * @param isopen the isopen to set
	 */
	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}
	
	/**
	 * @return the isopen
	 */
	public String getIsopen() {
		return isopen;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCensusregister() {
		return censusregister;
	}

	public void setCensusregister(String censusregister) {
		this.censusregister = censusregister;
	}

	public String getPoliticallandscape() {
		return politicallandscape;
	}

	public void setPoliticallandscape(String politicallandscape) {
		this.politicallandscape = politicallandscape;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	// 额外 参数
	private String isCome;
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setIsCome(String isCome) {
		this.isCome = isCome;
	}
	 
	public String getIsCome() {
		return isCome;
	}
	
	public void setCoinCount(BigDecimal coinCount) {
		this.coinCount = coinCount;
	}
	
	public BigDecimal getCoinCount() {
		return coinCount;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public String getBlockAddr() {
		return blockAddr;
	}

	public void setBlockAddr(String blockAddr) {
		this.blockAddr = blockAddr;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
