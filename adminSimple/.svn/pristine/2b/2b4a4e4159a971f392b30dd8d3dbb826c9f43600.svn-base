package appcloud.admin.action.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.AcUserClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreChangeAuthorityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PreChangeAuthorityAction.class);
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private List<AcGroup> groups;
	
	public String execute() {
		groups = acGroupClient.getAcGroups(); 
		logger.info("读取所有权限组成功");
		return SUCCESS;
	}


	public AcGroupClient getAcGroupClient() {
		return acGroupClient;
	}

	public void setAcGroupClient(AcGroupClient acGroupClient) {
		this.acGroupClient = acGroupClient;
	}

	public List<AcGroup> getGroups() {
		return groups;
	}


	public void setGroups(List<AcGroup> groups) {
		this.groups = groups;
	}
}
