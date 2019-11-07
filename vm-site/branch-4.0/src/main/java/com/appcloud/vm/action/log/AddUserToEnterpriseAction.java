package com.appcloud.vm.action.log;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcUser;
import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseInvitationClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;
import com.sun.jersey.api.client.Client;

public class AddUserToEnterpriseAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(AddUserToEnterpriseAction.class);
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	private EnterpriseInvitationClient enterpriseInvitationClient = ClientFactory.getEnterpriseInvitationClient();
	private String email;
	private String des;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		logger.info(email+", "+des);
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer enterpriseId = (Integer) session.get(SessionConstants.UserID);
		List<AcUser> acUsers = acUserClient.search(null, email, null, null, null, null);
		if (acUsers == null || acUsers.size() == 0) {
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "邀请加入企业", "失败，此用户不存在 "+email, "AddUserToEnterpriseAction.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			this.result = "no_user";
			return SUCCESS;
		} 
		if (acUsers.get(0).enterpriseId != null && acUsers.get(0).enterpriseId.equals(enterpriseId)) {
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "邀请加入企业", "失败，此用户已为本企业管理员 "+email, "AddUserToEnterpriseAction.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			this.result = "already";
			return SUCCESS;
		}
		Integer userId = Integer.parseInt(acUsers.get(0).userId); 
		logger.info(enterpriseId+", "+userId);
		
		if(enterpriseInvitationClient.getInvitationByProperties(null, enterpriseId.toString(), userId.toString(), "new", null, null).size() > 0){
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					this.getSessionUserID().toString(), null, "邀请加入企业", "失败，重复邀请 "+email+"加入企业", "AddUserToEnterpriseAction.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			this.result = "repeat";
			return SUCCESS;
		}
		
		EnterpriseInvitation enterpriseInvitation = new EnterpriseInvitation(null, enterpriseId, userId, des, VmEnterpriseInvitationStatus.NEW);
		logger.info(enterpriseInvitation);
		enterpriseInvitationClient.create(enterpriseInvitation);
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "邀请加入企业", "成功邀请"+email+"加入企业！", "AddUserToEnterpriseAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = "success";
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
