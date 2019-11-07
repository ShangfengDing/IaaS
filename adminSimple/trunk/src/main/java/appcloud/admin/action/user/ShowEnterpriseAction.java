package appcloud.admin.action.user;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;

public class ShowEnterpriseAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private Enterprise enterprise;
	private AcUserClient acuserClient = ClientFactory.getAcUserClient();
	private EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
	private static final Logger logger = Logger.getLogger(ChangeStatusAction.class);
	
	public String execute() {
		AcUser acuser = acuserClient.get(uid);
		if(acuser == null) {
			logger.error("no user with id " + uid);
			return "error";
		} else {
			if(acuser.enterpriseId == null) {
				logger.error("user " + uid + " does not have enterprise");
				return "error";
			} else {
				enterprise = enterpriseClient.get(acuser.enterpriseId);
				if(enterprise == null) {
					logger.error("no enterprise with id " + acuser.enterpriseId);
					return "error";
				} 
			}
		}
		return "success";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	
}
