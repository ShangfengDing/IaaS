package com.appcloud.vm.fe.entity;

public class SecurityGroupItem {

	private String SecurityGroupId;//安全组的Id
	private String SecurityGroupName;//安全组的名称
	private String Description;//安全组的描述
	private String providerEn;//提供商
	private String regionId;//地域Id
	private String userEmail;//用户的email

	public SecurityGroupItem(String SecurityGroupId,String SecurityGroupName,String Description,
			String providerEn,String regionId,String userEmail){
		this.SecurityGroupId = SecurityGroupId;
		this.SecurityGroupName = SecurityGroupName;
		this.Description = Description;
		this.providerEn = providerEn;
		this.regionId = regionId;
		this.userEmail = userEmail;
	}

	public String getSecurityGroupId() {
		return SecurityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId) {
		SecurityGroupId = securityGroupId;
	}

	public String getSecurityGroupName() {
		return SecurityGroupName;
	}

	public void setSecurityGroupName(String securityGroupName) {
		SecurityGroupName = securityGroupName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
