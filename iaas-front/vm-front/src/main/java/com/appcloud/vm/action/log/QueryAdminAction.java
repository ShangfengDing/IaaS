package com.appcloud.vm.action.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.SessionConstants;
import com.appcloud.vm.fe.util.ClientFactory;
import com.opensymphony.xwork2.ActionContext;

public class QueryAdminAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(QueryAdminAction.class);
	private AcUserClient acUserClient =  ClientFactory.getAcUserClient();
	private EnterpriseInvitationClient enterpriseInvitationClient = ClientFactory.getEnterpriseInvitationClient();
	private List<AcUser> acUsers = new ArrayList<AcUser>();
	private List<EnterpriseInvitation> enterpriseInvitations = new ArrayList<EnterpriseInvitation>();
	private Map<Integer, String> emailByUserIds = new HashMap<Integer, String>();
	private String email;
	private String status;
	private int page = 1;
	private int total = 0;
	private int lastpage = 1;
	private final static int PAGESIZE = 10;
	
	@Override
	public String execute() throws Exception {
		logger.info("QueryAdminAction: "+email+", "+status);
		String emailStr = null;
		String userid = null;
		if (email != null && !email.equals("")) {
			emailStr = email;
			userid = acUserClient.search(null, emailStr, null, null, null, null).get(0).userId;
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
	    String userId = session.get(SessionConstants.UserID).toString();
	    Integer enterpriseId = acUserClient.get(userId).enterpriseId;
		logger.info(enterpriseId);
		if (status.equals("actived")) { 
			this.acUsers = acUserClient.search(null, emailStr, null, enterpriseId, page, PAGESIZE);
			this.total += acUserClient.count(null, emailStr, null, enterpriseId).intValue();
		} else {
			logger.info(enterpriseId+", "+userid+", "+status+",");
			this.enterpriseInvitations = enterpriseInvitationClient.getInvitationByProperties(null, enterpriseId.toString(), userid, status, String.valueOf(page), String.valueOf(PAGESIZE));
			this.total += enterpriseInvitationClient.countInvitationByProperties(null, enterpriseId.toString(), userid, status).intValue();
			for (EnterpriseInvitation enterpriseInvitation : enterpriseInvitations) {
				emailByUserIds.put(enterpriseInvitation.userId, acUserClient.get(enterpriseInvitation.userId.toString()).userEmail);
			}
		}
		for (AcUser acUser : acUsers) {
			logger.info(acUser);
		}
		for (EnterpriseInvitation temp:enterpriseInvitations) {
			logger.info(temp.id);
		}
		if ((acUsers != null && acUsers.size() != 0) || (enterpriseInvitations != null && enterpriseInvitations.size() != 0)) {
			if(total % PAGESIZE == 0){
				lastpage = total / PAGESIZE;
			}else{
				lastpage = total / PAGESIZE + 1;
			}
		}
		logger.info("total: "+total+", page: "+page+", lastpage: "+lastpage);
		this.addAcMessageLog(AcModuleEnum.VM_FRONT, UUID.randomUUID().toString().replace("-", ""),
				this.getSessionUserID().toString(), null, "查看企业管理员", "查询企业管理员 ", "QueryAdminAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public List<AcUser> getAcUsers() {
		return acUsers;
	}

	public void setAcUsers(List<AcUser> acUsers) {
		this.acUsers = acUsers;
	}

	public List<EnterpriseInvitation> getEnterpriseInvitations() {
		return enterpriseInvitations;
	}

	public void setEnterpriseInvitations(
			List<EnterpriseInvitation> enterpriseInvitations) {
		this.enterpriseInvitations = enterpriseInvitations;
	}

	public Map<Integer, String> getEmailByUserIds() {
		return emailByUserIds;
	}

	public void setEmailByUserIds(Map<Integer, String> emailByUserIds) {
		this.emailByUserIds = emailByUserIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
}
