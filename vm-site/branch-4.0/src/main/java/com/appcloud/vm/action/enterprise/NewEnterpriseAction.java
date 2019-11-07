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


public class NewEnterpriseAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	
	private String userId;
	private String name;
	private String discription;
	private String phone; 
	private String email;
	private String address; 
	private String post;
	private String homepage;
	private String result;
	
	
	public String execute() {
		Enterprise enterprise = new Enterprise();
		if(userId == null || userId.equals("") ||
		   name == null || name.equals("") ||
		   phone == null || phone.equals("") ||
		   email == null || email.equals("") ||
		   discription == null || discription.equals("")) {
			this.result = "fail";
			return SUCCESS;
		}
		
		AcUser acUser = acUserClient.get(userId);
		if(acUser.enterpriseId != null) {
			this.result = "member_of_enterprise";
			return SUCCESS;
		}
		
		enterprise.id = Integer.valueOf(userId);
		enterprise.ownerId = Integer.valueOf(userId);
		enterprise.isActive = true;
		enterprise.name = name;
		enterprise.description = discription;
		enterprise.phone = phone;
		enterprise.email = email;
		enterprise.address = address;
		if(!post.equals(""))
			enterprise.postcode = post;
		if(!homepage.equals(""))
			enterprise.homepage = homepage;
		
		enterpriseClient.create(enterprise);
		
		acUser.enterpriseId = Integer.valueOf(acUser.userId);
		acUserClient.update(acUser.userId, acUser);
		
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "新建企业", this.getSessionUserEmail()+"申请创建企业",
				"NewEnterpriseAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));

		this.result = "success";
		return SUCCESS;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
