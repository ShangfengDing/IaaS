package com.appcloud.vm.action.enterprise;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcUser;
import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.Server;
import appcloud.api.beans.Snapshot;
import appcloud.api.beans.Volume;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.client.EnterpriseInvitationClient;
import appcloud.api.client.SecurityGroupClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.SnapshotClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;

public class DealInvitationAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(DealInvitationAction.class);
	private static final long serialVersionUID = 1L;
	private EnterpriseInvitationClient enterpriseInvitationClient = 
			ClientFactory.getEnterpriseInvitationClient();
	private ServerClient serverClient =
			ClientFactory.getServerClient();
	private VolumeClient volumeClient = 
			ClientFactory.getVolumeClient();
	private SnapshotClient snapshotClient = 
			ClientFactory.getSnapshotClient();
	private SecurityGroupClient securityGroupClient = 
			ClientFactory.getSecurityGroupClient();
	private AcUserClient acUserClient =
			ClientFactory.getAcUserClient();
	
	private String userId;
	private String invitationId;
	private String option;
	private String result;
	
	public String execute() {
		if(userId == null || userId.equals("") ||
		   invitationId == null || invitationId.equals("") ||
		   option == null || option.equals("")) {
			this.result = "fail";
			return SUCCESS;
		}
		
		Integer userIdInt=null, invitationIdInt=null;
		DealInvitationOption optionEnum = null;
		try {
			userIdInt = Integer.valueOf(userId);
			invitationIdInt = Integer.valueOf(invitationId);
		} catch(Exception e) {
			this.result = "fail";
			return SUCCESS;
		}
		for(DealInvitationOption op : DealInvitationOption.values()) {
			if(op.toString().equals(option)) {
				optionEnum = op;
				break;
			}
		}
		if(optionEnum == null) {
			this.result = "fail";
			return SUCCESS;
		}
		
		EnterpriseInvitation invitation = enterpriseInvitationClient.getById(invitationIdInt);
		AcUser acUser = acUserClient.get(userId);
		if(acUser == null || (acUser.enterpriseId != null && optionEnum.equals(DealInvitationOption.AGGREE))) {
			this.result = "fail";
			return SUCCESS;
		} 
		
		if(invitation == null || !invitation.userId.equals(userIdInt)) {
			this.result = "fail";
		} else if(optionEnum.equals(DealInvitationOption.AGGREE)){
			List<EnterpriseInvitation> enterpriseInvitations = enterpriseInvitationClient.getInvitationByProperties(null, invitation.enterpriseId.toString(), invitation.userId.toString(), "new", null, null);
			logger.info("来自同一个企业的邀请数为："+enterpriseInvitations.size());
			for (EnterpriseInvitation enterpriseInvitation : enterpriseInvitations) {
				enterpriseInvitation.status = VmEnterpriseInvitationStatus.AGGREED;
				enterpriseInvitationClient.update(enterpriseInvitation.id, enterpriseInvitation);
			}
			acUser.enterpriseId = invitation.enterpriseId;
			acUserClient.update(userId, acUser);
			
			
			/**
			 * 这部分需要将用户的全部资源修改为企业所,所有资产充公！！！！！
			 */
			List<Server> servers = serverClient.getServers(userId, false);
			List<Volume> volumes = volumeClient.getVolumes(userId, false);
			List<Snapshot> snapshots = snapshotClient.getSnapshots(userId);
			List<SecurityGroup> securityGroups = securityGroupClient.getSecurityGroups(userId);
			for(Server server : servers) {
				serverClient.updateOwner(userId, server.id, invitation.enterpriseId.toString());
			}
			for(Volume volume : volumes) {
				volumeClient.update(userId, volume.id, invitation.enterpriseId.toString(), 
						volume.displayName, volume.displayDescription);
			}
			for(Snapshot snapshot : snapshots) {
				snapshotClient.updateSnapshot(userId, snapshot.id, snapshot.displayName, 
						snapshot.displayDescription, invitation.enterpriseId.toString());
			}
			for(SecurityGroup securityGroup : securityGroups) {
				securityGroupClient.updateSecurityGroup(userId, securityGroup.id, securityGroup.name,
						securityGroup.description, invitation.enterpriseId.toString());
			}
			ActionContext.getContext().getSession().put(SessionConstants.UserID, getSessionLoginUserId());
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					invitation.enterpriseId.toString(), null, "加入企业", this.getSessionUserEmail()+"同意邀请信息，加入企业",
					"DealInvitationAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			
			ActionContext.getContext().getSession().put(SessionConstants.UserID, acUser.enterpriseId);
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					invitation.enterpriseId.toString(), null, "加入企业", this.getSessionUserEmail()+"同意邀请信息，加入企业",
					"DealInvitationAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			this.result = "success";
		} else {
			invitation.status = VmEnterpriseInvitationStatus.DENIED;
			enterpriseInvitationClient.update(invitationIdInt, invitation);
			
			Integer userId = getSessionLoginUserId(); 
			ActionContext.getContext().getSession().put(SessionConstants.UserID, getSessionLoginUserId());
			this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
					invitation.enterpriseId.toString(), null, "拒绝邀请", this.getSessionUserEmail()+"拒绝加入企业",
					"DealInvitationAction.class", null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			ActionContext.getContext().getSession().put(SessionConstants.UserID, userId);
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
	
	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getOption() {
		return option;
	}
	
	public void setOption(String option) {
		this.option = option;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public enum DealInvitationOption{
		AGGREE, DENY;
		public String toString() {
			switch(this) {
			case AGGREE:
				return "aggree";
			case DENY:
				return "deny";
			}
			return super.toString();
		}
	}
	
}
