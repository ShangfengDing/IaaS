package appcloud.admin.action.user;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;
import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcUser;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

public class ChangeAuthorityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ChangeAuthorityAction.class);
	
	private String uid;
	
	private String groupId;
	private String result;
	private AcUserClient userClient = ClientFactory.getAcUserClient();
    private AcGroupClient groupClient = ClientFactory.getAcGroupClient();
	private AcUser user = null;
	public String execute() throws Exception {
		if(uid == "" || uid == null || groupId == "" || groupId == null) {
			logger.error("parameter is wrong when changing authority");
			this.result = "error";
			return "error";
		} 
		if(groupClient.get(new Integer(groupId)) == null) {
			logger.error("can not find group with id " + groupId + " when changing authority");
			this.result = "error";
			return "error";
		}
		
		user = userClient.get(uid);
		if(user == null) {
			logger.error("can not find user with id " + uid + " when changing authority");
			this.result = "error";
			return "error";
		}
		
		user.groupId = new Integer(groupId);
		userClient.update(uid, user);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改用户组", "用户组名称为："+groupClient.get(Integer.parseInt(groupId)).name, "ChangeAuthorityAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = "success";
		return "success";
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
