package com.appcloud.vm.action.enterprise;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.client.EnterpriseInvitationClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.log.SearchLogAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class DissolveEnterpriseAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchLogAction.class);
	private AcUserClient acUserClient =
			ClientFactory.getAcUserClient();
	private EnterpriseClient enterpriseClient = 
			ClientFactory.getEnterpriseClient();
	private EnterpriseInvitationClient enterpriseInvitationClient = 
			ClientFactory.getEnterpriseInvitationClient();
	
	private String userId;
	private String ownerId;
	private String result;
	
	public String execute() {
		if(userId == null || userId.equals("") ||
		   ownerId == null || ownerId.equals("") ||
		   !userId.equals(ownerId)) {
			this.result = "fail";
			return SUCCESS;
		}
		Integer ownerIdInt=null;
		try {
			ownerIdInt = Integer.valueOf(ownerId);
		} catch(Exception e) {
			e.printStackTrace();
			this.result = "fail";
			return SUCCESS;
		}
		
		//修改user信息
		List<AcUser> acUsers = acUserClient.search(null, null, null, ownerIdInt, 0, 0);
		if(acUsers != null) {
			for(AcUser acUser : acUsers) {
				acUser.enterpriseId = null;
				acUserClient.update(acUser.userId, acUser);
			}
		}
		logger.info("update admin and owner of enterprise " + ownerId + " success");
		
		//删除企业邀请信息
		List<EnterpriseInvitation> invitations = 
				enterpriseInvitationClient.getInvitationOfEnterprise(ownerId);
		if(invitations != null) {
			for(EnterpriseInvitation invitation : invitations) {
				enterpriseInvitationClient.delete(invitation.id);
			}
		}
		logger.info("delete invitation of enterprise " + ownerId + " success");
		
		//删除企业信息
		Enterprise enterprise = enterpriseClient.get(ownerIdInt);
		if(enterprise != null) {
			enterpriseClient.delete(enterprise.id);
		}
		logger.info("delete enterprise " + ownerId + " success");
		
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "解散企业", this.getSessionUserEmail()+"解散企业",
				"DissolveEnterpriseAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		
		this.result = "success";
		return SUCCESS;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
	
	
}
