package appcloud.admin.action.group;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreNewAcGroupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PreNewAcGroupAction.class);
	private AcAggregateClient acAggregateClient = ClientFactory.getAggregateClient();
	private List<AcAggregate> acAggregates;
	
	public String execute() {
		acAggregates = acAggregateClient.getAggregates(); 
		logger.info("读取所有集群成功");
		return SUCCESS;
	}

	public List<AcAggregate> getAcAggregates() {
		return acAggregates;
	}

	public void setAcAggregates(List<AcAggregate> acAggregates) {
		this.acAggregates = acAggregates;
	}


	
}