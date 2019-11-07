package com.appcloud.vm.action.enterprise;

import java.util.Date;
import java.util.UUID;

import appcloud.api.beans.Enterprise;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class GetEnterpriseAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	
	private Integer ownerId;
	private Enterprise enterprise;
	private String result;
	
	public String execute() {
		this.enterprise = enterpriseClient.get(ownerId);
		if(enterprise == null)
			this.result = "fail";
		else {
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "获取企业信息", this.getSessionUserEmail()+"获取信息",
					"GetEnterpriseAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			this.result = "success";
		}
		return SUCCESS;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
