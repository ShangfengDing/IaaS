package appcloud.admin.action.group;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcGroupClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class AcGroupListAction extends BaseAction{
	private static final long serialVersionUID = -8978894090535415113L;
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private List<AcGroup> acGroups = null;

	public String execute() {
		acGroups = acGroupClient.getAcGroups();
		return SUCCESS;
	}
	
	public List<AcGroup> getAcGroups() {
		return acGroups;
	}

	public void setAcGroups(List<AcGroup> acGroups) {
		this.acGroups = acGroups;
	}
}
