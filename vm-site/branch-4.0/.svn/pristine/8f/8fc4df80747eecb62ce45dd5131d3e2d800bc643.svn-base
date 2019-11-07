package com.appcloud.vm.action.enterprise;

import java.util.Date;
import java.util.UUID;

import appcloud.api.beans.AcUser;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;

public class QuitEnterpriseAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	
	private String userId;
	private String ownerId;
	private String result;
	
	public String execute() {
		AcUser acUser = acUserClient.get(userId);
		if(userId.equals(ownerId)) {
			this.result = "fail";
			return SUCCESS;
		} else if(!acUser.enterpriseId.equals(Integer.valueOf(ownerId))){
			this.result = "fail";
		} else {
			acUser.enterpriseId = null;
			acUserClient.update(userId, acUser);
			
			//向退出企业者写日志
			Integer userId = getSessionUserID(); 
			ActionContext.getContext().getSession().put(SessionConstants.UserID, getSessionLoginUserId());
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "退出企业", this.getSessionUserEmail()+"退出企业",
					"QuitEnterpriseAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			
			//向企业拥有者写日志
			ActionContext.getContext().getSession().put(SessionConstants.UserID, userId);
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "退出企业", this.getSessionUserEmail()+"退出企业",
					"QuitEnterpriseAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			this.result = "success";
		}
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
