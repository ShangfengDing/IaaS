package com.appcloud.vm.action.enterprise;

import java.util.Date;
import java.util.UUID;

import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class ChangeInformationAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	
	private String userId;
	private String enterpriseId;
	private String name;
	private String discription;
	private String phone; 
	private String email;
	private String address; 
	private String post;
	private String homepage;
	private String result;
	
	public String execute() {
		Integer enterpriseIdInt = null;
		if(userId == null || userId.equals("") || 
				enterpriseId == null || enterpriseId.equals("")) {
			this.result = "fail";
			return SUCCESS;
		} 
		
		try {
			enterpriseIdInt = Integer.valueOf(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
			this.result = "fail";
			return SUCCESS;
		}
		
		AcUser acUser = acUserClient.get(userId);
		Enterprise enterprise = enterpriseClient.get(enterpriseIdInt);
		
		if(acUser == null || 
				!acUser.enterpriseId.equals(enterpriseIdInt)) {
			this.result = "fail";
		} else if(enterprise == null){
			this.result = "fail";
		} else {
			enterprise.name = name;
			enterprise.description = discription;
			enterprise.phone = phone;
			enterprise.email = email;
			enterprise.address = address;
			enterprise.postcode = post;
			enterprise.homepage = homepage;
			
			enterpriseClient.update(enterpriseIdInt, enterprise);
			this.result = "success";
			
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "修改企业信息", this.getSessionUserEmail()+"修改企业信息",
					"ChangeInformationAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		}
		return SUCCESS;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
