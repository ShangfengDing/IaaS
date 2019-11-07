package com.appcloud.vm.action.log;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.client.EnterpriseInvitationClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;
import com.sun.jersey.api.client.Client;

public class DeleteInvitationAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(DeleteInvitationAction.class);
	private EnterpriseInvitationClient enterpriseInvitationClient = ClientFactory.getEnterpriseInvitationClient();
	private Integer id;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		EnterpriseInvitation enterpriseInvitation = enterpriseInvitationClient.getById(id);
		if (enterpriseInvitation == null || enterpriseInvitation.status != VmEnterpriseInvitationStatus.NEW) {
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "邀请信息撤销失败", "撤销失败,请检查此用户是否存在或邀请信息是否处理", "DeleteInvitationAction.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			return SUCCESS;
		}
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "邀请信息撤销成功", "撤销成功", "DeleteInvitationAction.class", 
				null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
		enterpriseInvitationClient.delete(id);
		this.result = SUCCESS;
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
