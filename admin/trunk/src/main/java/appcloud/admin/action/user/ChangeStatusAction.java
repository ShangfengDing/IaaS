package appcloud.admin.action.user;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;
import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.Enterprise;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.EnterpriseClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class ChangeStatusAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ChangeStatusAction.class);
	
	private String uid;
	private String result;
	private AcUserClient userClient = ClientFactory.getAcUserClient();
	private String status;
	
	public String execute() throws Exception {
		if(uid == "" || uid == null) {
			logger.error("parameter is wrong when changing status");
			this.result = "error";
			return result;
		} 
		
		AcUser acUser = userClient.get(uid);
		if(acUser == null) {
			logger.error("can not find user with id " + uid);
			this.result = "error";
			return result;
		}
		
		if(acUser.isActive) {
			status = "冻结";
			acUser.isActive = new Boolean(false);
		} else {
			status = "解冻";
			acUser.isActive = new Boolean(true);
		}
		userClient.update(acUser.userId, acUser);
		
		//冻结用户时，检查该用户是否是企业拥有者，是则冻结企业
		if(acUser.enterpriseId != null && acUser.userId.equals(acUser.enterpriseId.toString())) {
			System.out.println("用户是企业用户");
			if(acUser.isActive.equals(false)) {
				EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
				Enterprise enterprise = enterpriseClient.get(Integer.valueOf(acUser.userId));
				if(enterprise.isActive) {
					enterprise.isActive = false;
					enterpriseClient.update(enterprise.id, enterprise);
				}
			}
			if(acUser.isActive.equals(true)) {
				EnterpriseClient enterpriseClient = ClientFactory.getEnterpriseClient();
				Enterprise enterprise = enterpriseClient.get(Integer.valueOf(acUser.userId));
				if(!enterprise.isActive) {
					enterprise.isActive = true;
					enterpriseClient.update(enterprise.id, enterprise);
				}
			}
		}
		
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, status, status+acUser.userEmail, "ChangeStatusAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = "success";
		return result;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
