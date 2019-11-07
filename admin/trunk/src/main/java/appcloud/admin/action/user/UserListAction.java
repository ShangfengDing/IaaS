package appcloud.admin.action.user;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.AcUserClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class UserListAction extends BaseAction{
	private static final long serialVersionUID = -8978894090535415113L;
	private AcUserClient acuserClient = ClientFactory.getAcUserClient();
	Long userNum;

	public String execute() {
		userNum = acuserClient.count(null, null ,null, null);
		return SUCCESS;
	}

	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

}
