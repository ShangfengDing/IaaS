package com.appcloud.vm.action.log;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcUser;
import appcloud.api.client.AcUserClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class RemoveUserFromEnterprise extends BaseAction {
	private static final Logger logger = Logger.getLogger(RemoveUserFromEnterprise.class);
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	private String userId;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		logger.info(userId);
		AcUser acUser = acUserClient.get(userId);
		if (acUser.userId.equals(acUser.enterpriseId.toString())) {
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "从企业移出用户", "失败，企业用户组不能移出自己", "RemoveUserFromEnterprise.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			this.result = "owner";
			return SUCCESS;
		}
		acUser.enterpriseId = null;
		acUserClient.update(acUser.userId, acUser);
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "从企业移出用户", "成功从企业中移出"+acUser.userEmail, "RemoveUserFromEnterprise.class", 
				null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
		this.result = SUCCESS;
		return SUCCESS;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
