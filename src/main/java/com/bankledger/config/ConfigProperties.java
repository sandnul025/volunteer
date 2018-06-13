package com.bankledger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月7日 上午11:00:13
 */
@Component
@PropertySource(value = {"classpath:config/config.properties"})
public class ConfigProperties {
   
    @Value("${adminAddress}")
    private String adminAddress;
    
    @Value("${adminAddressAppId}")
    private String adminAddressAppId;
    
    @Value("${rpcHost}")
    private String rpcHost;
    
    
    public String getRpcHost() {
		return rpcHost;
	}

	public void setRpcHost(String rpcHost) {
		this.rpcHost = rpcHost;
	}

	public String getAdminAddress() {
		return adminAddress;
	}

	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public String getAdminAddressAppId() {
		return adminAddressAppId;
	}

	public void setAdminAddressAppId(String adminAddressAppId) {
		this.adminAddressAppId = adminAddressAppId;
	}


}
