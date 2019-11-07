package appcloud.admin.action.group;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.admin.action.base.BaseAction;
public class DelAcGroupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DelAcGroupAction.class);
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	
	private int acGroupId;
	private String result;
	
	public String execute() {
		AcGroup acGroup = acGroupClient.get(acGroupId);
		if(acGroup == null) {
			logger.error("can not find a acGroup with id " + acGroupId);
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除群组", "群组不错在，删除失败", "DelAcGroupAction.class", 
					null, AcLogLevelEnum.ERROR, new Date(System.currentTimeMillis()));
			this.result = "error";
			return this.result;
		} else {
			long count = acUserClient.count(acGroupId, null, true, null);
			if(count > 0) {
				this.result = "using";
				this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
						this.getUserId(), null, "删除群组", "群组"+acGroup.name+"正在使用中，删除失败", "DelAcGroupAction.class", 
						null, AcLogLevelEnum.WARN, new Date(System.currentTimeMillis()));
				return this.result;
			} else {
				acGroupClient.delete(acGroupId);
				this.result = "success";
				this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
						this.getUserId(), null, "删除群组", "删除群组"+acGroup.name, "DelAcGroupAction.class", 
						null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
				return this.result;
			}
		}
		
	}

	public int getAcGroupId() {
		return acGroupId;
	}

	public void setAcGroupId(int acGroupId) {
		this.acGroupId = acGroupId;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
